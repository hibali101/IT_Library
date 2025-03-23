package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.customExceptions.HashingException;
import com.hibali.IT_Library.models.Dao.LogDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.Log;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class LogService implements IService<Log, Integer> {
    private DbConnection dbConnection;
    private LogDao dao;

    public LogService(DbConnection dbConnection, LogDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    @Override
    public Optional<Log> add(Log log) throws FieldRequiredException, FieldUniqueException, BuisnessRulesException,
            SQLException, HashingException {
        if(log.getUserId() <= 0 || log.getDate() == null){
            throw new FieldRequiredException(new String[]{"user_id","log_date"});
        }
        return ExecuteInTransaction.execute(cnx->{
            dao.insert(log, cnx);
            TransactionsResultsMessages.insertSuccess(log);
            return log;
        }, dbConnection);
    }

    @Override
    public Optional<Log> delete(Log log)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public ArrayList<Log> getAll() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Log> getById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<Log> update(Log log)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    //More than mere crud
    public ArrayList<Log> getByUserId(int userId) throws SQLException{
        try(Connection cnx = dbConnection.create()){
            return dao.findByUserId(userId, cnx);
        }
    }
    
}
