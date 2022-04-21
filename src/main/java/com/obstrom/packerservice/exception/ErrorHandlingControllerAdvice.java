package com.obstrom.packerservice.exception;

import com.obstrom.packerservice.response.HttpResponse;
import com.obstrom.packerservice.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleWebExchangeBindException(WebExchangeBindException e) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(HttpStatus.BAD_REQUEST);

        e.getFieldErrors()
                .forEach(fieldError -> errorResponse.addViolation(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ));

        return errorResponse;
    }

    @ExceptionHandler(EnumFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpResponse handleEnumFieldException(EnumFieldException e) {
        return new HttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
