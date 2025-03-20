package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class User extends BaseModel {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private boolean isAdmin;

    public User() {
        super();
    }

    public User(String name, String password, String email, String phone, boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    public User(boolean deleted, Timestamp created_at, Timestamp updated_at, int id, String name, String password,
            String email, String phone, boolean isAdmin) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User [deleted=" + deleted + ", id=" + id + ", name=" + name + ", created_at=" + created_at
                + ", password=" + password + ", updated_at=" + updated_at + ", email=" + email + ", phone=" + phone
                + ", isAdmin=" + isAdmin + "]";
    }
    

}
