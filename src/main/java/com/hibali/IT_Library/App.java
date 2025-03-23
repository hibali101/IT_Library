package com.hibali.IT_Library;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Base64.Encoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.customExceptions.HashingException;
import com.hibali.IT_Library.models.Dao.BookTopicDao;
import com.hibali.IT_Library.models.Dao.UserDao;
import com.hibali.IT_Library.models.classes.BookTopic;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.User;
import com.hibali.IT_Library.models.services.BookTopicService;
import com.hibali.IT_Library.models.services.UserService;
import com.hibali.IT_Library.utilities.QueryBuilder;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getDbConnection();
        /*
         * UserService service = new UserService(dbConnection, new UserDao());
         * try {
         * service.add(new User("hibadfsdzli", "f1ss234",
         * "hichamelbachari.grh@gmail.com", "0626358233", true));
         * } catch (FieldRequiredException e) {
         * e.printStackTrace();
         * } catch (FieldUniqueException e) {
         * e.printStackTrace();
         * } catch (BuisnessRulesException e) {
         * e.printStackTrace();
         * } catch (SQLException e) {
         * e.printStackTrace();
         * } catch (HashingException e) {
         * e.printStackTrace();
         * }
         */

        UserService service = new UserService(dbConnection, new UserDao());
        /* try {
            service.add(new User("toabui", "f1ss234", "bikouranImane.grh@gmail.com", "0626358233", true));
        } catch (FieldRequiredException | FieldUniqueException | BuisnessRulesException | SQLException
                | HashingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
        try {
            System.out.println("before update");
            System.out.println(service.getAll().toString());
            System.out.println("after update");
            Optional<User> userOptional = service.getById(10);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                user.setEmail("the newest email sever");
                user.setPassword(null);
                user.setName(null);
                service.update(user);
            }
            System.out.println(service.getAll().toString());
        } catch (SQLException | FieldUniqueException | FieldRequiredException | BuisnessRulesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // books
        /*
         * 
         * BookService bkService = new BookService(dbConnection);
         * BookDao bkDao = new BookDao();
         */

        /*
         * try {
         * ArrayList<Book> books = bkDao.findByName("sg", dbConnection.create());
         * System.out.println(books.size());
         * } catch (SQLException e) {
         * System.out.println(e.getMessage());
         * }
         */

        /*
         * BookTopicService service = new BookTopicService(dbConnection, new
         * BookTopicDao());
         * try {
         * service.add(new BookTopic(2,3));
         * } catch (SQLException | FieldRequiredException | FieldUniqueException |
         * BuisnessRulesException e) {
         * e.printStackTrace();
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
