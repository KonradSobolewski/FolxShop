package com.konrad.folxshop.dao

import com.konrad.folxshop.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductDao : JpaRepository<Product,Long> {
    fun findByName (name:String) : Optional<Product>
    fun deleteByName(name: String)
}

