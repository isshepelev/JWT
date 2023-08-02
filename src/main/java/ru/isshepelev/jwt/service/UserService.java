package ru.isshepelev.jwt.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isshepelev.jwt.entity.Role;
import ru.isshepelev.jwt.entity.User;
import ru.isshepelev.jwt.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository uSerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = uSerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User registerUser(String username, String password, Role role){
        if (userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRoles(role);

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    public List<User> findByAllUser() {
        return userRepository.findAll();
    }
}
