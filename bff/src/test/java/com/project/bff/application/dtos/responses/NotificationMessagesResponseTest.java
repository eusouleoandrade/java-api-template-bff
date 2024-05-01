package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bff.shared.notifications.models.NotificationMessage;
import com.project.bff.shared.ultils.MsgUltil;

@SpringBootTest
public class NotificationMessagesResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "COD0001, Notification message 1.",
            "COD0002, Notification message 2.",
            "COD0003, Notification message 3."
    })
    public void shouldExecuteSuccessfullyWhenToUseTheParameterizedCtor(String key, String message) {

        // Arranje
        var notificationMessage = new NotificationMessage(key, message);

        var notificationMesssageList = new ArrayList<NotificationMessage>();
        notificationMesssageList.add(notificationMessage);

        // Act
        NotificationMessagesResponse response = new NotificationMessagesResponse(notificationMesssageList);

        // Assert
        assertThat(response).isNotNull();

        assertThat(response.getErrors()).isNotEmpty();
        assertThat(response.getErrors()).hasSize(1);
        assertThat(response.getErrors()).isEqualTo(notificationMesssageList);

        assertThat(response.isSucceeded()).isFalse();

        assertThat(response.getMessage()).isEqualTo(MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1]);
    }

    @DisplayName("Should execute successfully when to use the set errors")
    @ParameterizedTest
    @CsvSource({
            "COD0001, Notification message 1.",
            "COD0002, Notification message 2.",
            "COD0003, Notification message 3."
    })
    public void shouldExecuteSuccessfullyWhenToUseSetErrors(String key, String message) {

        // Arranje
        var notificationMessage = new NotificationMessage(key, message);

        var notificationMesssageList = new ArrayList<NotificationMessage>();
        notificationMesssageList.add(notificationMessage);

        // Act
        NotificationMessagesResponse response = new NotificationMessagesResponse(null);
        response.setErrors(notificationMesssageList);

        // Assert
        assertThat(response).isNotNull();

        assertThat(response.getErrors()).isNotEmpty();
        assertThat(response.getErrors()).hasSize(1);
        assertThat(response.getErrors()).isEqualTo(notificationMesssageList);

        assertThat(response.isSucceeded()).isFalse();

        assertThat(response.getMessage()).isEqualTo(MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1]);
    }
}
