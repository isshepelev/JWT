package ru.isshepelev.jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.isshepelev.jwt.entity.Role;
import ru.isshepelev.jwt.entity.User;
import ru.isshepelev.jwt.entity.dto.UserRegistrationAndAuthenticateDto;
import ru.isshepelev.jwt.jwt.JwtUtil;
import ru.isshepelev.jwt.service.UserService;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello";
    }
    // сюда доступ разрешен только user и admin
    @GetMapping("/user")
    public String user() {
        return "User";
    }
    // сюда доступ разрешен только admin
    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }



    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationAndAuthenticateDto dto){
        String username = dto.getUsername();
        String password = dto.getPassword();
        Role role = Role.USER;
        return userService.registerUser(username,password,role);
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestBody UserRegistrationAndAuthenticateDto dto){
        String username = dto.getUsername();
        String password = dto.getPassword();

        User user = userService.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            Role role = user.getRoles(); // Изменили тип на Role.
            return jwtUtil.generateToken(username, role); // Передаем Role вместо List<String>.
        }else throw new IllegalArgumentException("Неверное имя пользователя или пароль");
    }
}
