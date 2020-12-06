package com.analog.alex.security.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(value = "application_user")
data class User(
    @Id val id: String,
    @Indexed(unique = true) val username: String,
    val password: String,
    val roles: Set<Role>,
    val createdAt: LocalDateTime
)

enum class Role {
    ADMIN,
    ANONYMOUS,
    BASIC
}