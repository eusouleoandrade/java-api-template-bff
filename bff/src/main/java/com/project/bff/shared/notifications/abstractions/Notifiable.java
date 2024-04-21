package com.project.bff.shared.notifications.abstractions;

import com.project.bff.shared.notifications.interfaces.INotifiable;
import com.project.bff.shared.notifications.models.NotificationMessage;

public abstract class Notifiable extends NotifiableGeneric<NotificationMessage> implements INotifiable {

    public void addErrorNotification(String key, String message) {

        errorNotifications.add(new NotificationMessage(key, message));
    }

    public void addErrorNotification(String key, String message, Object... parameters) {

        errorNotifications.add(new NotificationMessage(key, String.format(message, parameters)));
    }

    public void addErrorNotifications(INotifiable... objects) {

        for (INotifiable notifiable : objects) {
            errorNotifications.addAll(notifiable.getErrorNotifications());
        }
    }

    public void addSuccessNotification(String key, String message) {

        successNotifications.add(new NotificationMessage(key, message));
    }

    public void addSuccessNotification(String key, String message, Object... parameters) {

        successNotifications.add(new NotificationMessage(key, String.format(message, parameters)));
    }

    public void addSuccessNotifications(INotifiable... objects) {

        for (INotifiable notifiable : objects) {
            successNotifications.addAll(notifiable.getSuccessNotifications());
        }
    }
}