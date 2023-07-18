package com.legendyboi.forgeprotect;

public class LookUpData {

    private final String blockName;
    private final String userName;
    private final int blockQuantity;
    private final String time;
    private String blockState;

    public LookUpData(String blockName, String userName, int blockQuantity, String time){

        this.blockName = blockName;
        this.userName = userName;
        this.blockQuantity = blockQuantity;
        this.time = time;
        this.blockState = blockState;

    }

    public String getUserName(){

        return userName;

    }

    public String getBlockName() {
        return blockName;
    }

    public int getBlockQuantity() {
        return blockQuantity;
    }

    public String getBlockState() {
        return blockState;
    }

    public String getTime() {
        return time;
    }
}
