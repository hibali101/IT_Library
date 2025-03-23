package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.customExceptions.HashingException;
import com.hibali.IT_Library.models.Dao.RatingDao;
import com.hibali.IT_Library.models.Dao.TopicDao;
import com.hibali.IT_Library.models.classes.CompositeKey;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Rating;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class RatingService implements IService<Rating, CompositeKey> {

    private DbConnection dbConnection;
    private RatingDao dao;

    public RatingService(DbConnection dbConnection, RatingDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    @Override
    public Optional<Rating> add(Rating rating) throws FieldRequiredException, FieldUniqueException,
            BuisnessRulesException, SQLException, HashingException {
        if (rating.getUserId() <= 0 || rating.getBookId() <= 0) {
            throw new FieldRequiredException(new String[] { "user_id", "book_id" });
        }
        if (rating.getScore() < 0 || rating.getBookId() > 0) {
            throw new BuisnessRulesException("rating_score must be between 0 and 5");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.insert(rating, cnx);
            TransactionsResultsMessages.insertSuccess(rating);
            return rating;
        }, dbConnection);
    }

    @Override
    public ArrayList<Rating> getAll() throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findAll(cnx);
        }
    }

    @Override
    public Optional<Rating> getById(CompositeKey id) throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findById(id, cnx);
        }
    }

    @Override
    public Optional<Rating> update(Rating rating)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (rating.getUserId() <= 0 || rating.getBookId() <= 0) {
            throw new FieldRequiredException(new String[] { "user_id", "book_id" });
        }
        if (rating.getScore() < 0 || rating.getBookId() > 0) {
            throw new BuisnessRulesException("rating_score must be between 0 and 5");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.update(rating, cnx);
            TransactionsResultsMessages.updateSuccess(rating);
            return rating;
        }, dbConnection);
    }

    @Override
    public Optional<Rating> delete(Rating rating)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (rating.getUserId() <= 0 || rating.getBookId() <= 0) {
            throw new FieldRequiredException(new String[] { "user_id", "book_id" });
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.delete(rating, cnx);
            TransactionsResultsMessages.deleteSuccess(rating);
            return rating;
        }, dbConnection);
    }

}
