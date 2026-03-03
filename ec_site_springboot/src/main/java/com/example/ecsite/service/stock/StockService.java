package com.example.ecsite.service.stock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecsite.model.Stock;
import com.example.ecsite.model.Product;
import com.example.ecsite.repository.stock.StockRepository;
import com.example.ecsite.repository.product.ProductRepository;

import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public StockService(StockRepository stockRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    // 商品IDに対応する在庫取得
    public Optional<Stock> getStockByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return Optional.empty();
        return stockRepository.findByProduct(product);
    }

    // 在庫減算
    @Transactional
    public boolean decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return stockRepository.findByProduct(product)
            .map(stock -> {
                if (stock.getQuantity() < quantity) return false;
                stock.setQuantity(stock.getQuantity() - quantity);
                stockRepository.save(stock);
                return true;
            }).orElse(false);
    }

    // reduceStock は decreaseStock と同じ
    @Transactional
    public boolean reduceStock(Long productId, int quantity) {
        return decreaseStock(productId, quantity);
    }

    // 在庫補充
    @Transactional
    public void increaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Stock stock = stockRepository.findByProduct(product).orElse(new Stock(product, 0));
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
    }

    // 在庫を指定数量に設定
    @Transactional
    public void setStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Stock stock = stockRepository.findByProduct(product).orElse(new Stock(product, 0));
        stock.setQuantity(quantity);
        stockRepository.save(stock);
    }
}