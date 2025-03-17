package com.hibali.IT_Library.functionalInterfaces;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionFunction <T> {
    abstract T apply(Connection cnx) throws SQLException;
}
