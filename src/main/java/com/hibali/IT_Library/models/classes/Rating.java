package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class Rating extends BaseModel {
    private CompositeKey id;
    private int userId;
    private int bookId;
    private int score;

    public Rating() {
        super();
    }

    public Rating(int userId, int bookId, int score) {
        CompositeKey id = new CompositeKey(userId, bookId);
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
    }

    public Rating(int userId, int bookId, int score, boolean deleted, Timestamp created_at, Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        CompositeKey id = new CompositeKey(userId, bookId);
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
    }

    public CompositeKey getId() {
        return id;
    }

    public void setId(CompositeKey id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Rating [id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", score=" + score + "]";
    }
    
}
