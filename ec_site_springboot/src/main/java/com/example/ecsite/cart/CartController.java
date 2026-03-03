package com.example.ecsite.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ecsite.service.stock.StockService;

@Controller
public class CartController {

    @Autowired
    private StockService stockService;

    /**
     * カート画面を表示（仮で空のリスト）
     */
    @GetMapping("/cart")
    public String showCart(Model model) {
        // 今回は仮に空のカート
        model.addAttribute("cartItems", java.util.Collections.emptyList());
        return "cart_page";
    }

    /**
     * 購入ボタン押下時
     */
    @PostMapping("/cart/checkout")
    public String checkout(@RequestParam Long productId,
                           @RequestParam int quantity,
                           Model model) {

        boolean success = stockService.reduceStock(productId, quantity);

        if(success) {
            model.addAttribute("message", "購入成功！在庫を減らしました。");
        } else {
            model.addAttribute("message", "購入失敗: 在庫が足りません。");
        }

        // 購入後は再びカート画面に戻す
        model.addAttribute("cartItems", java.util.Collections.emptyList());
        return "cart_page";
    }
}