package com.analog.alex.data.entities.user.model

import com.analog.alex.data.entities.customer.model.Customer
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

enum class Role {
    ADMIN,
    USER,
    GUEST
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
    @Enumerated(EnumType.STRING)
    val role: Role = Role.GUEST,

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    val customer: Customer? = null
) {
    override fun hashCode(): Int {
        return username.hashCode() xor password.hashCode() + (id?.toInt() ?: 0)
    }
}
