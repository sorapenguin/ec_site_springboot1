package com.example.ecsite.service.cart;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecsite.model.Cart;
import com.example.ecsite.model.Product;
import com.example.ecsite.model.Stock;
import com.example.ecsite.repository.cart.CartRepository;
import com.example.ecsite.repository.product.ProductRepository;
import com.example.ecsite.service.stock.StockService;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockService stockService; // ← StockServiceに変更

    // カートに商品を追加
    public void addToCart(Cart cart, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    // ユーザーのカート一覧取得
    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // 購入処理（在庫減算）
    @Transactional
    public boolean purchaseCart(Long userId) {
        List<Cart> cartItems = getCartByUserId(userId);

        // 在庫チェック
        for (Cart item : cartItems) {
            Stock stock = stockService.getStockByProductId(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Stock not found"));
            if (stock.getQuantity() < item.getQuantity()) {
                // 在庫不足
                return false;
            }
        }

        // 在庫減算
        for (Cart item : cartItems) {
            boolean success = stockService.decreaseStock(item.getProduct().getId(), item.getQuantity());
            if (!success) {
                // 減算失敗（念のためロールバック）
                throw new RuntimeException("Failed to decrease stock for product: " + item.getProduct().getId());
            }
        }

        // カートをクリア
        cartRepository.deleteAll(cartItems);
        return true;
    }
}