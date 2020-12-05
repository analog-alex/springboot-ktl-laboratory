package com.analog.alex.security.product.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Product(
    @Id var id: String? = null,
    val name: String,
    val price: Int,
    val company: String,
    var createdDate: LocalDateTime? = null,
    var modifiedDate: LocalDateTime? = null
)