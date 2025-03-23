package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.models.classes.Log;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class LogDao implements IDao<Log, Integer> {
    @Override
    public void insert(Log log, Connection cnx) throws SQLException {
        String query = "insert into logs (user_id, log_date) values (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, log.getUserId());
            ps.setTimestamp(2, log.getDate());
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<Log> findAll(Connection cnx) throws SQLException {
        ArrayList<Log> logs = new ArrayList<>();
        String query = "select * from logs";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                logs.add(ResultSetMaper.mapToModel(rs, Log.class));
            }
        }
        return logs;
    }

    @Override
    public Optional<Log> findById(Integer id, Connection cnx) throws SQLException {
        String query = "select * from logs where log_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return Optional.ofNullable(ResultSetMaper.mapToModel(rs, Log.class));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Log log, Connection cnx) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Log log, Connection cnx) throws SQLException {
        // TODO Auto-generated method stub

    }

    //More than mere crud
    public ArrayList<Log> findByUserId(int userId, Connection cnx) throws SQLException{
        ArrayList<Log> logs = new ArrayList<>();
        String query = "select * from logs where user_id = ? order by log_date";
        try(PreparedStatement ps = cnx.prepareStatement(query)){
            ps.setInt(1, userId);
            try(ResultSet rs = ps.executeQuery()){
                logs.add(ResultSetMaper.mapToModel(rs, Log.class));
            }
        }
        return logs;
    }

}
