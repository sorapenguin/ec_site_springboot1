package com.example.ecsite.repository.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecsite.model.Stock;
import com.example.ecsite.model.Product;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProduct(Product product); // ←ここ
}