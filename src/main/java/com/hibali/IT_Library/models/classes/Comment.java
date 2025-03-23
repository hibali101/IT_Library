package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class Comment extends BaseModel {
    private int id;
    private int userId;
    private int bookId;
    private String Text;

    public Comment() {
        super();
    }

    public Comment(int userId, int bookId, String text) {
        this.userId = userId;
        this.bookId = bookId;
        Text = text;
    }

    public Comment(int id, int userId, int bookId,
            String text, boolean deleted, Timestamp created_at, Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        Text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    @Override
    public String toString() {
        return "Comment [deleted=" + deleted + ", id=" + id + ", userId=" + userId + ", created_at=" + created_at
                + ", bookId=" + bookId + ", updated_at=" + updated_at + ", Text=" + Text + "]";
    }

}
