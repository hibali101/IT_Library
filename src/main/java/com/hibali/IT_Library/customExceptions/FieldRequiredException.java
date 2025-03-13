package com.hibali.IT_Library.customExceptions;

public class FieldRequiredException extends Exception {
    private static final String PREFIX = "is required";

    public FieldRequiredException(String fieldName) {
        super(fieldName + " " + PREFIX);
    }
    public FieldRequiredException(String[] fieldsNames){
        super(String.join(", ",fieldsNames)+" "+PREFIX);
    }
}
