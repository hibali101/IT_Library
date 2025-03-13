package com.hibali.IT_Library;

import java.util.List;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.models.services.AuthorService;
import com.hibali.IT_Library.models.services.TopicService;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getDbConnection();
        AuthorService authorService = new AuthorService(dbConnection);
        try {
            authorService.add(new Author("George R. R. Martin", "https://en.wikipedia.org/wiki/George_R._R._Martin"));
        } catch (FieldRequiredException | FieldUniqueException e) {
            System.out.println(e);
        }

        /* TopicService service = new TopicService(dbConnection);
        
        try{
            
            Topic topic = new Topic();
            topic.setId(6);
            service.delete(topic);
        }catch(FieldRequiredException e){
            System.out.println(e);
        }
        Topic topic = service.getById(5);
        System.out.println("*********");
        System.out.println(topic);
        List<Topic> topics = service.getAll(); 
        topics.forEach(top -> System.out.println(top)); */
    }
}
