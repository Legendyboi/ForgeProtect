package com.legendyboi.forgeprotect;

public class BlockData {

    private String blockID;
    private byte[] blockState;
    private int X;
    private int Y;
    private int Z;
    private String worldName;
    private long time;

    public BlockData(String blockID, byte[] blockState, int x, int y, int z, String worldName, long time) {
        this.blockID = blockID;
        this.blockState = blockState;
        X = x;
        Y = y;
        Z = z;
        this.worldName = worldName;
        this.time = time;
    }

    public String getBlockID() {
        return blockID;
    }

    public byte[] getBlockState() {
        return blockState;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getZ() {
        return Z;
    }

    public String getWorldName() {
        return worldName;
    }

    public long getTime() {
        return time;
    }
}
