package com.hibali.IT_Library;

import java.sql.Date;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.enums.BookLanguages;
import com.hibali.IT_Library.enums.BookStatus;
import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.models.services.AuthorService;
import com.hibali.IT_Library.models.services.BookService;
import com.hibali.IT_Library.models.services.ProgrammingLanguageService;
import com.hibali.IT_Library.models.services.TopicService;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getDbConnection();

        TopicService service = new TopicService(dbConnection);
        Topic topic = new Topic();
        topic.setId(2);
        try {
            service.delete(topic);
        } catch (FieldRequiredException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // books
        /*
         * BookService bookService = new BookService(dbConnection);
         * Book book = new Book(1, "Song of ice and fire", Date.valueOf("2093-03-14"),
         * "good book", BookLanguages.ENGLISH,
         * "files/books/EZCddzz.pdf", 1, BookStatus.ACCEPTED);
         * try{
         * bookService.add(book);
         * }catch(FieldRequiredException | FieldUniqueException e){
         * System.out.println(e);
         * }
         */

        // authors
        // AuthorService service = new AuthorService(dbConnection);

        /*
         * Author author = new Author(2, "hicham el bachari",
         * "https://www.yourChoice.com");
         * try{
         * service.update(author);
         * }catch(FieldRequiredException | FieldUniqueException e){
         * System.out.println(e);
         * }
         */
        /*
         * Author author = service.getById(2);
         * System.out.println(author);
         */
        /*
         * ArrayList<Author> authors = service.getAll();
         * for (Author auth : authors) {
         * System.out.println(auth.toString());
         * }
         */
        /*
         * ProgrammingLanguageService progService = new
         * ProgrammingLanguageService(dbConnection);
         * try {
         * progService.add(new ProgrammingLanguage("java"));
         * } catch (FieldRequiredException | FieldUniqueException e) {
         * System.out.println(e);
         * }
         * 
         * try {
         * 
         * ProgrammingLanguage topic = new ProgrammingLanguage();
         * topic.setId(1);
         * progService.delete(topic);
         * } catch (FieldRequiredException e) {
         * System.out.println(e);
         * }
         * ProgrammingLanguage progsLang = progService.getById(1);
         * System.out.println("*********");
         * System.out.println(progsLang);
         * ArrayList<ProgrammingLanguage> progsLangs = progService.getAll();
         * progsLangs.forEach(top -> System.out.println(top));
         */

        /*
         * AuthorService authorService = new AuthorService(dbConnection);
         * try {
         * authorService.add(new Author("George R. R. Martin",
         * "https://en.wikipedia.org/wiki/George_R._R._Martin"));
         * } catch (FieldRequiredException | FieldUniqueException e) {
         * System.out.println(e);
         * }
         * 
         * TopicService service = new TopicService(dbConnection);
         * 
         * try{
         * 
         * Topic topic = new Topic();
         * topic.setId(6);
         * service.delete(topic);
         * }catch(FieldRequiredException e){
         * System.out.println(e);
         * }
         * Topic topic = service.getById(5);
         * System.out.println("*********");
         * System.out.println(topic);
         * List<Topic> topics = service.getAll();
         * topics.forEach(top -> System.out.println(top));
         */
    }
}
