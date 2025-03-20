package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.BookTopicDao;
import com.hibali.IT_Library.models.classes.BookTopic;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class BookTopicService implements IService<BookTopic, Integer> {
    private final DbConnection dbConnection;
    private final BookTopicDao dao;

    public BookTopicService(DbConnection dbConnection, BookTopicDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    @Override
    public Optional<BookTopic> add(BookTopic bookTopic)
            throws FieldRequiredException, FieldUniqueException, BuisnessRulesException, SQLException {
        if (bookTopic.getBook_id() <= 0 || bookTopic.getTopic_id() <= 0) {
            throw new FieldRequiredException(new String[] { "book_id", "topic_id" });
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.insert(bookTopic, cnx);
            TransactionsResultsMessages.insertSuccess(bookTopic);
            return bookTopic;
        }, dbConnection);
    }

    @Override
    public ArrayList<BookTopic> getAll() throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findAll(cnx);
        }
    }

    @Override
    public Optional<BookTopic> getById(Integer id) throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findById(id, cnx);
        }
    }

    @Override
    public Optional<BookTopic> update(BookTopic bookTopic)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (bookTopic.getBook_id() <= 0 || bookTopic.getTopic_id() <= 0) {
            throw new FieldRequiredException(new String[] { "book_id", "topic_id" });
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.update(bookTopic, cnx);
            TransactionsResultsMessages.updateSuccess(bookTopic);
            return bookTopic;
        }, dbConnection);
    }

    @Override
    public Optional<BookTopic> delete(BookTopic bookTopic)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (bookTopic.getId() <= 0) {
            throw new FieldRequiredException("book_topic_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.delete(bookTopic, cnx);
            TransactionsResultsMessages.deleteSuccess(bookTopic);
            return bookTopic;
        }, dbConnection);
    }

}
