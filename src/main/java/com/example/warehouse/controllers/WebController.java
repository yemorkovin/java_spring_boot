package com.example.warehouse.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login"; // Если не авторизован, перенаправить на страницу логина
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = "USER";
        if (authentication != null && authentication.isAuthenticated()) {
            // Проверка роли
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            // Логика в зависимости от роли
            if (isAdmin) {
                role = "ADMIN";
                System.out.println("Пользователь - администратор");
            } else {
                System.out.println("Пользователь - обычный пользователь");
            }
            model.addAttribute("isAdmin", isAdmin);
        }
        return "items"; // Если авторизован, показываем items.html
    }
    @GetMapping("/category")
    public String category(Model model,HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login"; // Если не авторизован, перенаправить на страницу логина
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = "USER";
        if (authentication != null && authentication.isAuthenticated()) {
            // Проверка роли
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            // Логика в зависимости от роли
            if (isAdmin) {
                role = "ADMIN";
                System.out.println("Пользователь - администратор");
            } else {
                System.out.println("Пользователь - обычный пользователь");
            }
            model.addAttribute("isAdmin", isAdmin);
        }
        return "category"; // Если авторизован, показываем items.html
    }
    @GetMapping("/about")
    public String about(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login"; // Если не авторизован, перенаправить на страницу логина
        }
        return "about"; // Если авторизован, показываем items.html
    }



}
