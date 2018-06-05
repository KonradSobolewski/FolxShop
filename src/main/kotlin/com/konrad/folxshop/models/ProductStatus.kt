package com.konrad.folxshop.models

import java.io.Serializable

enum class ProductStatus(val status : String) : Serializable {
    INSTOCK("INSTOCK"),
    OUTOFSTOCK("OUTOFSTOCK"),
    WITHDRAWN("WITHDRAWN")
}