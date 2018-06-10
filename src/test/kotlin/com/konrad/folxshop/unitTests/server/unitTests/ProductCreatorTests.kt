package com.konrad.folxshop.unitTests.server.unitTests

import com.konrad.folxshop.models.Product
import com.konrad.folxshop.models.ProductStatus
import com.konrad.folxshop.projectUtils.RestUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class ProductCreatorTests : ControllerUnitTests(){

    @Test
    fun createProductTest(){
        val product =  Product(1, "bike", 123, ProductStatus.INSTOCK.toString())
        val productJSON = objectMapper.writeValueAsString(product)

        val requestBuilder = put(RestUtils.CREATE).contentType(MediaType.APPLICATION_JSON).content(productJSON)
        mock.perform(requestBuilder).andExpect(status().isCreated)
    }

    @Test
    fun createProductTest_WITHDROWN(){
        val product =  Product(1, "bike", 123, ProductStatus.WITHDRAWN.toString())
        val productJSON = objectMapper.writeValueAsString(product)

        val requestBuilder = put(RestUtils.CREATE).contentType(MediaType.APPLICATION_JSON).content(productJSON)
        mock.perform(requestBuilder)
                .andExpect(status().isBadRequest)
                .andExpect(status().reason("Product with status WITHDRAWN"))
    }

    @Test
    fun createProductTest_NameNotAllowed(){
        val name = anyString()
        val product =  Product(1, name, 123, ProductStatus.INSTOCK.toString())
        val productJSON = objectMapper.writeValueAsString(product)

        Mockito.`when`(productDao.findByName(name)).thenReturn(Optional.of(product))
        val requestBuilder = put(RestUtils.CREATE).contentType(MediaType.APPLICATION_JSON).content(productJSON)
        mock.perform(requestBuilder)
                .andExpect(status().isBadRequest)
                .andExpect(status().reason("Name already exists"))
    }

    @Test
    fun createProductTest_IdNotFound(){
        val id = anyLong()
        val product =  Product(id = id)
        val productJSON = objectMapper.writeValueAsString(product)

        Mockito.`when`(productDao.findById(id)).thenReturn(Optional.of(product))
        val requestBuilder = put(RestUtils.CREATE).contentType(MediaType.APPLICATION_JSON).content(productJSON)
        mock.perform(requestBuilder)
                .andExpect(status().isBadRequest)
                .andExpect(status().reason("Name already exists"))
    }
}