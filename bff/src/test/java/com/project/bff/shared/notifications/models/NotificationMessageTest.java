package com.project.bff.shared.notifications.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationMessageTest {

    @DisplayName("Test constructor and getters")
    @ParameterizedTest
    @CsvSource({
            "Key 1, Message 1",
            "Key 2, Message 2"
    })
    public void testConstructorAndGetters(String key, String message) {

        // Arrange
        NotificationMessage notificationMessage;

        // Act
        notificationMessage = new NotificationMessage(key, message);

        // Assert
        assertThat(notificationMessage.getKey()).isEqualTo(key);
        assertThat(notificationMessage.getMessage()).isEqualTo(message);
    }

    @DisplayName("Test setters")
    @ParameterizedTest
    @CsvSource({
            "Key 1, Message 1, New key 1, New message 1",
            "Key 2, Message 2, New key 2, New message 2"
    })
    public void testSetters(String initialKey, String initialMessage, String newKey, String newMessage) {

        // Arrange
        NotificationMessage notificationMessage = new NotificationMessage(initialKey, initialMessage);

        // Act
        notificationMessage.setKey(newKey);
        notificationMessage.setMessage(newMessage);

        // Assert
        assertThat(notificationMessage.getKey()).isEqualTo(newKey);
        assertThat(notificationMessage.getMessage()).isEqualTo(newMessage);
    }
}
