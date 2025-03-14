package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class Author extends BaseModel {
    private int id;
    private String name;
    private String link;
    public Author(){
        super();
    }
    public Author(String name){
        this.name = name;
    }
    public Author(String name,String link){
        this.name = name;
        this.link = link;
    }
    public Author( int id, String name, String link,boolean deleted, Timestamp created_at, Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.name = name;
        this.link = link;
    }
    public Author(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
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
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    @Override
    public String toString() {
        return "Author [deleted=" + deleted + ", id=" + id + ", name=" + name + ", created_at=" + created_at + ", link="
                + link + ", updated_at=" + updated_at + "]";
    }
    
}
