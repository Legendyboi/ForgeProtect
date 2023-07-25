package com.legendyboi.forgeprotect.databases;

import com.legendyboi.forgeprotect.Main;
import com.legendyboi.forgeprotect.data.BlockData;
import com.legendyboi.forgeprotect.data.PlayerActionData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseHandler {

    private static final String DATABASE_URL = String.valueOf(Main.mod.db.getConnection());;

    public PlayerActionData getPlayerActionData(UUID playerUUID, String playerUsername) {
        PlayerActionData playerActionData = null;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            playerActionData = getPlayerActionDataFromDatabase(connection, playerUUID, playerUsername);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerActionData;
    }


    private PlayerActionData getPlayerActionDataFromDatabase(Connection connection, UUID playerUUID, String playerUsername) throws SQLException {
        PlayerActionData playerActionData = null;

        String brokenQuery = "SELECT * FROM blockBrokenData WHERE userName = ?;";
        String placedQuery = "SELECT * FROM blockPlacedData WHERE userName = ?;";

        try (PreparedStatement brokenStatement = connection.prepareStatement(brokenQuery);
             PreparedStatement placedStatement = connection.prepareStatement(placedQuery)) {

            brokenStatement.setString(1, playerUUID.toString());
            placedStatement.setString(1, playerUUID.toString());

            try (ResultSet brokenResultSet = brokenStatement.executeQuery();
                 ResultSet placedResultSet = placedStatement.executeQuery()) {

                playerActionData = new PlayerActionData(playerUUID, playerUsername);

                while (brokenResultSet.next()) {
                    String blockID = brokenResultSet.getString("blockID");
                    String playerUserName = brokenResultSet.getString("userName");
                    byte[] blockState = brokenResultSet.getBytes("blockState");
                    int X = brokenResultSet.getInt("X");
                    int Y = brokenResultSet.getInt("Y");
                    int Z = brokenResultSet.getInt("Z");
                    String worldName = brokenResultSet.getString("worldName");
                    long time = brokenResultSet.getLong("time");

                    BlockData blockData = new BlockData(blockID, blockState, X, Y, Z, worldName, playerUserName, time);
                    playerActionData.addBrokenBlock(blockData);
                }

                while (placedResultSet.next()) {
                    String blockID = placedResultSet.getString("blockID");
                    String playerUserName = brokenResultSet.getString("userName");
                    byte[] blockState = placedResultSet.getBytes("blockState");
                    int X = placedResultSet.getInt("X");
                    int Y = placedResultSet.getInt("Y");
                    int Z = placedResultSet.getInt("Z");
                    String worldName = placedResultSet.getString("worldName");

                    long time = placedResultSet.getLong("time");

                    BlockData blockData = new BlockData(blockID, blockState, X, Y, Z, worldName, playerUserName, time);
                    playerActionData.addPlacedBlock(blockData);
                }
            }
        }

        return playerActionData;
    }
}
