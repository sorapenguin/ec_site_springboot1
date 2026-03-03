package com.example.ecsite.product;

import java.io.IOException;
import java.util.List;

import com.example.ecsite.model.Product;
import com.example.ecsite.service.stock.StockService;
import com.example.ecsite.service.product.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/products") // 管理者用 URL
public class ProductController {

    private final ProductService productService;
    private final StockService stockService;

    public ProductController(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;
    }

    // ------------------------
    // 商品一覧表示（GET）
    // ------------------------
    @GetMapping
    public String productList(Model model, @RequestParam(required = false) String msg) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        if ("success".equals(msg)) model.addAttribute("success", "操作が成功しました");
        if ("error".equals(msg)) model.addAttribute("error", "操作に失敗しました");

        return "product_manage"; // Thymeleaf テンプレート
    }

    // ------------------------
    // 商品追加フォーム表示（GET）
    // ------------------------
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_add"; // Thymeleaf テンプレート
    }

    // ------------------------
    // 商品追加処理（POST）
    // ------------------------
@PostMapping("/add")
public String addProduct(
        @RequestParam String name,
        @RequestParam double price,
        @RequestParam(defaultValue = "0") int stockQty,
        @RequestParam(defaultValue = "false") boolean publicFlg,
        @RequestParam MultipartFile image
) {
    try {
        productService.addProduct(name, price, stockQty, publicFlg, image);
        return "redirect:/admin/products?msg=success";
    } catch (IOException | RuntimeException e) {
        e.printStackTrace();
        return "redirect:/admin/products?msg=error";
    }
}

// ------------------------
// 商品編集フォーム表示
// ------------------------
@GetMapping("/edit/{id}")
public String showEditForm(@PathVariable Long id, Model model) {
    Product product = productService.getProductById(id);
    model.addAttribute("product", product);
    return "product_edit";
}

    // ------------------------
    // 公開フラグ更新（POST）
    // ------------------------
    @PostMapping("/update-flag")
    public String updateFlag(@RequestParam Long productId, @RequestParam boolean publicFlg) {
        productService.updatePublicFlg(productId, publicFlg);
        return "redirect:/admin/products?msg=success";
    }

// ------------------------
// 商品削除
// ------------------------
@PostMapping("/delete/{id}")
public String delete(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "redirect:/admin/products?msg=success";
}
}