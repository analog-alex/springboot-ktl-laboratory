package com.analog.alex.messages.respository

import com.analog.alex.messages.model.Costumer
import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class Mutation(
        val costumerRepository: CostumerRepository
) : GraphQLMutationResolver {

    fun createCostumer(name: String, age: Int, country: String): Costumer {
        return costumerRepository.save(
                Costumer(
                        name = name,
                        age = age,
                        country = country
                )
        )
    }
}