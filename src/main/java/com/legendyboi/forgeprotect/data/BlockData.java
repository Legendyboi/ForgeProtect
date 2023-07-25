package com.legendyboi.forgeprotect.data;

public class BlockData {

    private String blockID;

    private String userName;

    private byte[] blockState;
    private int X;
    private int Y;
    private int Z;
    private String worldName;
    private long time;

    public BlockData(String blockID, byte[] blockState, int x, int y, int z, String worldName, String userName, long time) {
        this.userName = userName;
        this.blockID = blockID;
        this.blockState = blockState;
        X = x;
        Y = y;
        Z = z;
        this.worldName = worldName;
        this.time = time;

    }
    public String getUserName() {
        return userName;
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
