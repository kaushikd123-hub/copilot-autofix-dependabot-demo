package com.example.demo.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlParsingExceptionTest {

    @Test
    void testExceptionMessageAndCause() {
        String message = "Failed to parse XML";
        Exception cause = new Exception("XML error");
        XmlParsingException exception = new XmlParsingException(message, cause);
        
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionCanBeThrown() {
        assertThrows(XmlParsingException.class, () -> {
            throw new XmlParsingException("Test exception", new Exception());
        });
    }
}
