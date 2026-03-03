package com.example.ecsite.service.cart;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PurchaseService {

    @Autowired
    private CartService cartService;

    /**
     * 指定ユーザーのカートを購入処理する
     * 在庫不足があれば false を返す
     * @param userId
     * @return boolean 成功なら true、在庫不足なら false
     */
    public boolean purchaseCart(Long userId) {
        return cartService.purchaseCart(userId);
    }
}