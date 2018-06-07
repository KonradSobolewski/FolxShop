package com.konrad.folxshop.projectUtils

class UriProvider {
    private val host = "http://localhost:8080"
    fun readAll() = host + RestUtils.GETALL
    fun createProduct() = host + RestUtils.CREATE
    fun updateProduct() = host + RestUtils.UPDATE
    fun getById(id: Long) = host + RestUtils.GETBYID + "?id=$id"
    fun getByName(name : String) = host + RestUtils.GETBYNAME  + "?id=$name"
    fun deleteById(id : Long) = host + RestUtils.DELETEBYID  + "?id=$id"
    fun deleteByName(name : String) = host + RestUtils.DELETEBYNAME  + "?id=$name"
}