package com.test.tracker.core.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private Integer status;

    private String message;

    private Long timestamp;

    private String requestURI;

    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    private ErrorResponse(final Integer status, final String requestURI, String message) {
        this.message = message;
        this.status = status;
        this.requestURI = requestURI;
        this.timestamp = System.currentTimeMillis();
    }

    public static ErrorResponse of(final Integer status, final String senderChatId, String message) {
        return new ErrorResponse(status, senderChatId, message);
    }
}