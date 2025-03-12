package com.hibali.IT_Library.models.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Topic;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class TopicService implements IService<Topic,Integer> {
    DbConnection dbConnection;
    Connection cnx;
    public TopicService(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
        try{
            this.cnx = dbConnection.create();
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }

    public void add(Topic topic) throws FieldRequiredException, FieldUniqueException {
        if (topic.getTopic_name() != null) {
            try (Connection cnx = dbConnection.create()) {
                String query = "insert into topics (topic_name) values (?)";
                PreparedStatement ps = cnx.prepareStatement(query);
                ps.setString(1, topic.getTopic_name());
                ps.executeUpdate();
                System.out.println(topic.toString() + " inserted successefully");
            } catch (SQLException ex) {
                if(ex.getMessage().contains("UNIQUE")){
                    throw new FieldUniqueException("name must be unique");
                }
            }
        } else {
            throw new FieldRequiredException("topic_name is required");
        }
    }

    public ArrayList<Topic> getAll(){
        ArrayList<Topic> topics = new ArrayList<>();
        try (Connection cnx = dbConnection.create()) {
            String query = "select * from topics";
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                topics.add(ResultSetMaper.mapToModel(result, Topic.class));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return topics;
    }

    public Topic getById(Integer id){
        Topic topic = null;
        try(PreparedStatement ps =  this.cnx.prepareStatement("select * from topics where topic_id=?")){
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                topic = ResultSetMaper.mapToModel(result, Topic.class);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return topic;
    }

    
    
}
