package com.konrad.folxshop.server

import com.konrad.folxshop.exceptions.CantFindProduct
import com.konrad.folxshop.exceptions.NameNotAllowed
import com.konrad.folxshop.exceptions.ProductNotAllowed
import com.konrad.folxshop.models.Product
import com.konrad.folxshop.models.ProductStatus
import com.konrad.folxshop.projectUtils.RestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @PutMapping(RestUtils.CREATE)
    fun createProduct(@RequestBody product: Product){
        when {
            productService.findByName(product.name) != productService.findByID(product.id) -> throw NameNotAllowed()
            product.status == ProductStatus.WITHDRAWN.toString() -> throw ProductNotAllowed()
            else -> productService.save(product)
        }
    }

    @PostMapping(RestUtils.UPDATE)
    fun updateProduct(@RequestBody product: Product){
        when {
            !productService.findByID(product.id).isPresent -> throw CantFindProduct()
            else -> productService.save(product)
        }
    }

    @RequestMapping(RestUtils.GETALL)
    fun getAllProducts() : List<Product> = productService.findAll()

    @GetMapping(RestUtils.GETBYID)
    fun getProductById(@RequestParam(value = "id") id: Long) : Product? {
        return productService.findByID(id).takeIf {
            it.isPresent
        }?.get() ?: throw CantFindProduct()
    }

    @GetMapping(RestUtils.GETBYNAME)
    fun getProductByName(@RequestParam(value = "name") name : String) :Product? {
        return  productService.findByName(name).takeIf {
            it.isPresent
        }?.get() ?: throw CantFindProduct()
    }

    @DeleteMapping(RestUtils.DELETEBYNAME)
    fun deleteProductByName(@RequestParam(value = "name") name : String){
        when{
            !productService.findByName(name).isPresent -> throw CantFindProduct()
            else -> productService.deleteByName(name)
        }
    }

    @DeleteMapping(RestUtils.DELETEBYID)
    fun deleteProductById(@RequestParam(value = "id") id : Long){
        when{
            !productService.findByID(id).isPresent -> throw CantFindProduct()
            else -> productService.deleteByID(id)
        }
    }
}