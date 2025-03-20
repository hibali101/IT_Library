package com.hibali.IT_Library.models.classes;

import java.sql.Timestamp;

public class BookProgrammingLanguage extends BaseModel {
    private int id;
    private int bookId;
    private int programmingLanguageId;

    public BookProgrammingLanguage() {
        super();
    }

    public BookProgrammingLanguage(int bookId, int programmingLanguageId) {
        this.bookId = bookId;
        this.programmingLanguageId = programmingLanguageId;
    }

    public BookProgrammingLanguage(int id, int bookId,
            int programmingLanguageId, boolean deleted, Timestamp created_at, Timestamp updated_at) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.bookId = bookId;
        this.programmingLanguageId = programmingLanguageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getProgrammingLanguageId() {
        return programmingLanguageId;
    }

    public void setProgrammingLanguageId(int programmingLanguageId) {
        this.programmingLanguageId = programmingLanguageId;
    }

    @Override
    public String toString() {
        return "BookProgrammingLanguage [id=" + id + ", bookId=" + bookId + ", programmingLanguageId="
                + programmingLanguageId + "]";
    }
}
