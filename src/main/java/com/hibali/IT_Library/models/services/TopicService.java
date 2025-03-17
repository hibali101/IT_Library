package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.TopicDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class TopicService implements IService<Topic, Integer> {

    private final DbConnection dbConnection;
    private final TopicDao topicDao;

    public TopicService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.topicDao = new TopicDao();
    }

    public Topic add(Topic topic) throws FieldRequiredException, FieldUniqueException {
        if (topic.getName() != null) {
            try (Connection cnx = dbConnection.create()) {
                cnx.setAutoCommit(false);
                try {
                    topicDao.insert(topic, cnx);
                    cnx.commit();
                    TransactionsResultsMessages.insertSuccess(topic);
                    return topic;
                } catch (SQLException e) {
                    cnx.rollback();
                    if (e.getMessage().contains("UNIQUE")) {
                        throw new FieldUniqueException("topic_name");
                    }
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new FieldRequiredException("topic_name");
        }
        return null;
    }

    public ArrayList<Topic> getAll() {
        try (Connection cnx = dbConnection.create()) {
            return topicDao.findAll(cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Topic getById(Integer id) {
        try (Connection cnx = this.dbConnection.create()) {
            return topicDao.findById(id, cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Topic update(Topic topic) throws FieldUniqueException, FieldRequiredException {
        if (topic.getId() <= 0) {
            throw new FieldRequiredException("topic_id");
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            try {
                topicDao.update(topic, cnx);
                cnx.commit();
                TransactionsResultsMessages.updateSuccess(topic);
                return topic;
            } catch (SQLException e) {
                cnx.rollback();
                if (e.getMessage().contains("UNIQUE")) {
                    throw new FieldUniqueException("topic_name");
                }
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Topic delete(Topic topic) throws FieldRequiredException {
        if (topic.getId() <= 0) {
            throw new FieldRequiredException("topic_id");
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            try {
                topicDao.delete(topic, cnx);
                cnx.commit();
                TransactionsResultsMessages.deleteSuccess(topic);
                return topic;
            } catch (SQLException e) {
                cnx.rollback();
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
