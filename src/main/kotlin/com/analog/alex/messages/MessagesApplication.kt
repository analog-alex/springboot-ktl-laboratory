package com.analog.alex.messages

import com.analog.alex.messages.model.Costumer
import com.analog.alex.messages.respository.CostumerRepository
import jodd.util.CommandLine
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class MessagesApplication

fun main(args: Array<String>) {
    runApplication<MessagesApplication>(*args)
}


@Component
class AppStateInitializer(
        val costumerRepository: CostumerRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (costumerRepository.count() == 0L) {
            val costumers = listOf(
                    Costumer(
                            name = "Miguel Alexandre",
                            age = 28,
                            country = "Portugal"
                    ),
                    Costumer(
                            name = "Megan Fox",
                            age = 32,
                            country = "USA"
                    ),
                    Costumer(
                            name = "Michael Schumacher",
                            age = 52,
                            country = "Germany"
                    )
            )

            costumerRepository.saveAll(costumers)
        }
    }
}