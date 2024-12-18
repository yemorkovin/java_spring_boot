package com.example.warehouse.controllers;

import com.example.warehouse.models.User;
import com.example.warehouse.services.AuthService;
import com.example.warehouse.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // Страница логина
    @GetMapping("/login")
    public String loginPage() {
        System.out.println("логин get ");
        return "login"; // Возвращает страницу login.html
    }
    // Обработка логина
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        System.out.println("логин post ");
        // Проверка, что метод запускается
        System.out.println("POST /login запрос поступил");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if (authService.authenticate(username, password)) {
            System.out.println("Успех: ");

            session.setAttribute("username", username);

            return "redirect:/";
        } else {
            model.addAttribute("error", "Неверный логин или пароль");
            return "login";
        }
    }

    // Страница регистрации
    @GetMapping("/register")
    public String registerPage() {
        System.out.println("reg get ");
        return "register"; // Страница для регистрации
    }

    // Обработка регистрации
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) {
        System.out.println("reg post ");
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают");
            return "register"; // Возвращаем на страницу регистрации с ошибкой
        }

        if (userService.isUsernameTaken(username)) {
            model.addAttribute("error", "Имя пользователя уже занято");
            return "register"; // Возвращаем на страницу регистрации с ошибкой
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Пароль нужно захешировать в UserService при регистрации


        userService.register(user);
        return "redirect:/login"; // Перенаправляем на страницу логина
    }







    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
