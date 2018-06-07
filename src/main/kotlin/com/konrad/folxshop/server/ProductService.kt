package com.konrad.folxshop.server

import com.konrad.folxshop.dao.ProductDao
import com.konrad.folxshop.models.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ProductService(@Autowired var productDao: ProductDao) {
    fun save(product: Product) = productDao.save(product)
    fun findAll(): List<Product> = productDao.findAll()
    fun findByID(id: Long) = productDao.findById(id)
    fun deleteByID(id: Long) = productDao.deleteById(id)
    fun findByName(name : String) = productDao.findByName(name)
    fun deleteByName(name: String) = productDao.deleteByName(name)
}