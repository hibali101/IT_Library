package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.AuthorDao;
import com.hibali.IT_Library.models.classes.Author;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class AuthorService implements IService<Author, Integer> {
    private final DbConnection dbConnection;
    private final AuthorDao authorDao;

    public AuthorService(DbConnection dbConnection,AuthorDao dao) {
        this.dbConnection = dbConnection;
        this.authorDao = dao;
    }

    public Optional<Author> add(Author author) throws FieldRequiredException, FieldUniqueException {
        if (author.getName() == null){
            throw new FieldRequiredException("author_name");
        }
        return ExecuteInTransaction.execute(cnx->{
            authorDao.insert(author, cnx);
            TransactionsResultsMessages.insertSuccess(author);
            return author;
        }, dbConnection);
    }

    public ArrayList<Author> getAll() {
        try (Connection cnx = dbConnection.create()) {
            return authorDao.findAll(cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<Author> getById(Integer id) {
        try (Connection cnx = this.dbConnection.create()) {
            return authorDao.findById(id, cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Author> update(Author author) throws FieldUniqueException, FieldRequiredException {
        if (author.getId() <= 0) {
            throw new FieldRequiredException("author_id");
        }
        return ExecuteInTransaction.execute(cnx->{
            authorDao.update(author, cnx);
            TransactionsResultsMessages.updateSuccess(author);
            return author;
        }, dbConnection);
    }

    public Optional<Author> delete(Author author) throws FieldRequiredException {
        if (author.getId() <= 0) {
            throw new FieldRequiredException("author_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            authorDao.delete(author, cnx);
            TransactionsResultsMessages.deleteSuccess(author);
                return author;
        }, dbConnection);
    }
}
