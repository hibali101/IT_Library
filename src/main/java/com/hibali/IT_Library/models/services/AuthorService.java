package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class AuthorService implements IService<Author,Integer> {
    DbConnection dbConnection;

    public AuthorService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Author add(Author author) throws FieldRequiredException, FieldUniqueException {
        if (author.getName() != null) {
            try (Connection cnx = dbConnection.create()) {
                cnx.setAutoCommit(false);
                String query = "insert into authors (author_name, author_link) values (?,?)";
                try (PreparedStatement ps = cnx.prepareStatement(query)) {
                    ps.setString(1, author.getName());
                    ps.setString(2, author.getLink());
                    ps.executeUpdate();
                    cnx.commit();
                    System.out.println(author.toString() + " inserted successefully");
                    return author;
                } catch (SQLException e) {
                    cnx.rollback();
                    if (e.getMessage().contains("UNIQUE")) {
                        throw new FieldUniqueException("author_name");
                    }
                    System.out.println(e);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            throw new FieldRequiredException("author_name");
        }
        return null;
    }

    public ArrayList<Author> getAll() {
        ArrayList<Author> authors = new ArrayList<>();
        try (Connection cnx = dbConnection.create()) {
            String query = "select * from authors where author_deleted = 0";
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                authors.add(ResultSetMaper.mapToModel(result, Author.class));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return authors;
    }

    public Author getById(Integer id) {
        Author author = null;
        try (Connection cnx = this.dbConnection.create()) {
            PreparedStatement ps = cnx.prepareStatement("select * from authors where author_id=? and author_deleted = 0");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                author = ResultSetMaper.mapToModel(result, Author.class);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return author;
    }

    public Author update(Author author) throws FieldUniqueException, FieldRequiredException {
        if(author.getId() <= 0){
            throw new FieldRequiredException("author_id");
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            String query = "update authors set author_name = ?, author_link = ?, updated_at = GETDATE() where authors.author_id = ?";
            try (PreparedStatement ps = cnx.prepareStatement(query)) {
                ps.setString(1, author.getName());
                ps.setString(2, author.getLink());
                ps.setInt(3, author.getId());
                ps.executeUpdate();
                cnx.commit();
                System.out.println(author.toString() + " updated successefully");
                return author;
            } catch (SQLException e) {
                cnx.rollback();
                if (e.getMessage().contains("UNIQUE")) {
                    throw new FieldUniqueException("name must be unique");
                }
                System.out.println(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public Author delete(Author author) throws FieldRequiredException{
        if(author.getId() <= 0){
            throw new FieldRequiredException("author_id");
        }
        try(Connection cnx = dbConnection.create()){
            cnx.setAutoCommit(false);
            try(PreparedStatement ps = cnx.prepareStatement("update authors set author_deleted = 1 where author_id = ?")){
                ps.setInt(1, author.getId());
                ps.executeUpdate();
                cnx.commit();
                System.out.println(author.getName() + " deleted successfully");
                return author;
            }catch(SQLException ex){
                cnx.rollback();
                System.out.println(ex.getMessage());
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
