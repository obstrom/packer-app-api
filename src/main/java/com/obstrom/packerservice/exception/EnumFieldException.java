package com.obstrom.packerservice.exception;

import lombok.Getter;

@Getter
public class EnumFieldException extends RuntimeException {

    public EnumFieldException(String message) {
        super(message);
    }

    public EnumFieldException(String message, Throwable cause) {
        super(message, cause);
    }

}
