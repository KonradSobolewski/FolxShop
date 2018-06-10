package com.konrad.folxshop.unitTests.client

import com.konrad.folxshop.models.Product
import com.konrad.folxshop.models.ProductStatus
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RunWith(SpringRunner::class)
class ClientReadTests : ControllerUnitTestsClient() {
    private val product1 = Product(1, "prod1", 123, ProductStatus.WITHDRAWN.toString())
    private val product2 = Product(2, "prod2", 234, ProductStatus.INSTOCK.toString())
    private val products = listOf(product1, product2)


    @Test
    fun readAllProductsTest() {
        val productsJSON = objectMapper.writeValueAsString(products)
        server.expect(requestTo(clientService.uriProvider.readAll()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(productsJSON, MediaType.APPLICATION_JSON))
        val returnedProducts = clientService.getAllProducts()
        assertEquals(products, returnedProducts)
        server.verify()
    }

    @Test
    fun readByIdTest() {
        val id = product1.id
        val product1JSON = objectMapper.writeValueAsString(product1)
        server.expect(requestTo(clientService.uriProvider.getById(id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(product1JSON, MediaType.APPLICATION_JSON))
        val returnedProduct = clientService.getProductById(id)
        assertEquals(product1, returnedProduct)
        server.verify()
    }

    @Test
    fun readByIdTest_NotFound() {
        val id = 20L
        server.expect(requestTo(clientService.uriProvider.getById(id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND))
        clientService.getProductById(id)
        server.verify()
    }
}