package com.legendyboi.forgeprotect.data;

import com.legendyboi.forgeprotect.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerActionData {

    private UUID uuid;
    private String userName;
    private List<BlockData> brokenBlocks;
    private List<BlockData> placedBlocks;


    public PlayerActionData(UUID uuid, String userName) {
        this.uuid = uuid;
        this.userName = userName;
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
        try (Connection connection = Main.mod.db.getConnection();) {
            insertBrokenBlocks(connection);
            insertPlacedBlocks(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertBrokenBlocks(Connection connection) throws SQLException {
        String sql = "INSERT INTO blockBrokenData (username, blockID, blockState, X, Y, Z, worldName, time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BlockData blockData : brokenBlocks) {
                statement.setString(1, blockData.getUserName());
                statement.setString(2, blockData.getBlockID());
                statement.setBytes(3, blockData.getBlockState());
                statement.setInt(4, blockData.getX());
                statement.setInt(5, blockData.getY());
                statement.setInt(6, blockData.getZ());
                statement.setString(7, blockData.getWorldName());
                statement.setLong(8, blockData.getTime());
                statement.executeUpdate();
            }
        }
    }

    private void insertPlacedBlocks(Connection connection) throws SQLException {
        String sql = "INSERT INTO blockPlacedData (userName, blockID, blockState, X, Y, Z, worldName, time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BlockData blockData : placedBlocks) {
                statement.setString(1, blockData.getUserName());
                statement.setString(2, blockData.getBlockID());
                statement.setBytes(3, blockData.getBlockState());
                statement.setInt(4, blockData.getX());
                statement.setInt(5, blockData.getY());
                statement.setInt(6, blockData.getZ());
                statement.setString(7, blockData.getWorldName());
                statement.setLong(8, blockData.getTime());
                statement.executeUpdate();
            }
        }
    }
}
