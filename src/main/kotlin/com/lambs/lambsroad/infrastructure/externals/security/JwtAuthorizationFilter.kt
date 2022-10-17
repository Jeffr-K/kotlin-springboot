package com.lambs.lambsroad.infrastructure.externals.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
    authManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private var tokenPrefix: String = "Bearer ",
    private var headerString: String = "Authorization"
): BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(headerString)
        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(request, response)
            return
        }
        jwtTokenProvider.getAuthentication(header)?.also { authentication ->
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain.doFilter(request, response)
    }
}
