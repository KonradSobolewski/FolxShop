package com.konrad.folxshop.models

import java.util.*
import javax.persistence.*

@Entity(name = "product")
data class Product(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = -1,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "price")
    var price : Long = -1,

    @Column(name = "status")
    var status : String = ProductStatus.INSTOCK.toString(),

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    var dateCreated: Date = Date()
)