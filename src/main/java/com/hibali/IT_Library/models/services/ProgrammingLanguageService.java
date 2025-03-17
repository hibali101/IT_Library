package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.Dao.ProgrammingLanguageDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class ProgrammingLanguageService implements IService<ProgrammingLanguage, Integer> {

    private final DbConnection connexion;
    private final ProgrammingLanguageDao dao;

    public ProgrammingLanguageService(DbConnection cnx, ProgrammingLanguageDao dao) {
        this.connexion = cnx;
        this.dao = dao;
    }

    // adding new programming language
    @Override
    public ProgrammingLanguage add(ProgrammingLanguage progsLanguage)
            throws FieldRequiredException, FieldUniqueException {
        if (progsLanguage.getName() == null) {
            throw new FieldRequiredException("progsLanguage");
        } else {
            try (Connection cnx = connexion.create()) {
                cnx.setAutoCommit(false);
                try {
                    dao.insert(progsLanguage, cnx);
                    cnx.commit();
                    TransactionsResultsMessages.insertSuccess(progsLanguage);
                    return progsLanguage;
                } catch (SQLException e) {
                    cnx.rollback();
                    if (e.getMessage().contains("UNIQUE")) {
                        throw new FieldUniqueException("prog_lang_name");
                    }
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    /// get all programming language
    public ArrayList<ProgrammingLanguage> getAll() {
        try (Connection cnx = connexion.create()) {
            dao.findAll(cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /// get by id a programming language

    public ProgrammingLanguage getById(Integer id) {
        ProgrammingLanguage programmingLanguage = null;
        try (Connection cnx = this.connexion.create()) {
            dao.findById(id, cnx);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programmingLanguage;
    }

    /// upate a programming language
    public ProgrammingLanguage update(ProgrammingLanguage programmingLanguage)
            throws FieldUniqueException, FieldRequiredException {
        if (programmingLanguage.getId() <= 0) {
            throw new FieldRequiredException("prog_lang_id");
        }
        try (Connection cnx = connexion.create()) {
            cnx.setAutoCommit(false);
            try {
                dao.update(programmingLanguage, cnx);
                cnx.commit();
                TransactionsResultsMessages.updateSuccess(programmingLanguage);
                return programmingLanguage;
            } catch (SQLException e) {
                cnx.rollback();
                if (e.getMessage().contains("UNIQUE")) {
                    throw new FieldUniqueException("prog_lang_name");
                }
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // delete a programming language
    public ProgrammingLanguage delete(ProgrammingLanguage programmingLanguage) throws FieldRequiredException {
        if (programmingLanguage.getId() <= 0) {
            throw new FieldRequiredException("prog_lang_id");
        }
        try (Connection cnx = connexion.create()) {
            cnx.setAutoCommit(false);
            try {
                dao.delete(programmingLanguage, cnx);
                cnx.commit();
                TransactionsResultsMessages.deleteSuccess(programmingLanguage);
                return programmingLanguage;
            } catch (SQLException e) {
                cnx.rollback();
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
