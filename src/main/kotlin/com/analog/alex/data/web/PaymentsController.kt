package com.analog.alex.data.web

import com.analog.alex.data.services.PaymentService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class DoPay(val id: Long)

@RestController
@RequestMapping(value = ["/payment"])
class PaymentsController(
    val paymentService: PaymentService
) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun pay(@RequestBody doPay: DoPay) = paymentService.pay(doPay.id)
}