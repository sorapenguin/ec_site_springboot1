package com.example.ecsite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 商品名
    private String description; // 商品説明
    private double price;       // 価格
    private int stock;          // 在庫数

    private boolean publicFlg;  // 公開フラグ
    private String image;       // 画像ファイル名

    // ------------------------
    // コンストラクタ
    // ------------------------
    public Product() {}

    public Product(String name, String description, double price, int stock, boolean publicFlg, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.publicFlg = publicFlg;
        this.image = image;
    }

    // ------------------------
    // getter / setter
    // ------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isPublicFlg() { return publicFlg; }
    public void setPublicFlg(boolean publicFlg) { this.publicFlg = publicFlg; } // 追加

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; } // 追加
}