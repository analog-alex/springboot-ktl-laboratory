package com.analog.alex.security.authentication.jwt.service

import com.analog.alex.security.user.model.User
import com.analog.alex.security.utils.uuid
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.Date

@Service
class JwtService {
    private val logger = LoggerFactory.getLogger(JwtService::class.java)

    /**
     * The key should be configured externally to the system and consumed as a configuration.
     * In a scalable scenario involving multiple PODs, it would be unfeasible for each applications instance to
     *   have its own secret key
     */
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    private fun getExpiry(): Date = Date.from(
        Instant.now().plus(Duration.ofMinutes(10))
    )

    // ----------------------------

    fun generate(about: User): String {
        val customClaims = mapOf(
            "usr" to about.username,
            "rls" to about.roles
        )

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(customClaims)
            .setId(uuid())
            .setExpiration(getExpiry())
            .setSubject(about.id)
            .setIssuer(JwtService::class.java.simpleName)
            .signWith(secretKey)
            .compact()
    }

    fun parse(jwt: String): Claims {
        val parsed = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt)
        logger.debug("Validated JWT: '$parsed'")
        return parsed.body
    }
}