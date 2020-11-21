package com.analog.alex.data.entities.store.repository

import com.analog.alex.data.entities.store.model.Store
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface StoreRepository : CrudRepository<Store, Long>
