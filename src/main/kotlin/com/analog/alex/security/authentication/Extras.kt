package com.analog.alex.security.authentication

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.context.annotation.Configuration

enum class Action(val value: String) {
    REGISTERED("REGISTRATION_COMPLETE"),
    LOGGED("USER_LOGGED_ON")
}

data class AuthenticationResult(
    val action: Action,
    val principal: String,
    @JsonInclude(JsonInclude.Include.NON_NULL) val credential: String? = null
)
