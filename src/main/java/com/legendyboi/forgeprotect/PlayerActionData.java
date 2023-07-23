package com.legendyboi.forgeprotect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerActionData {

    private UUID uuid;
    private String userName;
    private List<BlockData> brokenBlocks;
    private List<BlockData> placedBlocks;


    public PlayerActionData(UUID uuid) {
        this.uuid = uuid;
        this.brokenBlocks = new ArrayList<>();
        this.placedBlocks = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUserName() {
        return userName;
    }

    public List<BlockData> getBrokenBlocks() {
        return brokenBlocks;
    }

    public List<BlockData> getPlacedBlocks() {
        return placedBlocks;
    }

    public void addBrokenBlock(BlockData blockData) {
        brokenBlocks.add(blockData);
    }

    public void addPlacedBlock(BlockData blockData) {
        placedBlocks.add(blockData);
    }


    public void insertData() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db")) {
            insertBrokenBlocks(connection);
            insertPlacedBlocks(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertBrokenBlocks(Connection connection) throws SQLException {
        String sql = "INSERT INTO blockBrokenData (blockID, blockState, X, Y, Z, worldName, time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BlockData blockData : brokenBlocks) {
                statement.setString(1, blockData.getBlockID());
                statement.setBytes(2, blockData.getBlockState());
                statement.setInt(3, blockData.getX());
                statement.setInt(4, blockData.getY());
                statement.setInt(5, blockData.getZ());
                statement.setString(6, blockData.getWorldName());
                statement.setLong(7, blockData.getTime());
                statement.executeUpdate();
            }
        }
    }

    private void insertPlacedBlocks(Connection connection) throws SQLException {
        String sql = "INSERT INTO blockPlacedData (blockID, blockState, X, Y, Z, worldName, time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BlockData blockData : placedBlocks) {
                statement.setString(1, blockData.getBlockID());
                statement.setBytes(2, blockData.getBlockState());
                statement.setInt(3, blockData.getX());
                statement.setInt(4, blockData.getY());
                statement.setInt(5, blockData.getZ());
                statement.setString(6, blockData.getWorldName());
                statement.setLong(7, blockData.getTime());
                statement.executeUpdate();
            }
        }
    }


}
