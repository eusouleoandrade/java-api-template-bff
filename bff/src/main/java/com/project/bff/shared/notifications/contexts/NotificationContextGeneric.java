package com.project.bff.shared.notifications.contexts;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.project.bff.shared.notifications.abstractions.NotifiableGeneric;

@Component
@RequestScope
public class NotificationContextGeneric<TNotificationMessage> extends NotifiableGeneric<TNotificationMessage> {
}