package com.analog.alex.messages.amqp

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class Producer(
    val rabbitTemplate: RabbitTemplate
) {

    private val logger = LoggerFactory.getLogger(Producer::class.java)

    fun produce(message: String, exchange: String, key: String) {
        rabbitTemplate.convertAndSend(exchange, key, message)
        logger.info("Placed a message in queue")
    }
}
