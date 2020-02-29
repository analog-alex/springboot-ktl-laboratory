package com.analog.alex.messages.amqp

import com.analog.alex.messages.model.MessageRecord
import com.analog.alex.messages.repository.MessageRecordRepository
import java.time.Instant
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class Listeners(
    val messageRecordRepository: MessageRecordRepository
) {

    @Bean
    fun receiver1() = Worker(1)

    @Bean
    fun receiver2() = Worker(2)

    @Bean
    fun observer1() = Observer(messageRecordRepository)
}

@RabbitListener(queues = ["messages"])
class Worker(private val id: Int) {

    private val logger = LoggerFactory.getLogger(Worker::class.java.toString() + "-$id")

    @RabbitHandler
    fun receive(message: String) {
        workOn(message)
        logger.info("$message")
    }

    private fun workOn(input: String) {
        val size = input.length
        val res = size shl 2 and 11
        // placeholder garbage
    }
}

@RabbitListener(queues = ["observations"], errorHandler = "rabbitErrorHandler")
class Observer(
    private val messageRecordRepository: MessageRecordRepository
) {

    private val logger = LoggerFactory.getLogger(Observer::class.java)

    @RabbitHandler
    fun receive(observation: String) {
        logger.info("Observation  : $observation")

        val record = MessageRecord(
            id = UUID.randomUUID().toString().replace("-", ""),
            body = observation,
            at = Instant.now(),
            queue = "observations",
            key = "see"
        )
        messageRecordRepository.save(record)
    }
}
