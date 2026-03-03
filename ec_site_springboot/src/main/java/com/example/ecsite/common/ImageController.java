package com.example.ecsite.common;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    // 画像ディレクトリのパス（必要に応じて変更）
    private final Path imageDir = Paths.get("C:/ワーク制作用Springboot/ec_site_springboot/src/main/resources/static/images");

    /**
     * 商品画像を返す
     * 例: GET /images/product1.jpg
     */
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = imageDir.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}