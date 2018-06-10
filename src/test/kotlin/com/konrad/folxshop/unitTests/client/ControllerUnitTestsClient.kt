package com.konrad.folxshop.unitTests.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.konrad.folxshop.client.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.test.web.client.MockRestServiceServer

@RestClientTest(ClientService::class)
abstract class ControllerUnitTestsClient {

    @Autowired
    protected lateinit var clientService: ClientService

    @Autowired
    lateinit var server: MockRestServiceServer

    @Autowired
    lateinit var objectMapper: ObjectMapper
}