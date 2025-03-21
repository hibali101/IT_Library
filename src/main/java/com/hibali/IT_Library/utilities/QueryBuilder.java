package com.hibali.IT_Library.utilities;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class QueryBuilder {
    private QueryBuilder() {
    }

    // adventure lets go
    public static <Model> Map<String, String> getFieldMappings(Class<Model> modelClass) {
        // the choice of a linkedMap is to preserve order, also class.getDeclaredField
        // retreive the fields in order
        Map<String, String> map = new LinkedHashMap<>();
        String tableName = modelClass.getSimpleName().toLowerCase();

        for (Field field : modelClass.getDeclaredFields()) {
            StringBuilder dbFieldName = new StringBuilder();

            for (char c : field.getName().toCharArray()) {
                if (Character.isUpperCase(c)) {
                    dbFieldName.append('_');
                }
                dbFieldName.append(Character.toLowerCase(c));
            }

            map.put(field.getName(), tableName + "_" + dbFieldName);
        }

        return map;
    }

    // am choosing to handle NoSuchFieldException, IllegalAccessException at this
    // utility level
    public static <Model> Map.Entry<String, List<Object>> buildUpdateQuery(Model model, Class<Model> modelClass) {

        Map<String, String> map = getFieldMappings(modelClass);
        String tableName = modelClass.getSimpleName().toLowerCase() + "s";
        StringBuilder query = new StringBuilder("update ").append(tableName).append(" set ");
        // am declaring this variable to register the field that are not and to be
        // updated
        List<Object> values = new ArrayList<>();
        // this boolean serve to determin if am at the end of the string to not insert
        // ',' character
        boolean hasAddedFields = false;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!key.equals("id")) {
                Field field;
                try {
                    field = modelClass.getDeclaredField(key);
                    field.setAccessible(true);
                    Object value = field.get(model);

                    if (value != null && (!(value instanceof Integer) || (int) value > 0)) {
                        query.append(entry.getValue()).append(" = ?,");
                        values.add(value);
                        hasAddedFields = true;
                    }
                } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        //adding the updated at logique also (i forgot this one)
        query.append(" updated_at = GETDATE()");
        // adding the condition to update in where the id is equal to the object id
        query.append(" where ").append(map.get("id")).append(" = ? ");
        if (!hasAddedFields) {
            throw new IllegalStateException("No valid fields to update.");
        }
        // abstractMap is a class used to create simple Map.Entry
        return new AbstractMap.SimpleEntry<>(query.toString(), values);
    }
}
