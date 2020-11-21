package com.analog.alex.data.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InsufficientFunds(id: Long): RuntimeException("Unable to perform the transaction. Costumer(id='$id') has insufficient funds!")

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFound(id: Long, clazz: Class<*>): RuntimeException("Entity(id='$id') of type {${clazz.simpleName}} not found!")