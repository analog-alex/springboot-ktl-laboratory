package com.analog.alex.data.user.model

import com.analog.alex.data.customer.model.Customer
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

enum class Role {
    ADMN,
    USER,
    GUST
}

@Entity(name = "app_users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String,

    @Column(name = "role")
    val role: String = Role.GUST.toString(),

    @OneToOne(mappedBy = "user")
    val customer: Customer? = null
) {
    override fun hashCode(): Int {
        return username.hashCode() xor password.hashCode() + (id?.toInt() ?: 0)
    }
}
