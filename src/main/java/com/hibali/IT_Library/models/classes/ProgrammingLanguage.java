package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class ProgrammingLanguage extends BaseModel {

    private int id;
    private String name;

    public ProgrammingLanguage() {
        super();
    }

    public ProgrammingLanguage(String n) {
        this.name = n;
    }

    public ProgrammingLanguage(int id, String name, boolean deleted, Timestamp created_at,
            Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProgrammingLanguage [deleted=" + deleted + ", id=" + id + ", created_at=" + created_at + ", name="
                + name + ", updated_at=" + updated_at + "]";
    }

}
