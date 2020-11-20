package com.analog.alex.data.customer.model

import com.analog.alex.data.item.model.Item
import com.analog.alex.data.user.model.User
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity(name = "customers")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "age")
    val age: Int,

    @Column(name = "country")
    val country: String,

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    val user: User,

    @OneToMany(mappedBy = "customer")
    val items: Set<Item> = emptySet()
) {
    override fun hashCode(): Int {
        return name.hashCode() xor age.hashCode() + country.hashCode() + (id?.toInt() ?: 0)
    }
}
