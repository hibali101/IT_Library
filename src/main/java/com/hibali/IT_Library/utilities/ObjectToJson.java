package com.hibali.IT_Library.utilities;

import java.lang.reflect.Field;

//this is a simple object to json string converter (i dont want to use any libraries because this project is for learning, alah akha hicham 3debti rask)
public class ObjectToJson {
    public static String toJson(Object object){
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{\n");
        Field[] fields = object.getClass().getDeclaredFields();
        for(int i = 0; i<fields.length; i++){
            try {
                fields[i].setAccessible(true);
                String key = fields[i].getName();
                Object value = fields[i].get(object); //i will to string it but this can create problemes for object's that toStriong method doesnt reprent it correctelly
                jsonString.append("\"").append(key).append("\"")
                .append(": ").append("\"").append(value.toString()).append("\"");
                if(i < fields.length - 1){
                    jsonString.append(",\n"); //only add comma "," if end of file is not reached
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        jsonString.append("\n}"); //end curly bracket
        return jsonString.toString();
    }
}
