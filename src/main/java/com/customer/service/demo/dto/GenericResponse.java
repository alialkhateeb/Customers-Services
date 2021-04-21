package com.customer.service.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class GenericResponse<T> {

    private String message;
    private HttpStatus status;
    private int statusNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;
    private T response;


    public GenericResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.statusNumber = status.value();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public GenericResponse(String message, HttpStatus status, T responseObject) {
        this.message = message;
        this.status = status;
        this.statusNumber = status.value();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.response = responseObject;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public T getResponse() {
        return response;
    }

    public int getStatusNumber() {
        return statusNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
