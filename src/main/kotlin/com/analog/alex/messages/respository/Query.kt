package com.analog.alex.messages.respository

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class Query(
        val costumerRepository: CostumerRepository
) : GraphQLQueryResolver {

    fun findAllCostumers() = costumerRepository.findAll()
    fun countCostumers() = costumerRepository.count()
}