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
            if (!folder.mkdirs()) { LOGGER.error("Could not create ForgeProtect directory!"); }
        }

        File dataFolder = new File(folder.getPath() + "/data.db");

        if (!dataFolder.exists()) {
            try { if (!dataFolder.createNewFile()) { LOGGER.error("Could not create data.db file!"); }
            } catch (IOException e) { e.printStackTrace(); return false; }
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

            createBlockBrokenDataTable();
            createBlockPlacedDataTable();

            try (Statement statement = connection.createStatement()) {
                LOGGER.info("SQLite Tables Loaded!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createBlockBrokenDataTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS blockBrokenData (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userName TEXT," +
                "blockID TEXT, " +
                "blockState BLOB, " +
                "X INTEGER, " +
                "Y INTEGER, " +
                "Z INTEGER, " +
                "worldName TEXT, " +
                "time INTEGER);";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    private void createBlockPlacedDataTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS blockPlacedData (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userName TEXT," +
                "blockID TEXT, " +
                "blockState BLOB, " +
                "X INTEGER, " +
                "Y INTEGER, " +
                "Z INTEGER, " +
                "userName TEXT," +
                "worldName TEXT, " +
                "time INTEGER);";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    public Connection getConnection() {
        return connection;
    }

}

