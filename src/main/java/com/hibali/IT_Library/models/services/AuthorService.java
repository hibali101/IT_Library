package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.AuthorDao;
import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class AuthorService implements IService<Author, Integer> {
    private final DbConnection dbConnection;
    private final AuthorDao authorDao;

    public AuthorService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.authorDao = new AuthorDao();
    }

    public Author add(Author author) throws FieldRequiredException, FieldUniqueException {
        if (author.getName() != null) {
            try (Connection cnx = dbConnection.create()) {
                cnx.setAutoCommit(false);
                try {
                    authorDao.insert(author, cnx);
                    cnx.commit();
                    TransactionsResultsMessages.insertSuccess(author);
                    return author;
                } catch (SQLException e) {
                    cnx.rollback();
                    if (e.getMessage().contains("UNIQUE")) {
                        throw new FieldUniqueException("author_name");
                    }
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new FieldRequiredException("author_name");
        }
        return null;
    }

    public ArrayList<Author> getAll() {
        try (Connection cnx = dbConnection.create()) {
            return authorDao.findAll(cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Author getById(Integer id) {
        try (Connection cnx = this.dbConnection.create()) {
            return authorDao.findById(id, cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Author update(Author author) throws FieldUniqueException, FieldRequiredException {
        if (author.getId() <= 0) {
            throw new FieldRequiredException("author_id");
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            try{
                authorDao.update(author, cnx);
                cnx.commit();
                TransactionsResultsMessages.updateSuccess(author);
                return author;
            } catch (SQLException e) {
                cnx.rollback();
                if (e.getMessage().contains("UNIQUE")) {
                    throw new FieldUniqueException("topic_name");
                }
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Author delete(Author author) throws FieldRequiredException {
        if (author.getId() <= 0) {
            throw new FieldRequiredException("author_id");
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            try {
                authorDao.delete(author, cnx);
                cnx.commit();
                TransactionsResultsMessages.deleteSuccess(author);
                return author;
            } catch (SQLException e) {
                cnx.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
