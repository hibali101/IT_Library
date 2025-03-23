package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.models.classes.BookTopic;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class BookTopicDao implements IDao<BookTopic, Integer> {

    @Override
    public void insert(BookTopic bookTopic, Connection cnx) throws SQLException {
        String query = "insert into books_topics (book_id, topic_id) values (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, bookTopic.getBook_id());
            ps.setInt(2, bookTopic.getTopic_id());
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<BookTopic> findAll(Connection cnx) throws SQLException {
        ArrayList<BookTopic> booksTopics = new ArrayList<>();
        String query = "select * from books_topics";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                booksTopics.add(ResultSetMaper.mapToModel(rs, BookTopic.class));
            }
            return booksTopics;
        }
    }

    @Override
    public Optional<BookTopic> findById(Integer id, Connection cnx) throws SQLException {
        String query = "select * from books_topics where book_topic_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return Optional.ofNullable(ResultSetMaper.mapToModel(rs, BookTopic.class));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(BookTopic bookTopic, Connection cnx) throws SQLException {
        String query = "update books_topics set book_id = ?, topic_id = ?, updated_at = GETDATE() where book_topic_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, bookTopic.getBook_id());
            ps.setInt(2, bookTopic.getTopic_id());
            ps.setInt(3, bookTopic.getId());
            ps.executeUpdate();
        }

    }

    @Override
    public void delete(BookTopic bookTopic, Connection cnx) throws SQLException {
        String query = "delete books_topics book_topic_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, bookTopic.getId());
            ps.executeUpdate();
        }
    }
}
