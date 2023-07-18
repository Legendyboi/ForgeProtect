package com.legendyboi.forgeprotect.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class LookUpCommand {

    public LookUpCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("lookup")
                .then(Commands.argument("action", StringArgumentType.word()).executes(this::OnCommand)));

    }

    private int OnCommand(CommandContext<CommandSourceStack> command){

        String action = StringArgumentType.getString(command, "action");

//        if (action != null)
//
        return 0;
    }

}
