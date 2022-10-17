package com.lambs.lambsroad.infrastructure.externals.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider(
    private val userDetailsService: UserDetailsService
) {
    private var key: Key? = null
    private var tokenValidity: Date? = null
    private val secretKey: String = "제블로그에오신여러분을환영합니다!@tKey"
    private var expirationTime = 30 * 60 * 1000L
    private var tokenPrefix = "Bearer "

    @PostConstruct
    fun init() {
        val now = Date()
        key = Keys.hmacShaKeyFor(secretKey.toByteArray())
        tokenValidity = Date(now.time + expirationTime)
    }

    fun createToken(authentication: Authentication): String {
        val authClaims: MutableList<String> = mutableListOf()
        authentication.authorities?.let { authorities ->
            authorities.forEach { claim -> authClaims.add(claim.toString()) }
        }

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authClaims)
            .setExpiration(tokenValidity)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getAuthentication(token: String): Authentication? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace(tokenPrefix, ""))
            val userDetail = userDetailsService.loadUserByUsername(claims.body.subject)
            val principal = User(userDetail.username, "", userDetail.authorities)
            UsernamePasswordAuthenticationToken(principal, token, userDetail.authorities)
        } catch (e: Exception) {
            return null
        }
    }
}
