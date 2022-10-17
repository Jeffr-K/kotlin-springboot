package com.lambs.lambsroad.infrastructure.externals.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lambs.lambsroad.domain.user.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private var tokenPrefix: String = "Bearer ",
    private var headerString: String = "Authorization"
): UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        return try {
            val mapper = jacksonObjectMapper()

            val credentials = mapper.readValue<User>(request.inputStream)

            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    credentials.username,
                    credentials.password,
                    ArrayList()
                )
            )
        } catch (e: IOException) {
            throw AuthenticationServiceException(e.message)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        ressponse: HttpServletResponse,
        chain: FilterChain?,
        authentication: Authentication
    ) {
        val token = jwtTokenProvider.createToken(authentication)
        ressponse.addHeader(headerString, tokenPrefix + token)
    }
}
