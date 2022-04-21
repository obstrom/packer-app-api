package com.obstrom.packerservice.response;

import com.obstrom.packerservice.exception.ValidationViolation;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorResponse extends HttpResponse {

    private final List<ValidationViolation> validationFailures = new ArrayList<>();

    public ValidationErrorResponse(HttpStatus status) {
        super(status, "Validation failed on one or more fields for this request");
    }

    public void addViolation(String fieldName, String message) {
        this.validationFailures.add(new ValidationViolation(fieldName, message));
    }

}
