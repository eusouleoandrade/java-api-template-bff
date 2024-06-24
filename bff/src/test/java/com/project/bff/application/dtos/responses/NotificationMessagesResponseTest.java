package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bff.shared.notifications.models.NotificationMessage;
import com.project.bff.shared.ultils.MsgUltil;

@SpringBootTest
public class NotificationMessagesResponseTest {

    @DisplayName("Test NotificationMessagesResponse Constructor and Getters")
    @Test
    public void testNotificationMessagesResponseConstructorAndGetters() {

        // Arrange
        NotificationMessage error1 = new NotificationMessage("Error 1", "E001");
        NotificationMessage error2 = new NotificationMessage("Error 2", "E002");

        var errors = new ArrayList<NotificationMessage>();
        errors.add(error1);
        errors.add(error2);

        // Act
        NotificationMessagesResponse response = new NotificationMessagesResponse(errors);

        // Assert
        assertThat(response).isNotNull();

        assertThat(response.isSucceeded()).isFalse();

        assertThat(response.getErrors()).isNotEmpty();
        assertThat(response.getErrors()).hasSize(2);
        assertThat(response.getErrors()).isEqualTo(errors);
        assertThat(response.getErrors()).containsExactly(error1, error2);

        assertThat(response.getMessage()).isEqualTo(MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1]);
    }

    @DisplayName("Test Set Errors")
    @Test
    public void testSetErrors() {

        // Arrange
        NotificationMessage error1 = new NotificationMessage("Error 1", "E001");
        NotificationMessage error2 = new NotificationMessage("Error 2", "E002");

        var errors = new ArrayList<NotificationMessage>();
        errors.add(error1);
        errors.add(error2);

        NotificationMessagesResponse response = new NotificationMessagesResponse(null);

        // Act
        response.setErrors(errors);

        // Assert
        assertThat(response).isNotNull();

        assertThat(response.isSucceeded()).isFalse();

        assertThat(response.getErrors()).isNotEmpty();
        assertThat(response.getErrors()).hasSize(2);
        assertThat(response.getErrors()).isEqualTo(errors);
        assertThat(response.getErrors()).containsExactly(error1, error2);

        assertThat(response.getMessage()).isEqualTo(MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1]);
    }
}