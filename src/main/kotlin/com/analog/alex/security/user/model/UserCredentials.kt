package com.analog.alex.security.user.model

data class UserCredentials(val username: String, val password: String)
data class UserContext(val username: String, val roles: Set<Role>)