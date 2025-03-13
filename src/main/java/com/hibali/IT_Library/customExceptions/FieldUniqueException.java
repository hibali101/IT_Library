package com.hibali.IT_Library.customExceptions;

public class FieldUniqueException extends Exception {
    private static final String prefix = "must be unique";

    public FieldUniqueException(String fieldName) {
        super(fieldName + "" + prefix);
    }
}
