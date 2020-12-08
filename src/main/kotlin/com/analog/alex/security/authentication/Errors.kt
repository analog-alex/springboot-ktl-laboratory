package com.analog.alex.security.authentication

import org.springframework.security.core.AuthenticationException

class NoTokenException : AuthenticationException("No JWT found in 'Authorization' header.")
class MalformedTokenException : AuthenticationException("Auth token does not come in the format: 'Bearer <token>'.")
class AuthenticationFailedException : AuthenticationException("Authentication failed. Check credentials.")