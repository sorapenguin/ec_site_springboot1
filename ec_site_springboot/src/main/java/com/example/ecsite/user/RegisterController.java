package com.example.ecsite.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecsite.service.user.UserService;
import com.example.ecsite.model.User;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登録フォーム表示
     */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // register.html を表示
    }

    /**
     * 登録処理
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {

        // すでにユーザーが存在するか確認
        if (userService.isUsernameTaken(username)) {
            model.addAttribute("error", "ユーザー名は既に使われています");
            return "register";
        }

        // ユーザー登録
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // 本番ならハッシュ化推奨
        userService.saveUser(newUser);

        // 登録成功後はログイン画面へ
        return "redirect:/login";
    }
}