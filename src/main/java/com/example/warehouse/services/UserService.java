package com.example.warehouse.services;

import com.example.warehouse.models.Role;
import com.example.warehouse.models.User;
import com.example.warehouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Проверка на существование пользователя с таким именем
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Регистрация нового пользователя
    public void register(User user) {
        // Хешируем пароль перед сохранением
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Присваиваем роль по умолчанию (например, ROLE_USER)
        user.setRoles(Set.of(Role.ADMIN));
        userRepository.save(user);
    }
}
