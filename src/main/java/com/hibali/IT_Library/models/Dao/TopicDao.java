package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class TopicDao {
    public void insert(Topic topic, Connection cnx) throws SQLException {
        String query = "insert into topics (topic_name) values (?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, topic.getName());
            ps.executeUpdate();
        }
    }

    public ArrayList<Topic> findAll(Connection cnx) throws SQLException {
        ArrayList<Topic> topics = new ArrayList<>();
        String query = "select * from topics where topic_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                topics.add(ResultSetMaper.mapToModel(rs, Topic.class));
            }
        }
        return topics;
    }

    public Topic findById(int id, Connection cnx) throws SQLException {
        String query = "select * from topics where topic_id=? and topic_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    return ResultSetMaper.mapToModel(result, Topic.class);
                }
            }
        }
        return null;
    }

    public void update(Topic topic, Connection cnx) throws SQLException {
        String query = "update topics set topic_name = ?, updated_at = GETDATE() where topics.topic_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, topic.getName());
            ps.setInt(2, topic.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Topic topic, Connection cnx) throws SQLException {
        String query = "update topics set topic_deleted = 1 where topic_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, topic.getId());
            ps.executeUpdate();
        }
    }
}
