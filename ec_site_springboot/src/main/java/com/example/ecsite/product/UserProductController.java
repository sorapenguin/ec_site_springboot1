package com.example.ecsite.product;

import com.example.ecsite.model.Product;
import com.example.ecsite.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserProductController {

    private final ProductService productService;

    public UserProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product_list")
    public String productList(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product_list";  // src/main/resources/templates/product_list.html
    }
}