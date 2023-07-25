package com.legendyboi.forgeprotect;

import com.legendyboi.forgeprotect.commands.LookUpCommand;
import com.legendyboi.forgeprotect.databases.IDatabase;
import com.legendyboi.forgeprotect.databases.SQLite;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.command.ConfigCommand;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "forgeprotect";
    public static final Logger LOGGER = LogUtils.getLogger();
    public IDatabase db;

    public static Main mod;

    private Listeners blockEvent;

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        db = new SQLite();
        db.connect();
        db.init();

        mod = this;

        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event){

        new LookUpCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
//        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }


}
