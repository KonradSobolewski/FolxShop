package com.konrad.folxshop.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Product with status WITHDRAWN")
class ProductNotAllowed(message : String = "Error") : Exception(message)