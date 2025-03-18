package com.hibali.IT_Library.functionalInterfaces;

import java.sql.Connection;
import java.sql.SQLException;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;

@FunctionalInterface
public interface TransactionFunction <T> {
    abstract T apply(Connection cnx) throws SQLException, FieldRequiredException, FieldUniqueException, BuisnessRulesException;
}
