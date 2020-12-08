package com.analog.alex.security.authentication.web

import com.analog.alex.security.authentication.Action
import com.analog.alex.security.authentication.AuthenticationFailedException
import com.analog.alex.security.authentication.AuthenticationResult
import com.analog.alex.security.authentication.jwt.service.JwtService
import com.analog.alex.security.errors.UserDoesNotExistException
import com.analog.alex.security.user.model.Role
import com.analog.alex.security.user.model.User
import com.analog.alex.security.user.model.UserCredentials
import com.analog.alex.security.user.repository.UserRepository
import com.analog.alex.security.utils.uuid
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("auth")
class AuthenticationController(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping(
        value = ["/register"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun registerUser(@RequestBody credentials: UserCredentials): AuthenticationResult {
        val saved = userRepository.save(
            User(
                id = uuid(),
                username = credentials.username,
                password = passwordEncoder.encode(credentials.password),
                roles = setOf(Role.BASIC, Role.ANONYMOUS),
                createdAt = LocalDateTime.now()
            )
        )

        return AuthenticationResult(action = Action.REGISTERED, principal = saved.username)
    }

    @PostMapping(
        value = ["/login"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun loginUser(@RequestBody credentials: UserCredentials): AuthenticationResult {
        val user = userRepository.findByUsername(credentials.username)
            ?: throw UserDoesNotExistException(credentials.username)

        if (passwordEncoder.matches(credentials.password, user.password).not()) {
            throw AuthenticationFailedException()
        }

        return AuthenticationResult(
            action = Action.LOGGED,
            principal = user.username,
            credential = jwtService.generate(user)
        )
    }

    @GetMapping(
        value = ["/validate"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun validateUser(@RequestParam token: String) = jwtService.parse(token)
}