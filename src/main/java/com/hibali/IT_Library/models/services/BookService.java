package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.BookDao;
import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.models.classes.DbConnection;

public class BookService {
    private final DbConnection dbConnection;
    private final BookDao dao;
    public BookService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.dao = new BookDao();
    }

    public Book add(Book book) throws FieldRequiredException, FieldUniqueException {
        if(book.getName() == null || book.getAuthorId() <= 0 || book.getFileUrI() == null){
            throw new FieldRequiredException(new String[]{"author_id","author_name","book_file_url"});
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            try{
                dao.insert(book, cnx);
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
