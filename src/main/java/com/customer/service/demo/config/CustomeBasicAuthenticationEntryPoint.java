package com.customer.service.demo.config;

import com.customer.service.demo.dto.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomeBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        GenericResponse jsonResponse = new GenericResponse(authEx.getMessage(), HttpStatus.UNAUTHORIZED);
        writer.println(mapper.writeValueAsString(jsonResponse));

    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("customer");
        super.afterPropertiesSet();
    }

}
