package com.analog.alex.data.entities.item.repository

import com.analog.alex.data.entities.item.model.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface ItemRepository : CrudRepository<Item, Long> {

    @Query(value = "SELECT i FROM items i WHERE brand = ?1 AND price > 20")
    fun findNotWorthlessItemByBrand(brand: String): Collection<Item>

    fun findItemByBrand(brand: String, sort: Sort): Collection<Item>

    fun findItemByBrandOrderByPrice(brand: String, pageable: Pageable): Page<Item>
}
