package com.tenpo.challenge.exceptions;

public class BusinessException extends RuntimeException {

    private String message;

    public BusinessException(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
