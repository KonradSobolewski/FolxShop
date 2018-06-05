package com.konrad.folxshop.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "Can't find product in database")
class CantFindProduct(message : String = "Error") : Exception(message)