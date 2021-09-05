package com.analog.alex.security

import com.analog.alex.security.authentication.service.AuthenticationService
import com.analog.alex.security.user.model.UserCredentials
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@SpringBootApplication
class SecurityApplication

fun main(args: Array<String>) {
    runApplication<SecurityApplication>(*args)
}


@Component
@Profile("development")
class RegisterAdmin(private val authenticationService: AuthenticationService) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(RegisterAdmin::class.java)


    override fun run(vararg args: String?) {
        logger.info("Bootstrapping a dummy admin account")

        runCatching {
            authenticationService.registerAdmin(
                UserCredentials(
                    username = "ADMIN",
                    password = "admin123"
                )
            )
        }.onFailure {
            logger.error("Failed to create admin account: {}", it.message)
        }

    }
}