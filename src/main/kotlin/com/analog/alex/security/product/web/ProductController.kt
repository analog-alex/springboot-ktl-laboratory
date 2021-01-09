package com.analog.alex.security.product.web

import com.analog.alex.security.product.model.Product
import com.analog.alex.security.product.repository.ProductRepository
import com.analog.alex.security.utils.uuid
import com.analog.alex.security.errors.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping(value = ["products"])
class ProductController(
    private val productRepository: ProductRepository
) {

    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findAll() = productRepository.findAll()

    @GetMapping(
        value = ["/{product}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findById(@PathVariable product: Product?): Product {
        return product ?: throw EntityNotFoundException()
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody entity: Product) = productRepository.save(
        entity.also {
            it.id = uuid()
            it.createdDate = LocalDateTime.now()
        }
    )

    @PutMapping(
        value = ["/{product}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable product: Product?, @RequestBody entity: Product): Product {
        product ?: throw EntityNotFoundException()

        return productRepository.save(entity.copy(
            id = product.id!!,
            createdDate = product.createdDate,
            modifiedDate = LocalDateTime.now()
        ))
    }

    @DeleteMapping(value = ["/{id}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = productRepository.deleteById(id)
}