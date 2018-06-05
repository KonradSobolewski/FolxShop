package com.konrad.folxshop.server

import com.konrad.folxshop.exceptions.CantFindProduct
import com.konrad.folxshop.exceptions.ProductNotAllowed
import com.konrad.folxshop.projectUtils.ConstUtils
import com.konrad.folxshop.projectUtils.ModeUtils
import com.konrad.folxshop.exceptions.NameNotAllowed
import com.konrad.folxshop.models.Product
import com.konrad.folxshop.models.ProductStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class MainController {

    @Autowired
    private lateinit var productService: ProductService

    @GetMapping(value = ["/",ConstUtils.HOME])
    fun home(request: HttpServletRequest) : String {
        request.setAttribute("mode",ModeUtils.HOME)
        return "index"
    }

    @GetMapping(ConstUtils.GETALL)
    fun allProducts(request: HttpServletRequest) : String {
        request.run {
            setAttribute("products",productService.findAll())
            setAttribute("mode",ModeUtils.GETALL)
        }
        return "index"
    }

    @GetMapping(ConstUtils.NEW)
    fun newProduct(request: HttpServletRequest) : String {
        request.setAttribute("mode",ModeUtils.NEW)
        return "index"
    }

    @PostMapping(ConstUtils.SAVE)
    fun saveProduct(@ModelAttribute product: Product,bindingResult: BindingResult, request: HttpServletRequest): String {
        product.dateCreated = Date()
        when {
            productService.finByName(product.name) != productService.findByID(product.id) -> throw NameNotAllowed()
            product.status == ProductStatus.WITHDRAWN.toString() -> throw ProductNotAllowed()
            else -> productService.save(product)
        }
        request.run {
            setAttribute("products",productService.findAll())
            setAttribute("mode",ModeUtils.GETALL)
        }
        return "index"
    }

    @GetMapping(ConstUtils.UPDATE)
    fun updateProduct(@RequestParam(value = "id") id: Long , request: HttpServletRequest) : String {
        request.setAttribute("product",productService.findByID(id).takeIf {
            it.isPresent
        }?.get()?: throw CantFindProduct())
        request.setAttribute("mode",ModeUtils.UPDATE)
        return "index"
    }

    @GetMapping(ConstUtils.DELETE)
    fun deleteProduct(@RequestParam(value = "id") id: Long , request: HttpServletRequest) : String {
        when {
            productService.findByID(id).isPresent -> productService.deleteByID(id)
            else -> throw CantFindProduct()
        }
        request.run {
            setAttribute("products",productService.findAll())
            setAttribute("mode",ModeUtils.GETALL)
        }
        return "index"
    }
}