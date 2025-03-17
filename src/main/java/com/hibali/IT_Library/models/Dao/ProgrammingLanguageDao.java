package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.models.classes.ProgrammingLanguage;
import com.hibali.IT_Library.utilities.ResultSetMaper;

public class ProgrammingLanguageDao implements IDao<ProgrammingLanguage> {

    @Override
    public void insert(ProgrammingLanguage proglanguage, Connection cnx) throws SQLException {
        String query = "insert into prog_langs (prog_lang_name) values(?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, proglanguage.getName());
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<ArrayList<ProgrammingLanguage>> findAll(Connection cnx) throws SQLException {
        ArrayList<ProgrammingLanguage> programmingLanguages = new ArrayList<>();
        String query = "select * from prog_langs where prog_lang_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query); ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                programmingLanguages.add(ResultSetMaper.mapToModel(result, ProgrammingLanguage.class));
            }
        }
        return Optional.ofNullable(programmingLanguages);
    }

    @Override
    public Optional<ProgrammingLanguage> findById(int id, Connection cnx) throws SQLException {
        String query = "select * from prog_langs where prog_lang_id=? and prog_lang_deleted = 0";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    return Optional.ofNullable(ResultSetMaper.mapToModel(result, ProgrammingLanguage.class));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(ProgrammingLanguage progLang, Connection cnx) throws SQLException {
        String query = "update prog_langs set prog_lang_name = ?, updated_at = GETDATE() where prog_lang_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, progLang.getName());
            ps.setInt(2, progLang.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(ProgrammingLanguage progLang, Connection cnx) throws SQLException {
        String query = "update prog_langs set prog_lang_deleted = 1 where prog_lang_id = ?";
        try (PreparedStatement ps = cnx
                .prepareStatement(query)) {
            ps.setInt(1, progLang.getId());
            ps.executeUpdate();
        }
    }
}
