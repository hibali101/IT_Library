package com.hibali.IT_Library.models.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class TopicService implements IService<Topic, Integer> {

    private final DbConnection dbConnection;

    public TopicService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Topic add(Topic topic) throws FieldRequiredException, FieldUniqueException {
        if (topic.getName() != null) {
            try (Connection cnx = dbConnection.create()) {
                cnx.setAutoCommit(false);
                String query = "insert into topics (topic_name) values (?)";
                try (PreparedStatement ps = cnx.prepareStatement(query)) {
                    ps.setString(1, topic.getName());
                    ps.executeUpdate();
                    cnx.commit();
                    System.out.println(topic.toString() + " inserted successefully");
                    return topic;
                } catch (SQLException e) {
                    cnx.rollback();
                    if (e.getMessage().contains("UNIQUE")) {
                        throw new FieldUniqueException("topic_name");
                    }
                    System.out.println(e);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            throw new FieldRequiredException("topic_name");
        }
        return null;
    }

    public ArrayList<Topic> getAll() {
        ArrayList<Topic> topics = new ArrayList<>();
        String query = "select * from topics where topic_deleted = 0";
        try (Connection cnx = dbConnection.create();
                PreparedStatement ps = cnx.prepareStatement(query);
                ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                topics.add(ResultSetMaper.mapToModel(result, Topic.class));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return topics;
    }

    public Topic getById(Integer id) {
        Topic topic = null;
        try (Connection cnx = this.dbConnection.create();
                PreparedStatement ps = cnx
                        .prepareStatement("select * from topics where topic_id=? and topic_deleted = 0")) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    topic = ResultSetMaper.mapToModel(result, Topic.class);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return topic;
    }

    public Topic update(Topic topic) throws FieldUniqueException, FieldRequiredException {
        if (topic.getId() <= 0) {
            throw new FieldRequiredException("topic_id");
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            String query = "update topics set topic_name = ?, updated_at = GETDATE() where topics.topic_id = ?";
            try (PreparedStatement ps = cnx.prepareStatement(query)) {
                ps.setString(1, topic.getName());
                ps.setInt(2, topic.getId());
                ps.executeUpdate();
                cnx.commit();
                System.out.println(topic.toString() + " updated successefully");
                return topic;
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

    public Topic delete(Topic topic) throws FieldRequiredException {
        if (topic.getId() <= 0) {
            throw new FieldRequiredException("topic_id");
        }
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            try (PreparedStatement ps = cnx
                    .prepareStatement("update topics set topic_deleted = 1 where topic_id = ?")) {
                ps.setInt(1, topic.getId());
                ps.executeUpdate();
                cnx.commit();
                System.out.println(topic.getName() + " deleted successfully");
                return topic;
            } catch (SQLException ex) {
                cnx.rollback();
                System.out.println(ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
