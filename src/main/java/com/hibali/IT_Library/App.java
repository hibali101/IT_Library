package com.hibali.IT_Library;

import java.util.List;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.models.services.TopicService;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getDbConnection();
        TopicService service = new TopicService(dbConnection);
        
        try{
            /* service.update(new Topic("data structures"));
            service.add(new Topic("Very good sir!"));
            service.add(new Topic("data science!")); */
            Topic topic = new Topic();
            topic.setId(2);
            topic.setName("dzeer");
            service.update(topic);
        }catch(FieldUniqueException e){
            System.out.println(e);
        }
        Topic topic = service.getById(6);
        System.out.println("*********");
        System.out.println(topic.toString());
        List<Topic> topics = service.getAll(); 
        topics.forEach(top -> System.out.println(top.getId()+" "+top.getName() + " updated at : "+top.updated_at));
    }
}
