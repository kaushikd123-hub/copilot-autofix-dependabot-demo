package com.example.demo.exception;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessingExceptionTest {

    @Test
    void testExceptionMessageAndCause() {
        String message = "Failed to process file";
        IOException cause = new IOException("IO error");
        FileProcessingException exception = new FileProcessingException(message, cause);
        
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionCanBeThrown() {
        assertThrows(FileProcessingException.class, () -> {
            throw new FileProcessingException("Test exception", new IOException());
        });
    }
}
