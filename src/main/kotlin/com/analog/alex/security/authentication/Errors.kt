package com.analog.alex.security.authentication

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus

class NoTokenException : AuthenticationException("No JWT found in 'Authorization' header.")
class MalformedTokenException : AuthenticationException("Auth token does not come in the format: 'Bearer <token>'.")

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthenticationFailedException : AuthenticationException("Authentication failed. Check credentials.")


@ResponseStatus(HttpStatus.CONFLICT)
class DuplicatedUsernameException : AuthenticationException("Username already taken")