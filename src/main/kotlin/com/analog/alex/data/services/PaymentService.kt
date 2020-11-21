package com.analog.alex.data.services

import com.analog.alex.data.entities.customer.model.Customer
import com.analog.alex.data.entities.customer.repository.CustomerRepository
import com.analog.alex.data.errors.InsufficientFunds
import com.analog.alex.data.errors.NotFound
import com.analog.alex.data.entities.item.repository.ItemRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

data class PaymentResult(val costumerId: Long, val updatedPurse: Int, val payed: Int)

interface PaymentService {
    fun pay(costumerId: Long): PaymentResult
}

@Service
class PaymentServiceImpl(
    val customerRepository: CustomerRepository,
    val itemRepository: ItemRepository
) : PaymentService {

    @Transactional(rollbackOn = [Exception::class])
    override fun pay(costumerId: Long): PaymentResult {
        val costumer = customerRepository.findById(costumerId)
            .orElseThrow { NotFound(costumerId, Customer::class.java) }
        val toPay = costumer.items.sumBy { it.price }
        val remainder = (costumer.purse - toPay).takeIf { it > 0 } ?: throw InsufficientFunds(costumerId)

        customerRepository.save(costumer.copy(purse = costumer.purse - toPay))
        itemRepository.deleteAll(costumer.items)

        return PaymentResult(costumerId, remainder, toPay)
    }
}