package com.obstrom.packerservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${application.http.auth-api-key}")
    private String principalRequestHeader;

    @Value("${application.http.auth-api-value}")
    private String principalRequestValue;

    @Value("${application.http.cors.allowed-origin-primary:}")
    private String corsAllowedOriginPrimary;

    @Value("${application.http.cors.allowed-origin-secondary:}")
    private String corsAllowedOriginSecondary;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(principalRequestHeader);

        filter.setAuthenticationManager(authentication -> {
            String principal = (String) authentication.getPrincipal();

            if (!principalRequestValue.equals(principal)) {
                log.warn("Invalid API key credentials on request");
                throw new BadCredentialsException("Invalid API key credentials");
            }

            authentication.setAuthenticated(true);
            return authentication;
        });

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Content-Type", "x-api-key"));
        corsConfiguration.setAllowedOrigins(List.of(corsAllowedOriginPrimary, corsAllowedOriginSecondary));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        httpSecurity
                .csrf().disable()
                .cors().configurationSource(request -> corsConfiguration)
                .and()
                .antMatcher("/api/**")
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(filter)
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

}
