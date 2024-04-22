package com.project.bff.application.dtos.wrappers;

import com.project.bff.shared.ultils.MsgUltil;

public abstract class Response {

    private boolean succeeded;

    private String message;

    public Response(boolean succeeded, String message) {
        this.succeeded = succeeded;
        this.message = message;
    }

    public Response(boolean succeeded) {

        this.succeeded = succeeded;

        this.message = succeeded ? MsgUltil.RESPONSE_SUCCEEDED_MESSAGE()[1]
                : MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1];
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }
}