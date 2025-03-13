package com.hibali.IT_Library.utilities;

import com.hibali.IT_Library.models.classes.Topic;
import java.sql.ResultSet;


public class ResultSetMaper {
    public static <T> T mapToModel(ResultSet result, Class<T> modelClass) throws RuntimeException{
        try{
            T model = modelClass.getDeclaredConstructor().newInstance();

            if(model instanceof Topic){
                Topic topic = (Topic) model;
                topic.setId(result.getInt("topic_id"));
                topic.setName(result.getString("topic_name"));
                topic.setdeleted(result.getBoolean("topic_deleted"));
                topic.setCreated_at(result.getTimestamp("created_at"));
                topic.setUpdated_at(result.getTimestamp("updated_at"));
            }
            return model;
        }catch(Exception e){
            throw new RuntimeException("Error mapping",e);
        }
    }
}
