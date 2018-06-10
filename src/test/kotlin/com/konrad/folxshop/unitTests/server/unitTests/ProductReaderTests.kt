package com.konrad.folxshop.unitTests.server.unitTests

import com.konrad.folxshop.models.Product
import com.konrad.folxshop.models.ProductStatus
import com.konrad.folxshop.projectUtils.RestUtils
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class ProductReaderTests : ControllerUnitTests() {

    @Test
    fun testReadAllProducts() {
        val mockProductList: List<Product> = listOf(
                Product(1, "bike", 123, ProductStatus.INSTOCK.toString()),
                Product(2, "ball", 456),
                Product(3, status = ProductStatus.OUTOFSTOCK.toString())
        )
        Mockito.`when`(productDao.findAll()).thenReturn(mockProductList)

        val requestBuilder: RequestBuilder = get(RestUtils.GETALL)
        val result: MvcResult = mock.perform(requestBuilder).andExpect(status().isOk).andReturn()

        val returnedMockProductList: List<Product> = objectMapper.readValue(
                result.response.contentAsString,
                objectMapper.typeFactory.constructCollectionType(List::class.java, Product::class.java)
        )
        assertEquals(mockProductList, returnedMockProductList)
    }

    @Test
    fun getProductById(){
        val id = anyLong()
        val mockProduct : Optional<Product> = Optional.of(Product(id,"bike",123,ProductStatus.INSTOCK.toString()))
        Mockito.`when`(productDao.findById(id)).thenReturn(mockProduct)

        val requestBuilder = get(RestUtils.GETBYID).param("id",id.toString())
        val result: MvcResult = mock.perform(requestBuilder).andExpect(status().isOk).andReturn()

        val product = objectMapper.readValue(
                result.response.contentAsString,
                Product::class.java
        )
        assertEquals(mockProduct.get(),product)
    }

    @Test
    fun getProductById_IdNotFound(){
        val id = anyLong()
        Mockito.`when`(productDao.findById(id)).thenReturn(Optional.empty())

        val requestBuilder = get(RestUtils.GETBYID).param("id",id.toString())
        mock.perform(requestBuilder)
                .andExpect(status().isNotFound)
                .andExpect(status().reason("Can't find product in database"))
    }
}