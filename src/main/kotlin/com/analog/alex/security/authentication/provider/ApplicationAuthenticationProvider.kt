package com.analog.alex.security.authentication.provider

import com.analog.alex.security.authentication.AuthenticationFailedException
import com.analog.alex.security.authentication.MalformedTokenException
import com.analog.alex.security.authentication.NoTokenException
import com.analog.alex.security.authentication.jwt.filter.JwtAuthenticationToken
import com.analog.alex.security.authentication.jwt.service.JwtService
import com.analog.alex.security.user.model.Role
import com.analog.alex.security.user.model.UserContext
import com.analog.alex.security.user.repository.UserRepository
import com.analog.alex.security.utils.Constants
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class ApplicationAuthenticationProvider(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) : AuthenticationProvider {
    private val logger = LoggerFactory.getLogger(AuthenticationProvider::class.java)

    override fun authenticate(authentication: Authentication): Authentication {
        val token = (authentication.credentials as? String)?.takeIf { it.isNotBlank() } ?: throw NoTokenException()

        if (token.startsWith(Constants.TOKEN_PREFIX).not()) {
            throw MalformedTokenException()
        }

        val claims = try { jwtService.parse(token.removePrefix(Constants.TOKEN_PREFIX)) } catch (e: Exception) {
            logger.debug("JWT parsing failed: ${e.message}")
            throw AuthenticationFailedException()
        }

        return userRepository.findByUsername(claims.usr)?.let { user ->
            logger.info("Authenticated {}", user)
            UserToken(
                jwt = token,
                userContext = UserContext(
                    username = claims.usr,
                    roles = claims.rls.map { asStr -> Role.valueOf(asStr) }.toSet()
                ),
                authorities = claims.rls.map { role -> SimpleGrantedAuthority("ROLE_$role") }
            )
        } ?: throw AuthenticationFailedException()

    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == JwtAuthenticationToken::class.java
    }
}


