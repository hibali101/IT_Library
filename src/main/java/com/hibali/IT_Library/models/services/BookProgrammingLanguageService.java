package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.BookProgrammingLanguageDao;
import com.hibali.IT_Library.models.classes.BookProgrammingLanguage;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class BookProgrammingLanguageService implements IService<BookProgrammingLanguage, Integer> {
    private final DbConnection dbConnection;
    private final BookProgrammingLanguageDao dao;

    public BookProgrammingLanguageService(DbConnection dbConnection, BookProgrammingLanguageDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    @Override
    public Optional<BookProgrammingLanguage> add(BookProgrammingLanguage bookProgrammingLanguage)
            throws FieldRequiredException, FieldUniqueException, BuisnessRulesException, SQLException {
        if (bookProgrammingLanguage.getBookId() <= 0 || bookProgrammingLanguage.getProgrammingLanguageId() <= 0) {
            throw new FieldRequiredException(new String[] { "book_id", "prog_lang_id" });
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.insert(bookProgrammingLanguage, cnx);
            TransactionsResultsMessages.insertSuccess(bookProgrammingLanguage);
            return bookProgrammingLanguage;
        }, dbConnection);
    }

    @Override
    public ArrayList<BookProgrammingLanguage> getAll() throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findAll(cnx);
        }
    }

    @Override
    public Optional<BookProgrammingLanguage> getById(Integer id) throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findById(id, cnx);
        }
    }

    @Override
    public Optional<BookProgrammingLanguage> update(BookProgrammingLanguage bookProgrammingLanguage)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (bookProgrammingLanguage.getBookId() <= 0 || bookProgrammingLanguage.getProgrammingLanguageId() <= 0) {
            throw new FieldRequiredException(new String[] { "book_id", "prog_lang_id" });
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.update(bookProgrammingLanguage, cnx);
            TransactionsResultsMessages.updateSuccess(bookProgrammingLanguage);
            return bookProgrammingLanguage;
        }, dbConnection);
    }

    @Override
    public Optional<BookProgrammingLanguage> delete(BookProgrammingLanguage bookProgrammingLanguage)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if(bookProgrammingLanguage.getId() <= 0){
            throw new FieldRequiredException("book_prog_lang_id");
        }
        return ExecuteInTransaction.execute(cnx->{
            dao.delete(bookProgrammingLanguage, cnx);
            TransactionsResultsMessages.deleteSuccess(bookProgrammingLanguage);
            return bookProgrammingLanguage;
        }, dbConnection);
    }

}
