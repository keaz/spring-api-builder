package com.keaz.tool.exception;

public class ApiBuilderException extends RuntimeException {

    public ApiBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiBuilderException(String message) {
        super(message);
    }
}
