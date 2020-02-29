package com.analog.alex.messages.configs

import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfiguration {

    object PropertiesWorker {
        const val queue: String = "messages"
        const val exchange: String = "exchangeWorkers"
        const val key: String = "bind.#"
    }

    @Bean
    fun messages() = Queue(PropertiesWorker.queue)

    @Bean
    fun topicWorker() = TopicExchange(PropertiesWorker.exchange)

    @Bean
    fun binding1(messages: Queue, topicWorker: TopicExchange) = BindingBuilder.bind(messages).to(topicWorker).with(PropertiesWorker.key)

    /* ----------------------- */

    object PropertiesObserver {
        const val queue: String = "observations"
        const val exchange: String = "exchangeObservations"
        const val key: String = "see.#"
    }

    @Bean
    fun observations() = Queue(PropertiesObserver.queue)

    @Bean
    fun topicObservation() = TopicExchange(PropertiesObserver.exchange)

    @Bean
    fun binding2(observations: Queue, topicObservation: TopicExchange) = BindingBuilder.bind(observations).to(topicObservation).with(PropertiesObserver.key)

    /* ----------------------- */

    @Bean
    fun rabbitErrorHandler(): RabbitListenerErrorHandler {
        return RabbitListenerErrorHandler { _, message, _ ->
            // don't requeue
            throw AmqpRejectAndDontRequeueException(message?.toString() ?: "reject")
        }
    }
}
