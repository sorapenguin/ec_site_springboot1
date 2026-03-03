package com.example.ecsite.repository.product;

import com.example.ecsite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 例: 商品名で検索するメソッド
    List<Product> findByNameContaining(String name);

    // 例: 在庫がある商品だけ取得するメソッド
    List<Product> findByStockGreaterThan(int stock);
}