package com.konrad.folxshop.models

import javax.persistence.*

@Entity(name = "user")
data class User (
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id :Long = -1,

        @Column(name = "email",nullable = false)
        var email: String = "",

        @Column(name = "password",nullable = false)
        var password: String = "",

        @Column(name = "name")
        var Name: String = "",

        @Column(name = "surname")
        var surname: String = ""
)