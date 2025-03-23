package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.models.classes.Comment;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class CommentDao implements IDao<Comment, Integer> {

    @Override
    public void insert(Comment comment, Connection cnx) throws SQLException {
        String query = "insert into comments (user_id, book_id, comment_text) values (?,?,?)";
        try(PreparedStatement ps = cnx.prepareStatement(query)){
            ps.setInt(1, comment.getUserId());
            ps.setInt(2, comment.getBookId());
            ps.setString(3, comment.getText());
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<Comment> findAll(Connection cnx) throws SQLException {
        ArrayList<Comment> comments = new ArrayList<>();
        String query = "select * from comments where comment_deleted = 0";
        try(PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
               comments.add(ResultSetMaper.mapToModel(rs, Comment.class));
            }
        }
        return comments;
    }

    @Override
    public Optional<Comment> findById(Integer id, Connection cnx) throws SQLException {
        String query = "select * from comments where comment_id = ? and comment_deleted = 0";
        try(PreparedStatement ps = cnx.prepareStatement(query)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    return Optional.ofNullable(ResultSetMaper.mapToModel(rs, Comment.class));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Comment comment, Connection cnx) throws SQLException {
        String query = "update comments set comment_text = ?, updated_at = GETDATE() where comment_id = ?";
        try(PreparedStatement ps = cnx.prepareStatement(query)){
            ps.setString(1, comment.getText());
            ps.setInt(2, comment.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Comment comment, Connection cnx) throws SQLException {
        String query = "update comment set comment_deleted = 1 where comment_id = ?";
        try(PreparedStatement ps = cnx.prepareStatement(query)){
            ps.setInt(1, comment.getId());
            ps.executeUpdate();
        }
    }
    
}
