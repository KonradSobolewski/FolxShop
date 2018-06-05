package com.konrad.folxshop.server

import com.konrad.folxshop.models.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @RequestMapping("/hello")
    fun home(): String =
       "index"

    @RequestMapping("/create")
    fun createProduct(@RequestParam(value = "name") name : String,
                      @RequestParam(value = "price") price: Long) : String{
        val product = Product(name = name, price = price)
        productService.save(product)
        return "Product Saved"
    }

    @GetMapping("/ByName")
    fun getByName(@RequestParam(value = "name") name : String) :Product? {
        return  productService.finByName(name).takeIf {
            it.isPresent
        }?.get()
    }

    @DeleteMapping("/deleteByName")
    fun deleteProductByName(@RequestParam(value = "name") name : String): String{
        productService.deleteByName(name)
        return "Product deleted"
    }

    @RequestMapping("/getAll")
    fun getProducts() : List<Product> =
         productService.findAll()


    @RequestMapping("/getById")
    fun getProductById(@RequestParam(value = "id") id: Long) : Product? {
        return productService.findByID(id).takeIf {
            it.isPresent
        }?.get()
    }

    @RequestMapping("/update")
    fun updateProduct(@RequestBody product: Product) : String {
        productService.save(product)
        return "Product Updated"
    }

    @RequestMapping("/deleteById")
    fun deleteProductById(@RequestParam(value = "id") id : Long):String{
        productService.deleteByID(id)
        return "Product deleted"
    }
}