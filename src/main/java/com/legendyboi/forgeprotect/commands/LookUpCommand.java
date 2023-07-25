package com.legendyboi.forgeprotect.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.*;

import com.legendyboi.forgeprotect.data.PlayerActionData;
import com.legendyboi.forgeprotect.data.BlockData;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class LookUpCommand {

    public LookUpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("lookup")
                .then(Commands.argument("action", StringArgumentType.word()).executes(this::OnCommand)));

    }

    private int OnCommand(CommandContext<CommandSourceStack> command) {

        String action = StringArgumentType.getString(command, "action");

        if (action != null) {

            ServerPlayer player = command.getSource().getPlayer();
            assert player != null;
            UUID playerUUID = player.getUUID();
            String playerUserName = player.getName().getString();

            PlayerActionData playerActionData = new PlayerActionData(playerUUID, playerUserName);

            if (playerActionData != null) {
                List<BlockData> actionBlocks;
                if ("broken".equalsIgnoreCase(action)) {
                    actionBlocks = playerActionData.getBrokenBlocks();
                } else if ("placed".equalsIgnoreCase(action)) {
                    actionBlocks = playerActionData.getPlacedBlocks();
                } else {
                    player.sendSystemMessage(Component.literal(ChatFormatting.RED + "Invalid 'action' parameter. Use 'broken' or 'placed'."));
                    return 0;
                }


                // Display the results to the player
                if (actionBlocks.isEmpty()) {
                    player.sendSystemMessage(Component.literal("No data found for the action: " + action));
                } else {
                    for (BlockData blockData : actionBlocks) {
                        player.sendSystemMessage(Component.literal(String.format("Username %s,Block: %s, State: %s, Location: %d, %d, %d, World: %s, Time: %d",
                                blockData.getUserName(),
                                blockData.getBlockID(),
                                Arrays.toString(blockData.getBlockState()),
                                blockData.getX(),
                                blockData.getY(),
                                blockData.getZ(),
                                blockData.getWorldName(),
                                blockData.getTime())
                        ));
                    }
                }

            } else {
                player.sendSystemMessage(Component.literal("No data found for the player."));
            }

                return 0;
            }

        return 1;
    }
}
