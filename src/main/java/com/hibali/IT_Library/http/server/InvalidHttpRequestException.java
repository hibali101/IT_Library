package com.hibali.IT_Library.http.server;

public class InvalidHttpRequestException extends Exception {
    public InvalidHttpRequestException(String message){
        super("Invalid http request: "+message);
    }
}
