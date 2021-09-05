package com.analog.alex.security.user.repository

import com.analog.alex.security.user.model.User
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {

    @CacheEvict("userCache")
    override fun <S : User?> save(entity: S): S

    @CacheEvict("userCache")
    override fun <S : User?> insert(entity: S): S

    @CacheEvict("userCache")
    override fun delete(entity: User)

    @Cacheable("userCache")
    fun findByUsername(username: String): User?
}