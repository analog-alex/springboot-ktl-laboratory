package com.analog.alex.security.authentication.jwt.filter

import com.analog.alex.security.utils.Constants
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val authHeader = httpRequest.getHeader(Constants.AUTH_HEADER)

        /*
         * Here we simple pass whatever is placed in the 'Authorization' header, if anything.
         * We don't concern ourselves here with any kind of validation or authentication; that
         *   is the responsibility of the Authentication Manager.
         */
        SecurityContextHolder.getContext().authentication = if (authHeader != null) {
            JwtAuthenticationToken(jwt = authHeader)
        } else { null }

        /*
         * Resume the request process.
         */
        chain.doFilter(request, response)
    }
}
