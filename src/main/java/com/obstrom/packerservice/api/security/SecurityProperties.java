package com.obstrom.packerservice.api.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("application.security")
public class SecurityProperties {

    private String authTokenHeaderName;
    private String authToken;

}
