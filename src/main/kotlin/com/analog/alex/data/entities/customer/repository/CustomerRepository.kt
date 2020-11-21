package com.analog.alex.data.entities.customer.repository

import com.analog.alex.data.entities.customer.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface CustomerRepository : CrudRepository<Customer, Long>, CustomCustomerRepository {
    fun findByName(name: String): Collection<Customer>
    fun findByNameAndCountry(name: String, country: String): Collection<Customer>
}
