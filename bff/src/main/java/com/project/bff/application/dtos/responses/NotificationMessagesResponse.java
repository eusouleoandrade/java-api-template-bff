package com.project.bff.application.dtos.responses;

import java.util.List;

import com.project.bff.application.dtos.wrappers.Response;
import com.project.bff.shared.notifications.models.NotificationMessage;

public class NotificationMessagesResponse extends Response {

    private List<NotificationMessage> errors;

    public NotificationMessagesResponse(List<NotificationMessage> errors) {
        super(false);

        this.errors = errors;
    }

    public List<NotificationMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<NotificationMessage> errors) {
        this.errors = errors;
    }
}