package com.hibali.IT_Library.utilities;

import java.sql.ResultSet;

import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.models.classes.BaseModel;
import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.models.classes.Topic;

public class ResultSetMaper {

    public static <T> T mapToModel(ResultSet result, Class<T> modelClass) throws RuntimeException {
        try {
            T model = modelClass.getDeclaredConstructor().newInstance();

            if (model instanceof Topic) {
                Topic topic = (Topic) model;
                topic.setId(result.getInt("topic_id"));
                topic.setName(result.getString("topic_name"));
                topic.setdeleted(result.getBoolean("topic_deleted"));
                topic.setCreated_at(result.getTimestamp("created_at"));
                topic.setUpdated_at(result.getTimestamp("updated_at"));
            }
            else if (model instanceof ProgrammingLanguage) {
                ProgrammingLanguage programmingLanguage = (ProgrammingLanguage) model;
                programmingLanguage.setId(result.getInt("prog_lang_id"));
                programmingLanguage.setName(result.getString("prog_lang_name"));
                programmingLanguage.setdeleted(result.getBoolean("prog_lang_deleted"));
                programmingLanguage.setCreated_at(result.getTimestamp("created_at"));
                programmingLanguage.setUpdated_at(result.getTimestamp("updated_at"));
            }
            else if(model instanceof Author){
                Author author = (Author) model;
                author.setId(result.getInt("author_id"));
                author.setName(result.getString("author_name"));
                author.setLink(result.getString("author_link"));
                author.setdeleted(result.getBoolean("author_deleted"));
                author.setCreated_at(result.getTimestamp("created_at"));
                author.setUpdated_at(result.getTimestamp("updated_at"));
            }

            return model;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping", e);
        }
    }
}
