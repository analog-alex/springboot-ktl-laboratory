package com.analog.alex.messages

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@SpringBootApplication
@EnableCassandraRepositories
class MessagesApplication

fun main(args: Array<String>) {
    runApplication<MessagesApplication>(*args)
}
