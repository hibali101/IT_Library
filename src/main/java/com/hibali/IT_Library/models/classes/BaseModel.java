package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public abstract class BaseModel {
    public boolean deleted;
    public Timestamp created_at;
    public Timestamp updated_at;

    protected BaseModel(){}

    protected BaseModel(boolean deleted, Timestamp created_at, Timestamp updated_at) {
        this.deleted = deleted;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    public boolean isdeleted() {
        return deleted;
    }
    public void setdeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public Timestamp getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
    public Timestamp getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
    public abstract String toString();
    
}
