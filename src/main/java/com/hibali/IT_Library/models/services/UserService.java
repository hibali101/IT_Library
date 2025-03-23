package com.hibali.IT_Library.models.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.hibali.IT_Library.customExceptions.BuisnessRulesException;
import com.hibali.IT_Library.customExceptions.FieldRequiredException;
import com.hibali.IT_Library.customExceptions.FieldUniqueException;
import com.hibali.IT_Library.customExceptions.HashingException;
import com.hibali.IT_Library.models.Dao.UserDao;
import com.hibali.IT_Library.models.classes.DbConnection;
import com.hibali.IT_Library.models.classes.User;
import com.hibali.IT_Library.utilities.ExecuteInTransaction;
import com.hibali.IT_Library.utilities.HashPassword;
import com.hibali.IT_Library.utilities.TransactionsResultsMessages;

public class UserService implements IService<User, Integer> {
    private final DbConnection dbConnection;
    private final UserDao dao;

    public UserService(DbConnection dbConnection, UserDao dao) {
        this.dbConnection = dbConnection;
        this.dao = dao;
    }

    @Override
    public Optional<User> add(User user)
            throws FieldRequiredException, FieldUniqueException, BuisnessRulesException, SQLException,
            HashingException {
        if (user.getName() == null || user.getPassword() == null) {
            throw new FieldRequiredException(new String[] { "user_name", "user_password" });
        }
        // hashing password
        String hashedPassword = HashPassword.hash(user.getPassword());
        if (hashedPassword == null) {
            throw new HashingException("user_password");
        }
        // storing original password before hashing to restore it after insertion (for returned string purposes)
        String noHashPassword = user.getPassword();
        user.setPassword(hashedPassword);
        return ExecuteInTransaction.execute(cnx -> {
            dao.insert(user, cnx);
            user.setPassword(noHashPassword);
            TransactionsResultsMessages.insertSuccess(user);
            return user;
        }, dbConnection);
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findAll(cnx);
        }
    }

    @Override
    public Optional<User> getById(Integer id) throws SQLException {
        try (Connection cnx = dbConnection.create()) {
            return dao.findById(id, cnx);
        }
    }

    @Override
    public Optional<User> delete(User user)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user)
            throws FieldUniqueException, FieldRequiredException, BuisnessRulesException, SQLException {
        if(user.getId() <= 0){
            throw new FieldRequiredException("user_id");
        }
        String noHashPassword = user.getPassword();
        if(user.getPassword() != null){
            String hashedPassword = HashPassword.hash(user.getPassword());
            user.setPassword(hashedPassword);
        }
        return ExecuteInTransaction.execute(cnx -> {
            dao.update(user, cnx);
            user.setPassword(noHashPassword);
            TransactionsResultsMessages.updateSuccess(user);
            return user;
        }, dbConnection);
    }

    

}
