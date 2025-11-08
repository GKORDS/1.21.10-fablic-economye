package com.example.moneymod;

import net.fabricmc.api.ClientModInitializer;

public class MoneyModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MoneyMod.LOGGER.info("Initializing client for {}", MoneyMod.MOD_ID);
        ModScreens.registerClient();
        NetworkHandler.registerClientListeners();
    }
}
