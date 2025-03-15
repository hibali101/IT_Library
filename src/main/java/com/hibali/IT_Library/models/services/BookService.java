package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.models.classes.DbConnection;

public class BookService implements IService<Book, Integer> {
    DbConnection dbConnection;

    public BookService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Book add(Book book) throws FieldRequiredException, FieldUniqueException {
        if(book.getName() == null || book.getAuthorId() <= 0 || book.getFileUrI() == null){
            throw new FieldRequiredException(new String[]{"author_id","author_name","book_file_url"});
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            //didnt insert number of downloads
            String query = "insert into books (author_id, book_name," +
                    "book_publish_date, book_description, book_language, "+
                    "book_file_url, book_edition, book_status )"+
                    " values (?,?,?,?,?,?,?,?)";
            try(PreparedStatement ps = cnx.prepareStatement(query)){
                ps.setInt(1, book.getAuthorId());
                ps.setString(2, book.getName());
                ps.setDate(3, book.getPublishDate());
                ps.setString(4, book.getDescription());
                ps.setString(5, book.getBookLanguage().toString().toLowerCase());
                ps.setString(6, book.getFileUrI());
                ps.setInt(7, book.getEdition());
                ps.setString(8, book.getStatus().toString().toLowerCase());

                ps.executeUpdate();
                cnx.commit();
                System.out.println(book + " inserted successfully");
                return book;
            }catch(SQLException e){
                cnx.rollback();
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    
}
