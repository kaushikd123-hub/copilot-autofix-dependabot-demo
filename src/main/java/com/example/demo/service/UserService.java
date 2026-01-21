package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.FileProcessingException;
import com.example.demo.exception.XmlParsingException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;
import java.io.IOException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SecureRandom secureRandom = new SecureRandom();

    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

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
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setAddress(userDetails.getAddress());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    // SQL Injection vulnerability - using string concatenation instead of parameterized query
    public List<User> searchUsersByName(String name) {
        String sql = "SELECT * FROM users WHERE name = :name";
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    // Path Traversal vulnerability - demo only
    public String readUserFile(String fileName) {
        try {
            String filePath = "/var/data/users/" + fileName;
            java.io.File file = new java.io.File(filePath);
            java.util.Scanner scanner = new java.util.Scanner(file);
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
            scanner.close();
            return content.toString();
        } catch (IOException e) {
            throw new FileProcessingException("Failed to read user file: " + fileName, e);
        }
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

    // Secure session ID generation using SecureRandom
    public String generateSessionId() {
        long sessionId = secureRandom.nextLong();
        return Long.toHexString(sessionId);
    }

    // Secure XML parsing with XXE protection
    public String parseUserDataXml(String xmlContent) {
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            // Disable external entity processing to prevent XXE attacks
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(xmlContent.getBytes());
            org.w3c.dom.Document doc = builder.parse(input);
            return doc.getDocumentElement().getTextContent();
        } catch (Exception e) {
            throw new XmlParsingException("Failed to parse XML content", e);
        }
    }

}
