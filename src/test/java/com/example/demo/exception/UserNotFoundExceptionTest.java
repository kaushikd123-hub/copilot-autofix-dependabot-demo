package com.example.demo.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "User not found with id: 1";
        UserNotFoundException exception = new UserNotFoundException(message);
        
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionCanBeThrown() {
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException("Test exception");
        });
    }
}
