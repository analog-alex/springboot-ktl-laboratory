package com.analog.alex.security.authentication.provider

import com.analog.alex.security.authentication.AuthenticationFailedException
import com.analog.alex.security.authentication.MalformedTokenException
import com.analog.alex.security.authentication.NoTokenException
import com.analog.alex.security.authentication.jwt.filter.JwtAuthenticationToken
import com.analog.alex.security.authentication.jwt.service.JwtService
import com.analog.alex.security.user.model.UserContext
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class ApplicationAuthenticationProvider(
    private val jwtService: JwtService
) : AuthenticationProvider {
    private val logger = LoggerFactory.getLogger(AuthenticationProvider::class.java)

    override fun authenticate(authentication: Authentication): Authentication {
        val token = (authentication.credentials as? String)?.takeIf { it.isNotBlank() } ?: throw NoTokenException()

        if (token.startsWith("Bearer ").not()) {
            throw MalformedTokenException()
        }

        val claims = try { jwtService.parse(token) } catch (e: Exception) {
            logger.debug("JWT parsing failed: ${e.message}")
            throw AuthenticationFailedException()
        }

        return UserToken(
            jwt = token,
            userContext = UserContext(
                username = claims.usr,
                roles = claims.roles()
            ),
            authorities = claims.rls.map { role -> SimpleGrantedAuthority("ROLE_$role") }
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == JwtAuthenticationToken::class.java
    }
}


