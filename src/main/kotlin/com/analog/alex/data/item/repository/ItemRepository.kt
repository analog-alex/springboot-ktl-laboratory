package com.analog.alex.data.item.repository

import com.analog.alex.data.item.model.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface ItemRepository : CrudRepository<Item, Long>
