package com.mimimiii.battleTools.database;

import com.mimimiii.battleTools.user.TokenRelated;
import com.mimimiii.battleTools.user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseService {
    private Connection connection;

    // mtcg_mimimiii
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "swe1user";
    private static final String PASSWORD = "swe1pw";

    private static DatabaseService instance;

    private DatabaseService() {
    }

    //    Method to obtain an object.
    public static DatabaseService getInstance() {
        if (DatabaseService.instance == null) {
            DatabaseService.instance = new DatabaseService();
        }
        return DatabaseService.instance;
    }


    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void createUser(User user) throws SQLException {
        PreparedStatement ps;
        String stackName = TablesPrepared.getUserStackTableName(user.getUsername());
        String deckName = TablesPrepared.getDeckTableName(user.getUsername());
        ps = connection.prepareStatement("INSERT INTO " + TablesPrepared.getUserListTableName() + " (username, password, collection, token) VALUES (?,?,?,?)");
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, stackName);
        ps.setString(4, TokenRelated.getUserToken(user.getUsername()));
        ps.executeUpdate();
        createUserStackTable(stackName, "-s");
        createUserStackTable(deckName, "-d");
    }

    private void createUserStackTable(String stackName, String s) throws SQLException {


        PreparedStatement ps = connection.prepareStatement("");
        switch (s) {
            case "-s":
                ps = connection.prepareStatement("CREATE TABLE " + stackName + " (uid varchar(255), amount int default 1)");
                break;
            case "-d":
                ps = connection.prepareStatement("CREATE TABLE " + stackName + " (uid varchar(255));");
        }
        ps.executeUpdate();
        ps = connection.prepareStatement("CREATE UNIQUE INDEX " + stackName + "_uid_uindex ON " + stackName + " (uid);");
        ps.executeUpdate();


    }


}



