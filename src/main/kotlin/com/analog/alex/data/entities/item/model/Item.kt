package com.analog.alex.data.entities.item.model

import com.analog.alex.data.entities.customer.model.Customer
import com.analog.alex.data.entities.store.model.Store
import javax.persistence.*

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer", referencedColumnName = "id")
    val customer: Customer,

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val stores: Set<Store> = emptySet()
) {
    override fun hashCode(): Int {
        return name.hashCode() xor price.hashCode() xor brand.hashCode() + (id?.toInt() ?: 0)
    }
}
