package com.customer.service.demo.dto.response;

import org.springframework.http.HttpStatus;

public interface ResponseInterface {

    String getMessage();
    HttpStatus getStatus();
}
