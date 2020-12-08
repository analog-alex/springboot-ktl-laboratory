package com.analog.alex.security.authentication.jwt.filter

import org.springframework.security.authentication.AbstractAuthenticationToken

/**
 * A simple container for the raw Jwt token made by extending [AbstractAuthenticationToken].
 *
 * No [org.springframework.security.core.GrantedAuthority] is passed to the super class, instead it defaults to an
 * empty list.
 *
 * @property jwt the raw JWT token, in [String] format
 */
class JwtAuthenticationToken(private val jwt: String) : AbstractAuthenticationToken(emptyList()) {
    override fun getCredentials() = jwt
    override fun getPrincipal() = "N/A"
}