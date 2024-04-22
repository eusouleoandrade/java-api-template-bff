package com.project.bff.shared.notifications.interfaces;

import java.util.List;

public interface INotifiableGeneric<TNotificationMessage> {

    boolean hasErrorNotification();

    boolean hasSuccessNotification();

    List<TNotificationMessage> getErrorNotifications();

    List<TNotificationMessage> getSuccessNotifications();
}