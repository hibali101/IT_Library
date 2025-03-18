package com.hibali.IT_Library.utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.functionalInterfaces.TransactionFunction;
import com.hibali.IT_Library.models.classes.DbConnection;

public class ExecuteInTransaction {
    public static <T> Optional<T> execute(TransactionFunction<T> transactionFunction, DbConnection dbConnection)
            throws SQLException, FieldRequiredException, FieldUniqueException, BuisnessRulesException {
        try (Connection cnx = dbConnection.create()) {
            cnx.setAutoCommit(false);
            try {
                T result = transactionFunction.apply(cnx);
                cnx.commit();
                return Optional.ofNullable(result);
            } catch (SQLException e) {
                cnx.rollback();
                throw e;
            }
        }
    }
}
