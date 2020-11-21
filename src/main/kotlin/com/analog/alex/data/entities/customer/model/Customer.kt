package com.analog.alex.data.entities.customer.model

import com.analog.alex.data.entities.item.model.Item
import com.analog.alex.data.entities.user.model.User
import javax.persistence.*

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

    @Column(name = "purse")
    val purse: Int = 0,

    @OneToOne(cascade = [CascadeType.REMOVE], fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    val user: User,

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    val items: Set<Item> = emptySet(),

    @Transient
    val treatment: String? = null
) {
    override fun hashCode(): Int {
        return name.hashCode() xor age.hashCode() + country.hashCode() + (id?.toInt() ?: 0)
    }
}
