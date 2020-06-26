package com.analog.alex.messages

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MessagesApplication

fun main(args: Array<String>) {
    runApplication<MessagesApplication>(*args)
}
