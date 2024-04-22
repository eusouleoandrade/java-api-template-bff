package com.project.bff.shared.notifications.contexts;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.project.bff.shared.notifications.abstractions.Notifiable;

@Component
@RequestScope
public class NotificationContext extends Notifiable {
}