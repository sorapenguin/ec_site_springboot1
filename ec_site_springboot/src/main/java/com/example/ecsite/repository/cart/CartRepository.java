package com.example.ecsite.repository.cart;

import com.example.ecsite.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 特定のユーザーのカートを取得するメソッド
    List<Cart> findByUserId(Long userId);
}