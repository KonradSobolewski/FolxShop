package com.konrad.folxshop.client

import com.konrad.folxshop.projectUtils.client.ClientUtils
import com.konrad.folxshop.projectUtils.client.ModeUtils
import com.konrad.folxshop.models.Product
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
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @GetMapping(value = ["/", ClientUtils.HOME])
    fun home(request: HttpServletRequest) : String {
        request.setAttribute("mode", ModeUtils.HOME)
        return "index"
    }

    @GetMapping(ClientUtils.GETALL)
    fun allProducts(request: HttpServletRequest) : String {
        request.run {
            setAttribute("products",clientService.getAllProducts())
            setAttribute("mode", ModeUtils.GETALL)
        }
        return "index"
    }

    @GetMapping(ClientUtils.NEW)
    fun newProduct(request: HttpServletRequest) : String {
        request.setAttribute("mode", ModeUtils.NEW)
        return "index"
    }

    @PostMapping(ClientUtils.SAVE)
    fun saveProduct(@ModelAttribute product: Product,bindingResult: BindingResult, request: HttpServletRequest): String {
        product.dateCreated = Date()
        clientService.updateProduct(product)
        request.run {
            setAttribute("products",clientService.getAllProducts())
            setAttribute("mode", ModeUtils.GETALL)
        }
        return "index"
    }

    @PostMapping(ClientUtils.CREATE)
    fun createProduct(@ModelAttribute product: Product,bindingResult: BindingResult, request: HttpServletRequest): String {
        product.dateCreated = Date()
        clientService.createProduct(product)
        request.run {
            setAttribute("products",clientService.getAllProducts())
            setAttribute("mode", ModeUtils.GETALL)
        }
        return "index"
    }

    @GetMapping(ClientUtils.UPDATE)
    fun updateProduct(@RequestParam(value = "id") id: Long , request: HttpServletRequest) : String {
        request.setAttribute("product",clientService.getProductById(id))
        request.setAttribute("mode", ModeUtils.UPDATE)
        return "index"
    }

    @GetMapping(ClientUtils.DELETE)
    fun deleteProduct(@RequestParam(value = "id") id: Long , request: HttpServletRequest) : String {
        clientService.deleteProductByID(id)
        request.run {
            setAttribute("products",clientService.getAllProducts())
            setAttribute("mode", ModeUtils.GETALL)
        }
        return "index"
    }
}