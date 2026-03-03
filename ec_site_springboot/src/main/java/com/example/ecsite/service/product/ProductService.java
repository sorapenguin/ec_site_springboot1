package com.example.ecsite.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecsite.repository.product.ProductRepository;
import com.example.ecsite.model.Product;
import com.example.ecsite.service.stock.StockService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockService stockService;
    private final Path uploadDir = Paths.get("uploads"); // 画像保存フォルダ

    public ProductService(ProductRepository productRepository, StockService stockService) {
        this.productRepository = productRepository;
        this.stockService = stockService;
        try { Files.createDirectories(uploadDir); } catch (IOException e) {}
    }

    // 全商品取得
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // IDで商品取得
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    // 商品追加
    @Transactional
    public void addProduct(String name, double price, int stockQty, boolean publicFlg, MultipartFile image) throws IOException {
        String filename = image.getOriginalFilename();
        if (filename == null) throw new RuntimeException("画像が選択されていません");
        String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
            throw new RuntimeException("画像形式が不正です");
        }

        String storedName = filename.substring(0, filename.lastIndexOf('.')) + "_" + System.currentTimeMillis() + "." + ext;
        Path filePath = uploadDir.resolve(storedName);
        image.transferTo(filePath);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setPublicFlg(publicFlg); // Product に追加済み前提
        product.setImage(storedName);    // Product に追加済み前提
        productRepository.save(product);

        stockService.increaseStock(product.getId(), stockQty);
    }

    // 公開フラグ更新
    @Transactional
    public void updatePublicFlg(Long productId, boolean publicFlg) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setPublicFlg(publicFlg);
        productRepository.save(product);
    }

    // 商品削除
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}