package com.analog.alex.data.user.model

import com.analog.alex.data.costumer.model.Costumer
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

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

    @OneToOne(mappedBy = "user")
    val costumer: Costumer
) {
    override fun hashCode(): Int {
        return username.hashCode() xor password.hashCode() + (id?.toInt() ?: 0)
    }
}
