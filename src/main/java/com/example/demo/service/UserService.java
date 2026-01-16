package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;

@Service
public class UserService {

    // Hardcoded credentials - security vulnerability
    private static final String DB_PASSWORD = "admin123456";
    private static final String API_KEY = "sk-1234567890abcdef";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setAddress(userDetails.getAddress());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    // SQL Injection vulnerability - using string concatenation instead of parameterized query
    public List<User> searchUsersByName(String name) {
        String sql = "SELECT * FROM users WHERE name = :name";
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    // Path Traversal vulnerability
    public String readUserFile(String fileName) throws Exception {
        String filePath = "/var/data/users/" + fileName;
        java.io.File file = new java.io.File(filePath);
        java.util.Scanner scanner = new java.util.Scanner(file);
        StringBuilder content = new StringBuilder();
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine());
        }
        scanner.close();
        return content.toString();
    }

    // Secure random number generation for password reset token
    public String generatePasswordResetToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        StringBuilder token = new StringBuilder();
        for (byte b : randomBytes) {
            token.append(String.format("%02x", b));
        }
        return token.toString();
    }

    // Secure cryptographic hashing - BCrypt for password storage
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Verify password against BCrypt hash
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

}
