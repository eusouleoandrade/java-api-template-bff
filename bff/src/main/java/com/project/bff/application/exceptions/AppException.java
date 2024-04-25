package com.project.bff.application.exceptions;

import java.text.MessageFormat;

public class AppException extends RuntimeException {

    private String code;

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String code, String message, Throwable cause) {
        super(message, cause);

        this.code = code;
    }

    public String getCode() {
        return code;
    }
}