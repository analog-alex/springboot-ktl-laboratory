package com.analog.alex.messages.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "costumers")
data class Costumer(
        @Id
        val id: String? = null,

        val name: String,

        val age: Int? = null,

        val country: String? = null
)