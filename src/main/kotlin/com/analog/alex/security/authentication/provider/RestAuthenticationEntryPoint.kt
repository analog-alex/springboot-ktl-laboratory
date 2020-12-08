package com.analog.alex.security.authentication.provider

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.Instant
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        val data = mapOf(
            "timestamp" to Instant.now().toString(),
            "status" to HttpStatus.UNAUTHORIZED.value(),
            "error" to HttpStatus.UNAUTHORIZED.reasonPhrase,
            "message" to authException.message,
            "path" to request.requestURI
        )

        response.apply {
            contentType = MediaType.APPLICATION_JSON_VALUE
            status = HttpStatus.UNAUTHORIZED.value()
            outputStream.println(ObjectMapper().writeValueAsString(data))
        }
    }
}