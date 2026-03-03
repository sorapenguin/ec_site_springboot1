package com.example.ecsite.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.ecsite.service.stock.StockService;

@Controller
public class PurchaseController {

    @Autowired
    private StockService stockService;

    /**
     * カート購入処理
     * productId と quantity を受け取り在庫を減らす
     */
    @PostMapping("/purchase")
    public String purchase(@RequestParam Long productId,
                           @RequestParam int quantity,
                           Model model) {

        boolean success = stockService.reduceStock(productId, quantity);

        if(success) {
            model.addAttribute("message", "購入成功！在庫を減らしました。");
        } else {
            model.addAttribute("message", "購入失敗: 在庫が足りません。");
        }

        // 購入後はカート画面に戻す
        model.addAttribute("cartItems", java.util.Collections.emptyList());
        return "cart_page"; // cart.html に遷移
    }
}