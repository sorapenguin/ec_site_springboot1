package com.example.ecsite.repository.user;

import com.example.ecsite.model.User; // ← ここがポイント
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
       boolean existsByUsername(String username); // これを追加

}