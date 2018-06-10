package com.konrad.folxshop.unitTests.client

import com.konrad.folxshop.models.Product
import com.konrad.folxshop.models.ProductStatus
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus

@RunWith(SpringRunner::class)
class ClientCreateTests : ControllerUnitTestsClient() {
    @Test
    fun createProductTest(){
        server.expect(requestTo(clientService.uriProvider.createProduct()))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.CREATED))

        clientService.createProduct(Product( name ="bike", price =123,status =  ProductStatus.INSTOCK.toString()))
        server.verify()
    }

    @Test
    fun createProductTest_Withdrawn() {
        server.expect(requestTo(clientService.uriProvider.createProduct()))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST))
        clientService.createProduct(Product( name ="bike", price =123,status =  ProductStatus.WITHDRAWN.toString()))
        server.verify()
    }

}