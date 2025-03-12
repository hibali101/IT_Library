package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class Topic extends BaseModel {
    private int id;
    private String topic_name;

    public Topic() {
        super();
    }
    public Topic(String name) {
        topic_name = name;
    }
    public Topic(int id, String topic_name, boolean deleted, Timestamp created_at,
            Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.topic_name = topic_name;
    }

    public int getId() {
        return id;
    }
    public String getTopic_name() {
        return topic_name;
    }
    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Topic [id=" + id + ", topic_deleted=" + deleted + ", topic_name=" + topic_name + ", created_at="
                + created_at + ", updated_at=" + updated_at + ", getTopic_name()=" + getTopic_name() + "]";
    }

}
