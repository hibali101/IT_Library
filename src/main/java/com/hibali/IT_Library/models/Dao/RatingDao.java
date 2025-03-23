package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.models.classes.CompositeKey;
import com.hibali.IT_Library.models.classes.Rating;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class RatingDao implements IDao<Rating, CompositeKey> {

    @Override
    public void insert(Rating rating, Connection cnx) throws SQLException {
        String query = "insert into ratings (user_id, book_id, rating_score) values (?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, rating.getUserId());
            ps.setInt(2, rating.getBookId());
            ps.setInt(3, rating.getScore());
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<Rating> findAll(Connection cnx) throws SQLException {
        ArrayList<Rating> ratings = new ArrayList<>();
        String query = "select * from ratings where rating_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ratings.add(ResultSetMaper.mapToModel(rs, Rating.class));
            }
        }
        return ratings;
    }

    @Override
    public Optional<Rating> findById(CompositeKey id, Connection cnx) throws SQLException {
        String query = "select * from ratings where user_id = ? and book_id = ? and rating_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id.getKeyPart1());
            ps.setInt(2, id.getKeyPart2());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return Optional.ofNullable(ResultSetMaper.mapToModel(rs, Rating.class));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Rating rating, Connection cnx) throws SQLException {
        String query = "update ratings set rating_score = ? where user_id = ? and book_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, rating.getScore());
            ps.setInt(2, rating.getUserId());
            ps.setInt(3, rating.getBookId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Rating rating, Connection cnx) throws SQLException {
        String query = "update ratings set rating_deleted = 1 where user_id = ? and book_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, rating.getUserId());
            ps.setInt(2, rating.getBookId());
            ps.executeUpdate();
        }
    }
}
