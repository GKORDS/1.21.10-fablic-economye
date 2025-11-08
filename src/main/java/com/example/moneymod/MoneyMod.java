package com.example.moneymod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyMod implements ModInitializer {
    public static final String MOD_ID = "moneymod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.register();
        ModScreenHandlers.register();
        NetworkHandler.registerServerListeners();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> MoneyManager.syncBalance(handler.player));
        ServerPlayConnectionEvents.RESPAWN.register((handler, sender, server, oldPlayer, newPlayer, alive) -> MoneyManager.syncBalance(newPlayer));
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> MoneyManager.syncBalance(newPlayer));
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
