package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.models.classes.BookProgrammingLanguage;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class BookProgrammingLanguageDao implements IDao<BookProgrammingLanguage> {

    @Override
    public void insert(BookProgrammingLanguage bookProgrammingLanguage, Connection cnx) throws SQLException {
        String query = "insert into books_prog_langs (book_id, prog_lang_id) values (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, bookProgrammingLanguage.getBookId());
            ps.setInt(2, bookProgrammingLanguage.getProgrammingLanguageId());
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<BookProgrammingLanguage> findAll(Connection cnx) throws SQLException {
        ArrayList<BookProgrammingLanguage> booksProgLangs = new ArrayList<>();
        String query = "select * from books_prog_langs";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                booksProgLangs.add(ResultSetMaper.mapToModel(rs, BookProgrammingLanguage.class));
            }
        }
        return booksProgLangs;
    }

    @Override
    public Optional<BookProgrammingLanguage> findById(int id, Connection cnx) throws SQLException {
        String query = "select * from books_prog_langs where book_prog_lang_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return Optional.ofNullable(ResultSetMaper.mapToModel(rs, BookProgrammingLanguage.class));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(BookProgrammingLanguage bookProgrammingLanguage, Connection cnx) throws SQLException {
        String query = "update books_prog_langs set book_id = ? , prog_lang_id = ?, updated_at = GETDATE()" +
                " where book_prog_lang_id = ? ";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, bookProgrammingLanguage.getBookId());
            ps.setInt(2, bookProgrammingLanguage.getProgrammingLanguageId());
            ps.setInt(3, bookProgrammingLanguage.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(BookProgrammingLanguage bookProgrammingLanguage, Connection cnx) throws SQLException {
        String query = "delete books_prog_langs where book_prog_lang_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, bookProgrammingLanguage.getId());
            ps.executeUpdate();
        }
    }

}
