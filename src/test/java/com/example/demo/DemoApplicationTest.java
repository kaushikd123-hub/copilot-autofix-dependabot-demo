package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoApplicationTest {

    @Test
    void mainMethodTest() {
        // Just verify the class exists and can be instantiated
        DemoApplication app = new DemoApplication();
        assertNotNull(app);
    }
}
