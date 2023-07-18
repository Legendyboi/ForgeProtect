package com.legendyboi.forgeprotect.databases;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import static com.legendyboi.forgeprotect.Main.LOGGER;

public class SQLite implements IDatabase {
    private String url;
    private Connection connection;

    @Override
    public String name() {
        return "SQLite";
    }

    @Override
    public boolean connect() {
        File folder = new File("config/ForgeProtect");
        LOGGER.info("Initializing SQLite!");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                LOGGER.error("Could not create ForgeProtect directory!");
            }
        }
        File dataFolder = new File(folder.getPath() + "/data.db");
        if (!dataFolder.exists()) {
            try {
                if (!dataFolder.createNewFile()) {
                    LOGGER.error("Could not create data.db file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        this.url = "jdbc:sqlite:" + dataFolder;
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException) {
                LOGGER.error("Could not find SQLite driver!");
            }
            e.printStackTrace();
            return false;
        }
        LOGGER.info("SQLite connection successful!");
        return true;
    }

    @Override
    public void init() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS block_data (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "blockQuantity INTEGER, " +
                    "blockState TEXT, " +
                    "blockName TEXT, " +
                    "userName TEXT, " +
                    "time TEXT);";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                LOGGER.info("SQLite Tables Loaded!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

//    @Override
//    public boolean isPresent(UUID uuid) {
//        String sql = "SELECT uuid FROM login_data WHERE uuid=?;";
//        try {
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, uuid.toString());
//                ResultSet result = statement.executeQuery();
//                return result.next();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean isPresent(String name) {
//        String sql = "SELECT username FROM login_data WHERE username=?;";
//        try {
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, name);
//                ResultSet result = statement.executeQuery();
//                return result.next();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean deleteData(UUID uuid) {
//        String sql = "DELETE FROM login_data WHERE uuid=?;";
//        try {
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, uuid.toString());
//                statement.executeUpdate();
//            }
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean deleteData(String name) {
//        String sql = "DELETE FROM login_data WHERE username=?;";
//        try {
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, name);
//                statement.executeUpdate();
//            }
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}

