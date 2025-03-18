package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.ProgrammingLanguageDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class ProgrammingLanguageService implements IService<ProgrammingLanguage, Integer> {

    private final DbConnection dbConnection;
    private final ProgrammingLanguageDao dao;

    public ProgrammingLanguageService(DbConnection dbConnection, ProgrammingLanguageDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    // adding new programming language
    @Override
    public Optional<ProgrammingLanguage> add(ProgrammingLanguage progsLanguage)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (progsLanguage.getName() == null) {
            throw new FieldRequiredException("progsLanguage");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.insert(progsLanguage, cnx);
            TransactionsResultsMessages.insertSuccess(progsLanguage);
            return progsLanguage;
        }, dbConnection);
    }

    /// get all programming language
    public ArrayList<ProgrammingLanguage> getAll() throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findAll(cnx);
        }
    }

    /// get by id a programming language

    public Optional<ProgrammingLanguage> getById(Integer id) throws SQLException {
        try (Connection cnx = this.dbConnection.create()) {
            return dao.findById(id, cnx);
        }
    }

    /// upate a programming language
    public Optional<ProgrammingLanguage> update(ProgrammingLanguage programmingLanguage)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (programmingLanguage.getId() <= 0) {
            throw new FieldRequiredException("prog_lang_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.update(programmingLanguage, cnx);
            TransactionsResultsMessages.updateSuccess(programmingLanguage);
            return programmingLanguage;
        }, dbConnection);
    }

    // delete a programming language
    public Optional<ProgrammingLanguage> delete(ProgrammingLanguage programmingLanguage)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if (programmingLanguage.getId() <= 0) {
            throw new FieldRequiredException("prog_lang_id");
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.delete(programmingLanguage, cnx);
            TransactionsResultsMessages.deleteSuccess(programmingLanguage);
            return programmingLanguage;
        }, dbConnection);
    }
}
