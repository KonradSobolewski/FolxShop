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
import org.springframework.web.client.HttpStatusCodeException

@Service
class ClientService {

    @Autowired
    lateinit var restTemplate: RestTemplate
    val uriProvider = UriProvider()

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    fun createProduct(product: Product) {
        try {
             restTemplate.exchange(
                    uriProvider.createProduct(),
                    HttpMethod.PUT,
                    HttpEntity(product),
                    Product::class.java
            )
        }
        catch (exception: HttpStatusCodeException) {
        }
    }

    fun updateProduct(product: Product) {
        try {
             restTemplate.exchange(
                    uriProvider.updateProduct(),
                    HttpMethod.PATCH,
                    HttpEntity(product),
                    Product::class.java
            )
        }
        catch (exception: HttpStatusCodeException) {
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
            restTemplate.getForEntity(uriProvider.getById(id), Product::class.java).body
        }
        catch (exception: HttpStatusCodeException) {
            null
        }
    }

    fun getProductByName(name: String): Product? {
        return try {
            restTemplate.getForEntity(uriProvider.getByName(name), Product::class.java).body
        }
        catch (exception: HttpStatusCodeException) {
            null
        }
    }

    fun deleteProductByID(id: Long) {
        try {
            restTemplate.exchange(
                    uriProvider.deleteById(id),
                    HttpMethod.DELETE,
                    HttpEntity.EMPTY,
                    Unit::class.java
            )
        }
        catch (exception: HttpStatusCodeException) { }
    }

    fun deleteProductByName(name: String) {
        try {
             restTemplate.exchange(
                    uriProvider.deleteByName(name),
                    HttpMethod.DELETE,
                    HttpEntity.EMPTY,
                    Unit::class.java)
        }
        catch (exception: HttpStatusCodeException) { }
    }

}