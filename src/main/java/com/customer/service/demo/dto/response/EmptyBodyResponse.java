package com.customer.service.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class EmptyBodyResponse implements ResponseInterface {

    private String message;
    private HttpStatus status;
    private int statusNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;


    public EmptyBodyResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.statusNumber = this.status.value();
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    public int getStatusNumber() {
        return statusNumber;
    }
}
