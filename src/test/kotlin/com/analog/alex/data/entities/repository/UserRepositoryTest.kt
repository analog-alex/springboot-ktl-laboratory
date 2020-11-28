package com.analog.alex.data.entities.repository

import com.analog.alex.data.entities.user.model.Role
import com.analog.alex.data.entities.user.model.User
import com.analog.alex.data.entities.user.respository.UserRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(value = [SpringExtension::class])
@SpringBootTest
@TestPropertySource(properties = [
    "spring.profiles.active=test",
    "spring.datasource.url=jdbc:postgresql://localhost:5432/db",
    "spring.datasource.username=postgres",
    "spring.datasource.password=password"
])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeAll
    fun setup() {
        userRepository.saveAll(
            listOf(
                User(username = "user1", password = "password1", role = Role.ADMIN),
                User(username = "user2", password = "password2", role = Role.USER),
                User(username = "user3", password = "password3", role = Role.USER),
                User(username = "user4", password = "password4", role = Role.GUEST)
            )
        )
    }

    @AfterAll
    fun teardown() {
        userRepository.deleteAll()
    }

    @Test
    fun `findByFields -- query correctly retrieves resources by username`() {
        val users = userRepository.findByFields(username = "user2", role = null)

        assertTrue { users.isNotEmpty() && users.size == 1 }

        val extracted = users.first()

        assertEquals("user2", extracted.username)
        assertEquals("password2", extracted.password)
        assertEquals(Role.USER, extracted.role)
    }

    @Test
    fun `findByFields -- query correctly retrieves resources by role`() {
        val users = userRepository.findByFields(username = null, role = Role.ADMIN)

        assertTrue { users.isNotEmpty() && users.size == 1 }

        val extracted = users.first()

        assertEquals("user1", extracted.username)
        assertEquals("password1", extracted.password)
        assertEquals(Role.ADMIN, extracted.role)
    }

    @Test
    fun `findByFields -- query correctly retrieves resources by role -- alternative`() {
        val users = userRepository.findByFields(username = null, role = Role.USER)

        assertTrue { users.isNotEmpty() && users.size == 2 }
    }

    @Test
    fun `findByFields -- query correctly retrieves resources by both username and role`() {
        val users = userRepository.findByFields(username = "user4", role = Role.GUEST)

        assertTrue { users.isNotEmpty() && users.size == 1 }

        val extracted = users.first()

        assertEquals("user4", extracted.username)
        assertEquals("password4", extracted.password)
        assertEquals(Role.GUEST, extracted.role)
    }
}