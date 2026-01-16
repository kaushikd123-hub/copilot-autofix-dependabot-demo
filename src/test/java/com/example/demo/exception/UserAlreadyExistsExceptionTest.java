package com.example.demo.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistsExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "User already exists";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionCanBeThrown() {
        assertThrows(UserAlreadyExistsException.class, () -> {
            throw new UserAlreadyExistsException("Test exception");
        });
    }
}
