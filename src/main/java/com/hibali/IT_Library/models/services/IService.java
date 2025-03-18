package com.hibali.IT_Library.models.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;

public interface IService<Model, IDType> {

    Optional<Model> add(Model model) throws FieldRequiredException, FieldUniqueException, BuisnessRulesException, SQLException;

    ArrayList<Model> getAll() throws SQLException;

    Optional<Model> getById(IDType id) throws SQLException;

    Optional<Model> update(Model model) throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException;

    Optional<Model> delete(Model model) throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException;
}
