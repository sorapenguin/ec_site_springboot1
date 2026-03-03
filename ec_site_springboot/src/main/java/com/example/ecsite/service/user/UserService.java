package com.example.ecsite.service.user;

import com.example.ecsite.model.User; 
import com.example.ecsite.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 認証
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    // ユーザー名の重複チェック
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    // ユーザー保存
    public void saveUser(User user) {
        userRepository.save(user);
    }
}