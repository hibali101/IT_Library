package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class ProgrammingLanguageService implements IService<ProgrammingLanguage, Integer> {

    private final DbConnection connexion;

    public ProgrammingLanguageService(DbConnection cnx) {
        this.connexion = cnx;
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
                String query = "insert into prog_langs (prog_lang_name) values(?)";
                try (PreparedStatement ps = cnx.prepareStatement(query)) {
                    ps.setString(1, progsLanguage.getName());
                    ps.executeUpdate();
                    cnx.commit();
                    return progsLanguage;
                } catch (SQLException e) {
                    cnx.rollback();
                    if (e.getMessage().contains("UNIQUE")) {
                        throw new FieldUniqueException("progsLanguage");
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
        ArrayList<ProgrammingLanguage> programmingLanguage = new ArrayList<>();
        String query = "select * from prog_langs where prog_lang_deleted = 0";
        try (Connection cnx = connexion.create(); PreparedStatement ps = cnx.prepareStatement(query)) {
            try(ResultSet result = ps.executeQuery()){
                while (result.next()) {
                    programmingLanguage.add(ResultSetMaper.mapToModel(result, ProgrammingLanguage.class));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return programmingLanguage;
    }

    /// get by id a programming language

    public ProgrammingLanguage getById(Integer id) {
        ProgrammingLanguage programmingLanguage = null;
        try (Connection cnx = this.connexion.create();
                PreparedStatement ps = cnx
                        .prepareStatement("select * from prog_langs where prog_lang_id=? and prog_lang_deleted = 0")) {
            ps.setInt(1, id);
            try(ResultSet result = ps.executeQuery()){
                while (result.next()) {
                    programmingLanguage = ResultSetMaper.mapToModel(result, ProgrammingLanguage.class);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
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
            String query = "update prog_langs set prog_lang_name = ?, updated_at = GETDATE() where prog_lang_id = ?";
            try (PreparedStatement ps = cnx.prepareStatement(query)) {
                ps.setString(1, programmingLanguage.getName());
                ps.setInt(2, programmingLanguage.getId());
                ps.executeUpdate();
                cnx.commit();
                System.out.println(programmingLanguage.toString() + " updated successefully");
                return programmingLanguage;
            } catch (SQLException e) {
                cnx.rollback();
                if (e.getMessage().contains("UNIQUE")) {
                    throw new FieldUniqueException("prog_lang_name");
                }
                System.out.println(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
            try (PreparedStatement ps = cnx
                    .prepareStatement("update prog_langs set prog_lang_deleted = 1 where prog_lang_id = ?")) {
                ps.setInt(1, programmingLanguage.getId());
                ps.executeUpdate();
                cnx.commit();
                System.out.println(programmingLanguage.getName() + " deleted successfully");
                return programmingLanguage;
            } catch (SQLException ex) {
                cnx.rollback();
                System.out.println(ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
