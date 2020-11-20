package com.analog.alex.data.utils

import com.analog.alex.data.customer.model.Customer
import com.analog.alex.data.customer.repository.CustomerRepository
import com.analog.alex.data.item.model.Item
import com.analog.alex.data.item.repository.ItemRepository
import com.analog.alex.data.store.model.Store
import com.analog.alex.data.store.repository.StoreRepository
import com.analog.alex.data.user.model.Role
import com.analog.alex.data.user.model.User
import com.analog.alex.data.user.respository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile(value = ["dev"])
class DataInjectorOnStartUp(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val storeRepository: StoreRepository,
    private val itemRepository: ItemRepository
) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(DataInjectorOnStartUp::class.java)

    override fun run(vararg args: String?) {
        logger.info("Drop data")

        storeRepository.deleteAll()
        itemRepository.deleteAll()
        customerRepository.deleteAll()
        userRepository.deleteAll()

        // ------------------------

        logger.info("Inject Users")

        val users = listOf(
            User(username = "user1", password = "password1", role = Role.ADMN.toString()),
            User(username = "user2", password = "password2", role = Role.USER.toString()),
            User(username = "user3", password = "password3", role = Role.USER.toString()),
            User(username = "user4", password = "password4", role = Role.USER.toString())
        ).map(userRepository::save)


        // ------------------------

        logger.info("Inject Costumers")

        val costumers = listOf(
            Customer(name = "Costumer 1", age = 29, country = "PT", user = users[0]),
            Customer(name = "Costumer 2", age = 29, country = "US", user = users[1]),
            Customer(name = "Costumer 3", age = 29, country = "UK", user = users[2]),
            Customer(name = "Costumer 4", age = 29, country = "FR", user = users[3])
        ).map(customerRepository::save)

        // ------------------------

        logger.info("Inject Items")

        val items = listOf(
            Item(name = "Computer", price = 999, brand = "Apple", customer = costumers[0]),
            Item(name = "Phone", price = 870, brand = "Samsung", customer = costumers[0]),
            Item(name = "Monitor", price = 459, brand = "LG", customer = costumers[1])
        ).map(itemRepository::save)

        // ------------------------

        logger.info("Inject Stores")

        val stores = listOf(
            Store(name = "Store 1", country = "PT", location = "Location 1", items = setOf(items[0], items[1])),
            Store(name = "Store 2", country = "JP", location = "Location 2", items = setOf(items[1], items[2])),
            Store(name = "Store 3", country = "US", location = "Location 3", items = setOf(items[0], items[2]))
        )

        storeRepository.saveAll(stores)

    }
}