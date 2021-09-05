package com.analog.alex.security.authentication.web

import com.analog.alex.security.authentication.service.AuthenticationService
import com.analog.alex.security.user.model.UserCredentials
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping(
        value = ["/register"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun registerUser(@RequestBody credentials: UserCredentials) = authenticationService.registerUser(credentials)

    @PostMapping(
        value = ["/login"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun loginUser(@RequestBody credentials: UserCredentials) = authenticationService.login(credentials)


    @GetMapping(
        value = ["/validate"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun validateUser(@RequestParam token: String) = authenticationService.validateToken(token)
}