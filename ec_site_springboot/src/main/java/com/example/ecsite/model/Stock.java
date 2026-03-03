package com.example.ecsite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity; // 在庫数

    // 商品と紐づけ（多対一）
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // ----------------------
    // コンストラクタ
    // ----------------------

    // デフォルトコンストラクタ（JPA用）
    public Stock() {}

    // Product と数量を指定して作るコンストラクタ
    public Stock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // ----------------------
    // getter / setter
    // ----------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}