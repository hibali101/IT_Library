package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import com.hibali.IT_Library.models.classes.User;
import com.hibali.IT_Library.utilities.QueryBuilder;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class UserDao implements IDao<User, Integer> {

    @Override
    public void insert(User user, Connection cnx) throws SQLException {
        String query = "insert into users (user_name, user_password, user_email, user_phone," +
                " user_is_admin) values (?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setBoolean(5, user.isAdmin());
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<User> findAll(Connection cnx) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String query = "select * from users where user_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(ResultSetMaper.mapToModel(rs, User.class));
            }
        }
        return users;
    }

    @Override
    public Optional<User> findById(Integer id, Connection cnx) throws SQLException {
        String query = "select * from users where user_id = ? and user_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return Optional.ofNullable(ResultSetMaper.mapToModel(rs, User.class));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(User user, Connection cnx) throws SQLException {
        Map.Entry<String, List<Object>> queryWithNonNullEntries = QueryBuilder.buildUpdateQuery(user, User.class);
        String query = queryWithNonNullEntries.getKey();
        List<Object> values = queryWithNonNullEntries.getValue();
        try(PreparedStatement ps = cnx.prepareStatement(query)){
            for(int i = 0; i<values.size(); i++){
                Object value = values.get(i);
                int paramIndex = i+1;
                QueryBuilder.setPreparedStatementValue(ps, paramIndex, value);
            }
            ps.setInt( values.size()+1, user.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(User user, Connection cnx) throws SQLException {
        String query = "update users set user_deleted = 1 where user_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        }

    }
}
