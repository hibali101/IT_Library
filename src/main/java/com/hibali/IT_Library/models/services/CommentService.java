
package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.customExceptions.HashingException;
import com.hibali.IT_Library.models.Dao.CommentDao;
import com.hibali.IT_Library.models.classes.Comment;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class CommentService implements IService<Comment, Integer> {
    private DbConnection dbConnection;
    private CommentDao dao;

    public CommentService(DbConnection dbConnection, CommentDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    @Override
    public Optional<Comment> add(Comment comment) throws FieldRequiredException, FieldUniqueException,
            BuisnessRulesException, SQLException, HashingException {
        if (comment.getBookId() <= 0 || comment.getUserId() <= 0 || comment.getText() == null) {
            throw new FieldRequiredException(new String[] { "user_id", "book_id", "comment_text" });
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.insert(comment, cnx);
            TransactionsResultsMessages.insertSuccess(comment);
            return comment;
        }, dbConnection);
    }

    @Override
    public ArrayList<Comment> getAll() throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findAll(cnx);
        }
    }

    @Override
    public Optional<Comment> getById(Integer id) throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findById(id, cnx);
        }
    }

    @Override
    public Optional<Comment> update(Comment comment)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if(comment.getId() <= 0 || comment.getText() == null){
            throw new FieldRequiredException(new String[]{"comment_id","comment_text"});
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.update(comment, cnx);
            TransactionsResultsMessages.updateSuccess(comment);
            return comment;
        }, dbConnection);
    }

    @Override
    public Optional<Comment> delete(Comment comment)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if(comment.getId() <= 0){
            throw new FieldRequiredException("comment_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.delete(comment, cnx);
            TransactionsResultsMessages.deleteSuccess(comment);
            return comment;
        }, dbConnection);
    }
}
