package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.TopicDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class TopicService implements IService<Topic, Integer> {

    private final DbConnection dbConnection;
    private final TopicDao topicDao;

    public TopicService(DbConnection dbConnection, TopicDao dao) {
        this.dbConnection = dbConnection;
        this.topicDao = dao;
    }

    public Optional<Topic> add(Topic topic)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (topic.getName() == null) {
            throw new FieldRequiredException("topic_name");
        }
        return ExecuteInTransaction.execute(cnx -> {
            topicDao.insert(topic, cnx);
            TransactionsResultsMessages.insertSuccess(topic);
            return topic;
        }, dbConnection);
    }

    public ArrayList<Topic> getAll() throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return topicDao.findAll(cnx);
        }
    }

    public Optional<Topic> getById(Integer id) throws SQLException {
        try (Connection cnx = this.dbConnection.create()) {
            return topicDao.findById(id, cnx);
        }
    }

    public Optional<Topic> update(Topic topic)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (topic.getId() <= 0) {
            throw new FieldRequiredException("topic_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            topicDao.update(topic, cnx);
            TransactionsResultsMessages.updateSuccess(topic);
            return topic;
        }, dbConnection);
    }

    public Optional<Topic> delete(Topic topic)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (topic.getId() <= 0) {
            throw new FieldRequiredException("topic_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            topicDao.delete(topic, cnx);
            TransactionsResultsMessages.deleteSuccess(topic);
            return topic;
        }, dbConnection);
    }
}
