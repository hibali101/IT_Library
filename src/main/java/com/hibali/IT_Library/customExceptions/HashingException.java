package com.hibali.IT_Library.customExceptions;

public class HashingException extends Exception {
    public HashingException(String fieldName){
        super("failed to hash "+fieldName);
    }
}
