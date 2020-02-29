package com.analog.alex.messages.error

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequest(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.FORBIDDEN)
class Forbidden(message: String) : RuntimeException(message)
