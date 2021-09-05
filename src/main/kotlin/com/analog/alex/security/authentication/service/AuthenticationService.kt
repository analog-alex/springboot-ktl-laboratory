package com.analog.alex.security.authentication.service

import com.analog.alex.security.authentication.Action
import com.analog.alex.security.authentication.AuthenticationFailedException
import com.analog.alex.security.authentication.AuthenticationResult
import com.analog.alex.security.authentication.DuplicatedUsernameException
import com.analog.alex.security.authentication.jwt.service.JwtService
import com.analog.alex.security.errors.UserDoesNotExistException
import com.analog.alex.security.user.model.Role
import com.analog.alex.security.user.model.User
import com.analog.alex.security.user.model.UserCredentials
import com.analog.alex.security.user.repository.UserRepository
import com.analog.alex.security.utils.uuid
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime


interface AuthenticationService {
    fun registerUser(credentials: UserCredentials): AuthenticationResult
    fun registerAdmin(credentials: UserCredentials): AuthenticationResult
    fun login(credentials: UserCredentials): AuthenticationResult
    fun validateToken(token: String): JwtService.JwtBodyClaims?

}

@Service
class AuthenticationServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: BCryptPasswordEncoder
) : AuthenticationService {
    override fun registerUser(credentials: UserCredentials): AuthenticationResult {
        userRepository.findByUsername(credentials.username)?.let {
            throw DuplicatedUsernameException()
        }

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

    override fun registerAdmin(credentials: UserCredentials): AuthenticationResult {
        userRepository.findByUsername(credentials.username)?.let {
            throw DuplicatedUsernameException()
        }

        val saved = userRepository.save(
            User(
                id = uuid(),
                username = credentials.username,
                password = passwordEncoder.encode(credentials.password),
                roles = setOf(Role.ADMIN),
                createdAt = LocalDateTime.now()
            )
        )

        return AuthenticationResult(action = Action.REGISTERED, principal = saved.username)
    }

    override fun login(credentials: UserCredentials): AuthenticationResult {
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

    override fun validateToken(token: String): JwtService.JwtBodyClaims? {
        return try {
            return jwtService.parse(token)
        } catch (e: Exception) { null }
    }
}