package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTest {

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
    }

    @Test
    void mainMethodTest() {
        // Test main method doesn't throw exception
        String[] args = {};
        DemoApplication.main(args);
    }
}
