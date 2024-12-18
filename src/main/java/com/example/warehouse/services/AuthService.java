package com.example.warehouse.services;

import com.example.warehouse.models.Role;
import com.example.warehouse.models.User;
import com.example.warehouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Метод аутентификации
    public boolean authenticate(String username, String password) {
        // Ищем пользователя по имени
        User user = userRepository.findByUsername(username).orElse(null);

        // Проверяем, что пользователь существует и пароль совпадает
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }

        return false;
    }

}
