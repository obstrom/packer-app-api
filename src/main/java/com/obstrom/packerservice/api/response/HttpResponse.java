package com.obstrom.packerservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HttpResponse {

    private final HttpStatus status;
    private final String message;

}
