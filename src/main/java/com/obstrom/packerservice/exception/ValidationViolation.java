package com.obstrom.packerservice.exception;

public record ValidationViolation(
        String fieldName,
        String message
) {
}
