package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class BookTopic extends BaseModel {
    private int id;
    private int book_id;
    private int topic_id;

    public BookTopic() {
        super();
    }

    public BookTopic(int book_id, int topic_id) {
        this.book_id = book_id;
        this.topic_id = topic_id;
    }

    public BookTopic(int id, int book_id, int topic_id,boolean deleted, Timestamp created_at, Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.book_id = book_id;
        this.topic_id = topic_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    @Override
    public String toString() {
        return "BookTopic [id=" + id + ", book_id=" + book_id + ", deleted=" + deleted + ", topic_id=" + topic_id
                + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
    }
    
}
