package com.hibali.IT_Library.http.server;

public class RouterException extends Exception {
    public RouterException(String message){
        super("Router exception: "+message);
    }
}
