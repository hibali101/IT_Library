package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hibali.IT_Library.models.classes.Book;

public class BookDao/*  implements IDao<Book> */ {

    public void insert(Book book, Connection cnx) throws SQLException {
        // didnt insert number of downloads
        String query = "insert into books (author_id, book_name," +
                "book_publish_date, book_description, book_language, " +
                "book_file_url, book_edition, book_status )" +
                " values (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, book.getAuthorId());
            ps.setString(2, book.getName());
            ps.setDate(3, book.getPublishDate());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getBookLanguage().toString().toLowerCase());
            ps.setString(6, book.getFileUrI());
            ps.setInt(7, book.getEdition());
            ps.setString(8, book.getStatus().toString().toLowerCase());
            ps.executeUpdate();
        }

    }

}
