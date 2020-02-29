package com.analog.alex.messages.web

import com.analog.alex.messages.amqp.Producer
import com.analog.alex.messages.configs.RabbitConfiguration
import com.analog.alex.messages.error.BadRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class WebController(
    val producer: Producer
) {

    @PostMapping(
        value = ["send"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun send(@RequestBody payload: SimpleContainer) {
        if (payload.data?.isEmpty()) { throw BadRequest("Payload is empty") }

        producer.produce(payload.data, RabbitConfiguration.PropertiesWorker.exchange, RabbitConfiguration.PropertiesWorker.key)
        producer.produce("Event registered", RabbitConfiguration.PropertiesObserver.exchange, RabbitConfiguration.PropertiesObserver.key)
    }
}

data class SimpleContainer(val data: String)
