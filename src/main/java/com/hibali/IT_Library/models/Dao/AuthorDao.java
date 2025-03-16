package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class AuthorDao implements IDao<Author> {
    public void insert(Author author, Connection cnx) throws SQLException {
        String query = "insert into authors (author_name, author_link) values (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getLink());
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<Author> findAll(Connection cnx) throws SQLException {
        ArrayList<Author> authors = new ArrayList<>();
        String query = "select * from authors where author_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    authors.add(ResultSetMaper.mapToModel(result, Author.class));
                }
            }
        }
        return authors;
    }

    @Override
    public Author findById(int id, Connection cnx) throws SQLException {
        String query = "select * from authors where author_id=? and author_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    return ResultSetMaper.mapToModel(result, Author.class);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Author author, Connection cnx) throws SQLException {
        String query = "update authors set author_name = ?, author_link = ?, updated_at = GETDATE() where authors.author_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getLink());
            ps.setInt(3, author.getId());
            ps.executeUpdate();
            System.out.println(author.toString() + " updated successefully");
        }
    }

    @Override
    public void delete(Author author, Connection cnx) throws SQLException {
        String query = "update authors set author_deleted = 1 where author_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, author.getId());
            ps.executeUpdate();
        }
    }
}
