package com.konrad.folxshop.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Name already exists")
class NameNotAllowed(message : String = "Error") : Exception(message)