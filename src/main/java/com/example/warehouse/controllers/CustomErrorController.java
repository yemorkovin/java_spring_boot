package com.example.warehouse.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    /**
     * Обрабатывает ошибки и возвращает соответствующую страницу.
     *
     * @param request HTTP-запрос для получения кода ошибки.
     * @param model   Модель для передачи атрибутов в представление.
     * @return Страница ошибки (404 или общая ошибка).
     */
    @GetMapping
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("statusCode", statusCode);

            if (statusCode == 404) {
                model.addAttribute("message", "Страница не найдена");
                return "error/404";
            }
        }

        model.addAttribute("message", "Произошла ошибка");
        return "error/error";
    }
}
