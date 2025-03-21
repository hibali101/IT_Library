package com.hibali.IT_Library.models.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface IDao<Model> {
    void insert(Model model, Connection cnx) throws SQLException;

    ArrayList<Model> findAll(Connection cnx) throws SQLException;

    Optional<Model> findById(int id, Connection cnx) throws SQLException;

    void update(Model model, Connection cnx) throws  SQLException;

    void delete(Model model, Connection cnx) throws SQLException;
}
