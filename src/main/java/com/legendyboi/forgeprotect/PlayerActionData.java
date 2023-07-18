package com.legendyboi.forgeprotect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

}
