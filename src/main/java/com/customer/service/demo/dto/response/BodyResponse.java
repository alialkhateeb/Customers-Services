package com.customer.service.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class BodyResponse<T> implements ResponseInterface {

    private String message;
    private HttpStatus status;
    private int statusNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;
    private T response;

    public BodyResponse(String message, HttpStatus status, T response) {
        this.message = message;
        this.status = status;
        this.statusNumber = status.value();
        this.response = response;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }


}
