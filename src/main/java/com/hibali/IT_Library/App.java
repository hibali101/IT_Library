package com.hibali.IT_Library;

import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.models.services.ProgrammingLanguageService;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getDbConnection();

        ProgrammingLanguageService progService = new ProgrammingLanguageService(dbConnection);
        try {
            progService.add(new ProgrammingLanguage("java"));
        } catch (FieldRequiredException | FieldUniqueException e) {
            System.out.println(e);
        }

        try {

            ProgrammingLanguage topic = new ProgrammingLanguage();
            topic.setId(1);
            progService.delete(topic);
        } catch (FieldRequiredException e) {
            System.out.println(e);
        }
        ProgrammingLanguage progsLang = progService.getById(1);
        System.out.println("*********");
        System.out.println(progsLang);
        ArrayList<ProgrammingLanguage> progsLangs = progService.getAll();
        progsLangs.forEach(top -> System.out.println(top));
        /* AuthorService authorService = new AuthorService(dbConnection);
        try {
            authorService.add(new Author("George R. R. Martin", "https://en.wikipedia.org/wiki/George_R._R._Martin"));
        } catch (FieldRequiredException | FieldUniqueException e) {
            System.out.println(e);
        }

         TopicService service = new TopicService(dbConnection);
        
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
