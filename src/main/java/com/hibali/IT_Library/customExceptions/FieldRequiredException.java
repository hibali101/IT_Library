package com.hibali.IT_Library.customExceptions;

public class FieldRequiredException extends Exception {
    private static final String prefix = "is required";

    public FieldRequiredException(String fieldName) {
        super(fieldName + " " + prefix);
    }
}
