package com.obstrom.packerservice.api.exception;

public class JobTimeoutException extends JobException {

    public JobTimeoutException(String message) {
        super(message);
    }

    public JobTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

}
