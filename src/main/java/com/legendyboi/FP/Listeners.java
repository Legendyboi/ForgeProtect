package com.legendyboi.FP;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Listeners {
        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            try {
                // Get the Minecraft game directory
                Path minecraftDir = Paths.get(".");
                String logFilePath = minecraftDir.resolve("block_break.log").toString();

                // Get block details
                Block block = event.getState().getBlock();
                BlockPos pos = event.getPos();
                String blockName = block.getName().getString();
                String coords = String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());

                // Open the log file in append mode
                BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));

                // Write block details to the log file
                writer.write("Block broken: {" + blockName + ", " + coords + "}");
                writer.newLine();

                // Close the file
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @SubscribeEvent
        public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
            try {
                // Get the Minecraft game directory
                Path minecraftDir = Paths.get(".");
                String logFilePath = minecraftDir.resolve("block_place.log").toString();

                // Get block details
                Block block = event.getState().getBlock();
                BlockPos pos = event.getPos();
                String blockName = block.getName().getString();
                String coords = String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());

                // Open the log file in append mode
                BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));

                // Write block details to the log file
                writer.write("Block placed: {" + blockName + ", " + coords + "}");
                writer.newLine();

                // Close the file
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

        @SubscribeEvent
        public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
            // Check if a liquid block has been placed
                try {
                    // Get the Minecraft game directory
                    Path minecraftDir = Paths.get(".");
                    String logFilePath = minecraftDir.resolve("liquid_place.log").toString();

                    // Get block details
                    Block block = event.getNewState().getBlock();
                    BlockPos pos = event.getPos();
                    String blockName = block.getName().getString();
                    String coords = String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());

                    // Open the log file in append mode
                    BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));

                    // Write block details to the log file
                    writer.write("Liquid placed: {" + blockName + ", " + coords + "}");
                    writer.newLine();

                    // Close the file
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

