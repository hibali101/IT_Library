package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class Log extends BaseModel {
    private int id;
    private int userId;
    private Timestamp date;

    public Log() {
        super();
    }

    public Log(int userId, Timestamp date) {
        this.userId = userId;
        this.date = date;
    }

    public Log(int id, int userId, Timestamp date, boolean deleted, Timestamp created_at, Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.userId = userId;
        this.date = date;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Log [id=" + id + ", deleted=" + deleted + ", userId=" + userId + ", created_at=" + created_at
                + ", date=" + date + ", updated_at=" + updated_at + "]";
    }

}
