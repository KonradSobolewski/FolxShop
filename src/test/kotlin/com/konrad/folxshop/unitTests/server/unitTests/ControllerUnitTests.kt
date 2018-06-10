package com.konrad.folxshop.unitTests.server.unitTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.konrad.folxshop.dao.ProductDao
import com.konrad.folxshop.server.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
abstract class ControllerUnitTests {

    @MockBean
    @Autowired
    protected lateinit var productDao: ProductDao

    @Autowired
    protected lateinit var mock: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

}