package com.legendyboi.forgeprotect;

import com.legendyboi.forgeprotect.data.BlockData;
import com.legendyboi.forgeprotect.data.PlayerActionData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.server.level.ServerPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Listeners {

    private static final Map<UUID, PlayerActionData> playerActionDataMap = new HashMap<>();

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        assert player != null;
        UUID playerUuid = player.getUUID();
        String playerUserName = String.valueOf(player.getName());

        // Retrieve or create PlayerActionData object for the player
        PlayerActionData playerActionData = playerActionDataMap.computeIfAbsent(playerUuid, uuid -> new PlayerActionData(playerUuid, playerUserName));

        // Get the block details
        Block block = event.getState().getBlock();
        BlockPos pos = event.getPos();
        String blockID = block.getName().getString();
        try {

            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            String worldName = player.getLevel().dimension().location().toString();
            long time = System.currentTimeMillis();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            NbtUtils.writeBlockState(event.getState()).write(dos);
            byte[] blockState = bos.toByteArray();
            // Create a new BlockData object
            BlockData blockData = new BlockData(blockID, blockState, x, y, z, worldName, playerUserName, time);

            // Add the BlockData to the player's broken blocks list
            playerActionData.addBrokenBlock(blockData);
        } catch (Exception e) {

        }
    }


    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {

        ServerPlayer player = (ServerPlayer) event.getEntity();
        assert player != null;
        UUID playerUuid = player.getUUID();
        String playerUserName = String.valueOf(player.getName());

        PlayerActionData playerActionData = playerActionDataMap.computeIfAbsent(playerUuid, uuid -> new PlayerActionData(playerUuid, playerUserName));

        // Get the block details
        Block block = event.getState().getBlock();
        BlockPos pos = event.getPos();
        String blockID = block.getName().getString();
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            NbtUtils.writeBlockState(event.getState()).write(dos);
            byte[] blockState = bos.toByteArray();

            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            String worldName = player.getLevel().dimension().location().toString();
            long time = System.currentTimeMillis();
            BlockData blockData = new BlockData(blockID, blockState, x, y, z, worldName, playerUserName, time);
            // Add the BlockData to the player's broken blocks list
            playerActionData.addPlacedBlock(blockData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static PlayerActionData getPlayerActionData(UUID playerUuid) {
        return playerActionDataMap.get(playerUuid);
    }

//    @SubscribeEvent
//    public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
//        try {
//            // Get the Minecraft game directory
//            Path minecraftDir = Paths.get(".");
//            String logFilePath = minecraftDir.resolve("liquid_place.log").toString();
//
//            // Get fluid details
//            FluidState fluidState = event.getNewState().getFluidState();
//            String fluidName = fluidState.getFluidType().toString();
//
//            // Get block details
//            Block block = event.getNewState().getBlock();
//            BlockPos pos = event.getPos();
//            String blockName = block.getName().getString();
//            String coords = String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());
//
//            // Open the log file in append mode
//            BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));
//
//            // Write block and fluid details to the log file
//            writer.write("Liquid placed: {" + fluidName + " on " + blockName + ", " + coords + "}");
//            writer.newLine();
//
//            // Close the file
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private static void logBlockBroken(String playerName, String blockName, String coords, String timeFormat) {
//        try {
//            // Get the SQLite database connection
//            // ForgeProtect.mod.db.getConnection();
//            Connection connection = Main.mod.db.getConnection();
//            if (connection == null) {
//                return;
//            }
//
//            // Prepare the SQL statement
//            String sql = "INSERT INTO block_data (blockName, userName, time) VALUES (?, ?, ?);";
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                // Set the parameter values
//                statement.setString(1, blockName);
//                statement.setString(2, playerName);
//                statement.setString(3, timeFormat);
//
//                // Execute the SQL statement
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
}