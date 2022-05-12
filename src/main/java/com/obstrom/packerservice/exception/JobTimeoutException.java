package com.obstrom.packerservice.exception;

public class JobTimeoutException extends JobException {

    public JobTimeoutException(String message) {
        super(message);
    }

    public JobTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

}
