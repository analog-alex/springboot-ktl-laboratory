package com.analog.alex.data.user.respository

import com.analog.alex.data.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface UserRepository : CrudRepository<User, Long>
