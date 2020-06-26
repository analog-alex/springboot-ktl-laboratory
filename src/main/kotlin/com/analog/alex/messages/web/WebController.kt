package com.analog.alex.messages.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class WebController {

    @GetMapping(
            value = ["ping"],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun ping() = "Up and running at ${Instant.now()}"
}

