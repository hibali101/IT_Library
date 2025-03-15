package com.hibali.IT_Library.models.classes;

import java.sql.Date;
import java.sql.Timestamp;

import com.hibali.IT_Library.enums.BookLanguages;
import com.hibali.IT_Library.enums.BookStatus;

public class Book extends BaseModel {
    private int id;
    private int authorId;
    private String name;
    private Date publishDate;
    private String description;
    private BookLanguages bookLanguage;
    private String fileUrI;
    private int edition;
    private int nbrDownloads;
    private BookStatus status;

    public Book() {
        super();
    }

    public Book(int authorId, String name,
            Date publishDate, String description, BookLanguages bookLanguage, String fileUrI, int edition, BookStatus status) {
        this.authorId = authorId;
        this.name = name;
        this.publishDate = publishDate;
        this.description = description;
        this.bookLanguage = bookLanguage;
        this.fileUrI = fileUrI;
        this.edition = edition;
        this.status = status;
    }

    public Book(boolean deleted, Timestamp created_at, Timestamp updated_at, int id, int authorId, String name,
            Date publishDate, String description, BookLanguages bookLanguage, String fileUrI, int edition,
            int nbrDownloads, BookStatus status) {
        super(deleted, created_at, updated_at);
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.publishDate = publishDate;
        this.description = description;
        this.bookLanguage = bookLanguage;
        this.fileUrI = fileUrI;
        this.edition = edition;
        this.nbrDownloads = nbrDownloads;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookLanguages getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(BookLanguages bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public String getFileUrI() {
        return fileUrI;
    }

    public void setFileUrI(String fileUrI) {
        this.fileUrI = fileUrI;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getNbrDownloads() {
        return nbrDownloads;
    }

    public void setNbrDownloads(int nbrDownloads) {
        this.nbrDownloads = nbrDownloads;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book [deleted=" + deleted + ", id=" + id + ", created_at=" + created_at + ", authorId=" + authorId
                + ", updated_at=" + updated_at + ", name=" + name + ", publishDate=" + publishDate + ", description="
                + description + ", bookLanguage=" + bookLanguage + ", fileUrI=" + fileUrI + ", edition=" + edition
                + ", nbrDownloads=" + nbrDownloads + ", status=" + status + "]";
    }

}
