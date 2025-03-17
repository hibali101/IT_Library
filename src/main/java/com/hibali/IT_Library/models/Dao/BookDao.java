package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.models.classes.Book;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class BookDao implements IDao<Book> {

    public void insert(Book book, Connection cnx) throws SQLException {
        // didnt insert number of downloads
        String query = "insert into books (author_id, book_name," +
                "book_publish_date, book_description, book_language, " +
                "book_file_url, book_edition, book_nbr_downloads, book_status )" +
                " values (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, book.getAuthorId());
            ps.setString(2, book.getName());
            ps.setDate(3, book.getPublishDate());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getBookLanguage().toString().toLowerCase());
            ps.setString(6, book.getFileUrI());
            ps.setInt(7, book.getEdition());
            ps.setInt(8, book.getNbrDownloads());
            ps.setString(9, book.getStatus().toString().toLowerCase());
            ps.executeUpdate();
        }

    }

    public ArrayList<Book> findAll(Connection cnx) throws SQLException {
        String query = "select * from books where book_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(ResultSetMaper.mapToModel(rs, Book.class));
            }
            return books;
        }
    }

    public Book findById(int id, Connection cnx) throws SQLException {
        String query = "select * from books where book_id=? and book_deleted=0";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return ResultSetMaper.mapToModel(rs, Book.class);
                }
            }
        }
        return null;
    }

    public void update(Book book, Connection cnx) throws SQLException {
        String query = "update books set author_id=?, book_name=?," +
                " book_publish_date=?, book_description=?," +
                "book_language=?, book_file_url=?," +
                " book_edition=?, book_nbr_downloads=?, book_status=?, updated_at=GETDATE()";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, book.getAuthorId());
            ps.setString(2, book.getName());
            ps.setDate(3, book.getPublishDate());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getBookLanguage().toString().toLowerCase());
            ps.setString(6, book.getFileUrI());
            ps.setInt(7, book.getEdition());
            ps.setInt(8, book.getNbrDownloads());
            ps.setString(9, book.getStatus().toString().toLowerCase());
            ps.executeUpdate();
        }
    }

    public void delete(Book book, Connection cnx) throws SQLException {
        String query = "update books set book_deleted=1 where book_id=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, book.getId());
            ps.executeUpdate();
        }
    }

    public ArrayList<Book> findByName(String name, Connection cnx) throws SQLException{
        ArrayList<Book> books = new ArrayList<>();
        String query = "select * from books where book_name=? and book_deleted=0";
        try(PreparedStatement ps = cnx.prepareStatement(query)){
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    books.add(ResultSetMaper.mapToModel(rs, Book.class));
                }
            }
        }
        return books;
    }
}
