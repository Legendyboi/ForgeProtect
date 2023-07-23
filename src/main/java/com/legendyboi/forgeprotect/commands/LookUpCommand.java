package com.legendyboi.forgeprotect.commands;

import com.legendyboi.forgeprotect.PlayerActionData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import com.legendyboi.forgeprotect.BlockData;

import net.minecraft.network.chat.*;

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
            UUID playerUUID = player.getUUID(); // Get the UUID of the player executing the command

            // Query the database for the player's action data based on the 'action' parameter
            PlayerActionData playerActionData = new PlayerActionData(playerUUID);

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


                return 0;
            }

        }
        return 0;
    }
}
