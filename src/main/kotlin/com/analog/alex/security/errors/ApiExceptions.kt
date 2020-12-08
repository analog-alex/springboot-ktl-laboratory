package com.analog.alex.security.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class EntityNotFoundException : RuntimeException("Requested entity with was not found.")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserDoesNotExistException(username: String) : RuntimeException("User '$username' does not exist.")
