package com.analog.alex.data.costumer.repository

import com.analog.alex.data.costumer.model.Costumer
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface CostumerRepository : CrudRepository<Costumer, Long>
