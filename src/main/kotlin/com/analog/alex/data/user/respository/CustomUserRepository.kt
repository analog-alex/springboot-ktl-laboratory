package com.analog.alex.data.user.respository

import com.analog.alex.data.user.model.User
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.Predicate

interface CustomUserRepository {
    fun fundByFields(username: String?, role: String?): Collection<User>
}

@Repository
class CustomUserRepositoryImpl(
    val entityManager: EntityManager
) : CustomUserRepository {

    override fun fundByFields(username: String?, role: String?): Collection<User> {
        val criteriaBuilder = entityManager.criteriaBuilder
        val queryUser = criteriaBuilder.createQuery(User::class.java)

        val rootUser = queryUser.from(User::class.java)
        val predicates = mutableListOf<Predicate>()

        if (username != null) {
            predicates.add(criteriaBuilder.equal(rootUser.get<String>("username"), username))
        }

        if (role != null) {
            predicates.add(criteriaBuilder.equal(rootUser.get<String>("role"), role))
        }

        queryUser.where(*predicates.toTypedArray())

        return entityManager.createQuery(queryUser).resultList
    }
}