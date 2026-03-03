package com.example.ecsite.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// ← ここで UserService を正しく import する必要がある
import com.example.ecsite.service.user.UserService;
@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }
@GetMapping("/login")
public String loginForm() {
    return "login"; // login.html を返す
}
@PostMapping("/login")
public String loginSubmit(@RequestParam String username,
                          @RequestParam String password,
                          Model model) {

    System.out.println("username: '" + username + "'");
    System.out.println("password: '" + password + "'");

    if(userService.authenticate(username, password)) {
        System.out.println("認証成功");
        return "redirect:/product_list";
    } else {
        System.out.println("認証失敗");
        model.addAttribute("error", "ユーザー名またはパスワードが間違っています");
        return "login";
    }
}
}