package com.konrad.folxshop.client

import com.konrad.folxshop.models.Product
import com.konrad.folxshop.projectUtils.UriProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

@Service
class ClientService {

    @Autowired
    lateinit var restTemplate: RestTemplate
    val uriProvider = UriProvider()
    val logger: Logger = LoggerFactory.getLogger(ClientService::class.java)

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    fun createProduct(product: Product) {
        try {
            val response = restTemplate.exchange(
                    uriProvider.createProduct(),
                    HttpMethod.PUT,
                    HttpEntity(product),
                    Product::class.java
            )
            logger.info(response.toString())
        }
        catch (exception: HttpStatusCodeException) {
            if (exception.statusCode == HttpStatus.CONFLICT) {
                getProductById(product.id)?.takeIf { it.price < product.price }?.let {
                    updateProduct(product) }
            }
        }
    }

    fun updateProduct(product: Product) {
        try {
            val response = restTemplate.exchange(
                    uriProvider.updateProduct(),
                    HttpMethod.PATCH,
                    HttpEntity(product),
                    Product::class.java
            )
            logger.info(response.toString())
        }
        catch (exception: HttpStatusCodeException) {
            logger.info(exception.responseBodyAsString)
        }
    }

    fun getAllProducts(): List<Product>? {
        val response = restTemplate.exchange(
                uriProvider.readAll(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                object: ParameterizedTypeReference<List<Product>>(){}
        )
        return response.body
    }

    fun getProductById(id: Long): Product? {
        return try {
            restTemplate.getForEntity(uriProvider.getById(id), Product::class.java).run {
                logger.info(toString())
                body
            }
        }
        catch (exception: HttpStatusCodeException) {
            logger.info(exception.responseBodyAsString)
            null
        }
    }

    fun getProductByName(name: String): Product? {
        return try {
            restTemplate.getForEntity(uriProvider.getByName(name), Product::class.java).run {
                logger.info(toString())
                body
            }
        }
        catch (exception: HttpStatusCodeException) {
            logger.info(exception.responseBodyAsString)
            null
        }
    }

    fun deleteProductByID(id: Long) {
        try {
            val response = restTemplate.exchange(
                    uriProvider.deleteById(id),
                    HttpMethod.DELETE,
                    HttpEntity.EMPTY,
                    Unit::class.java
            )
            logger.info(response.toString())
        }
        catch (exception: HttpStatusCodeException) {
            logger.info(exception.responseBodyAsString)
        }
    }

    fun deleteProductByName(name: String) {
        try {
            val response = restTemplate.exchange(
                    uriProvider.deleteByName(name),
                    HttpMethod.DELETE,
                    HttpEntity.EMPTY,
                    Unit::class.java)
            logger.info(response.toString())
        }
        catch (exception: HttpStatusCodeException) {
            logger.info(exception.responseBodyAsString)
        }
    }

}