package com.analog.alex.security.authentication.provider

import com.analog.alex.security.user.model.UserContext
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class UserToken(
    private val jwt: String,
    private val userContext: UserContext,
    authorities: List<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {

    override fun getCredentials() = jwt
    override fun getPrincipal() = userContext
}