package com.analog.alex.data.item.model

import com.analog.alex.data.customer.model.Customer
import com.analog.alex.data.store.model.Store
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity(name = "items")
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "price")
    val price: Int,

    @Column(name = "brand")
    val brand: String,

    @ManyToOne
    @JoinColumn(name = "customer", referencedColumnName = "id")
    val customer: Customer,

    @ManyToMany(mappedBy = "items")
    val stores: Set<Store> = emptySet()
) {
    override fun hashCode(): Int {
        return name.hashCode() xor price.hashCode() xor brand.hashCode() + (id?.toInt() ?: 0)
    }
}
