package com.analog.alex.data.entities.user.respository

import com.analog.alex.data.entities.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface UserRepository : CrudRepository<User, Long>, CustomUserRepository {
    fun findByUsername(username: String): User?
    fun findByRole(role: String): Collection<User>
}
