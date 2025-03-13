package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class Topic extends BaseModel {
    private int id;
    private String name;

    public Topic() { 
        super();
    }
    public Topic(String name) {
        this.name = name;
    }
    public Topic(int id, String name, boolean deleted, Timestamp created_at,
            Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Topic [id=" + id + ", topic_deleted=" + deleted + ", name=" + name + ", created_at="
                + created_at + ", updated_at=" + updated_at + "]";
    }

}
