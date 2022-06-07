package com.obstrom.packerservice.api.exception;

public record ValidationViolation(
        String fieldName,
        String message
) {
}
