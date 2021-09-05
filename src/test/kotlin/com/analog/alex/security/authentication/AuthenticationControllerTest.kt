package com.analog.alex.security.authentication

import assertk.all
import com.analog.alex.security.authentication.web.AuthenticationController
import com.analog.alex.security.user.model.Role
import com.analog.alex.security.user.model.User
import com.analog.alex.security.user.model.UserCredentials
import com.analog.alex.security.utils.uuid
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

import assertk.assertThat
import assertk.assertions.*
import com.analog.alex.security.authentication.jwt.service.JwtService
import org.springframework.dao.DuplicateKeyException

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerTest {

    @Autowired
    private lateinit var authenticationController: AuthenticationController

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @BeforeAll
    fun setup() {
        val encodedPassword = passwordEncoder.encode("test_password")

        mongoTemplate.save<User>(
            User(
                id = uuid(),
                username = "test_user",
                password = encodedPassword,
                roles = setOf(Role.ADMIN),
                createdAt = LocalDateTime.now()
            )
        )
    }

    @AfterAll
    fun teardown() {
        mongoTemplate.dropCollection<User>()
    }

    @Test
    fun `when user logins a JWT is passed in the response`() {
        val response = authenticationController.loginUser(
            UserCredentials(
                username = "test_user",
                password = "test_password"
            )
        )

        assertThat(response.action).isEqualTo(Action.LOGGED)
        assertThat(response.credential).isNotNull().isNotEmpty()
        assertThat(response.principal).isEqualTo("test_user")
    }

    @Test
    fun `when user logins a valid JWT is returned with the expected body`() {
        val response = authenticationController.loginUser(
            UserCredentials(
                username = "test_user",
                password = "test_password"
            )
        )

        val claims = authenticationController.validateUser(response.credential!!)

        assertThat(claims).all {
            prop(JwtService.JwtBodyClaims::usr).isEqualTo("test_user")
            prop(JwtService.JwtBodyClaims::rls).isEqualTo(listOf("ADMIN"))
        }
    }

    @Test
    fun `when a user is registered, we can log in with his credentials`() {
        val registered = authenticationController.registerUser(
            UserCredentials(
                username = "test_user_2",
                password = "test_password_2"
            )
        )

        assertThat(registered.action).isEqualTo(Action.REGISTERED)
        assertThat(registered.credential).isNull()
        assertThat(registered.principal).isEqualTo("test_user_2")

        val loggedOn = authenticationController.loginUser(
            UserCredentials(
                username = "test_user_2",
                password = "test_password_2"
            )
        )

        assertThat(loggedOn.action).isEqualTo(Action.LOGGED)
        assertThat(loggedOn.credential).isNotNull().isNotEmpty()
        assertThat(loggedOn.principal).isEqualTo("test_user_2")
    }

    @Test
    fun `when a user is registered with a non unique username, an error should be verified`() {
        assertThat {
            authenticationController.registerUser(
                UserCredentials(
                    username = "test_user",
                    password = "test_password_another"
                )
            )
        }
            .isFailure().isInstanceOf(DuplicatedUsernameException::class)
    }
}