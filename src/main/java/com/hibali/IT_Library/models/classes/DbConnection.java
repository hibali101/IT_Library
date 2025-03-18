package com.hibali.IT_Library.models.classes;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static DbConnection instance;
    private String connextionString;
    private String username;
    private String password;

    private DbConnection() {
        //path to the enviroment .env file
        Path path = Path.of("./").resolve(".env").normalize().toAbsolutePath();
        try (FileReader fr = new FileReader(path.toString())) {
            Properties prop = new Properties();
            prop.load(fr);
            this.connextionString = prop.getProperty("CON_URL");
            this.username = prop.getProperty("CON_USERNAME");
            this.password = prop.getProperty("CON_PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to load env file");
        }
    }

    public Connection create() throws SQLException {
        try{
            return DriverManager.getConnection(this.connextionString, this.username, this.password);
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    public static DbConnection getDbConnection(){
        if(instance == null){
            instance = new DbConnection();
        }
        return instance;
    }
}
