package com.analog.alex.data.entities.store.model

import com.analog.alex.data.entities.item.model.Item
import javax.persistence.*

@Entity(name = "stores")
data class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "country")
    val country: String,

    @Column(name = "location")
    val location: String,

    @ManyToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "item_to_store",
        joinColumns = [JoinColumn(name = "store", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "item", referencedColumnName = "id")]
    )
    val items: Set<Item> = emptySet()
) {
    override fun hashCode(): Int {
        return name.hashCode() xor country.hashCode() xor location.hashCode() + (id?.toInt() ?: 0)
    }
}
