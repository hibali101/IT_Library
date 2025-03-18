package com.hibali.IT_Library;

import com.hibali.IT_Library.models.Dao.AuthorDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.services.AuthorService;
/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getDbConnection();

        // books
        /*
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

        AuthorService service = new AuthorService(dbConnection, new AuthorDao());
        service.getAll().forEach(System.out::println);

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
