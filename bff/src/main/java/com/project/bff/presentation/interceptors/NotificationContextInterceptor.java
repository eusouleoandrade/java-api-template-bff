package com.project.bff.presentation.interceptors;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bff.application.dtos.responses.NotificationMessagesResponse;
import com.project.bff.shared.notifications.contexts.NotificationContext;
import com.project.bff.shared.ultils.MsgUltil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NotificationContextInterceptor implements HandlerInterceptor {

    private final NotificationContext notificationContext;
    private final Logger logger = LoggerFactory.getLogger(NotificationContextInterceptor.class);

    public NotificationContextInterceptor(NotificationContext notificationContext) {
        this.notificationContext = notificationContext;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable ModelAndView modelAndView) throws IOException {

        if (notificationContext.hasErrorNotification()) {

            response.setContentType("application/json");

            // Check if you have not found notifications
            boolean hasSingleErrorWithDataNotFound = notificationContext.getErrorNotifications().size() == 1 &&
                    notificationContext.getErrorNotifications().get(0).getKey()
                            .equals(MsgUltil.DATA_OF_X0_X1_NOT_FOUND(null, null)[0]);

            // Check if you have entity validation notifications
            boolean hasValidationError = notificationContext.getErrorNotifications().get(0).getKey()
                    .contains(MsgUltil.X0_MUST_CONTAIN_X1_CHARACTERS(null, null)[0])
                    || notificationContext.getErrorNotifications().get(0).getKey()
                            .contains(MsgUltil.X0_IS_REQUIRED(null)[0])
                    || notificationContext.getErrorNotifications().get(0).getKey()
                            .contains(MsgUltil.IDENTIFIER_X0_IS_INVALID(null)[0]);

            if (hasSingleErrorWithDataNotFound) {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            } else if (hasValidationError) {

                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

            } else {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            var errorResponse = new NotificationMessagesResponse(notificationContext.getErrorNotifications());

            try {
                String notifications = new ObjectMapper().writeValueAsString(errorResponse);

                logger.warn("Finalizes request with notifications. Notifications: {}", notifications);

                response.getWriter().write(notifications);

            } catch (JsonProcessingException ex) {

                String errorMessage = "Error during response serialization: " + ex.getMessage();

                logger.error(errorMessage, ex);

                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

                response.getWriter().write("{\"message\":\"" + errorMessage + "\"}");

            } finally {
                response.getWriter().flush();
            }

            return;
        }
    }
}