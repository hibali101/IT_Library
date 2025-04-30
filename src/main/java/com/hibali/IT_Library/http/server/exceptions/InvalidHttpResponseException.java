package com.hibali.IT_Library.http.server.exceptions;

public class InvalidHttpResponseException extends Exception {
    public InvalidHttpResponseException(String message){
        super("Invalid http response: "+message);
    }
}
