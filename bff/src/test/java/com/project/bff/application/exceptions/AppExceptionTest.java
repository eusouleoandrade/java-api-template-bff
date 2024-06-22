package com.project.bff.application.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppExceptionTest {

    @DisplayName("Test default constructor")
    @Test
    public void testDefaultConstructor() {

        // Arrange
        AppException exception;

        // Act
        exception = new AppException();

        // Assert
        assertNull(exception.getMessage());
        assertNull(exception.getCode());
    }

    @DisplayName("Test message constructor")
    @ParameterizedTest
    @CsvSource({
            "Test message 1.",
            "Test message 2.",
            "Teste message 3."
    })
    public void testMessageConstructor(String message) {

        // Arrange
        AppException exception;

        // Act
        exception = new AppException(message);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertNull(exception.getCode());
    }

    @DisplayName("Test message and args constructor")
    @Test
    public void testMessageAndArgsConstructor() {

        // Arrange
        String message = "Test message with {0} and {1}";
        String arg1 = "arg1";
        String arg2 = "arg2";

        String expectedMessage = "Test message with arg1 and arg2";

        // Act
        AppException exception = new AppException(message, arg1, arg2);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
        assertNull(exception.getCode());
    }

    @DisplayName("test message and cause constructor")
    @Test
    public void testMessageAndCauseConstructor() {

        // Arrange
        String message = "Test message";
        Throwable cause = new Throwable("Cause message");

        // Act
        AppException exception = new AppException(message, cause);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
        assertNull(exception.getCode());
    }

    @DisplayName("Test get code")
    @Test
    public void testGetCode() {

        // Arrange
        String code = "ERROR_CODE";
        AppException exception = new AppException(code, "Test message", new Throwable("Cause message"));

        // Act
        String resultCode = exception.getCode();

        // Assert
        assertThat(resultCode).isEqualTo(code);
    }
}