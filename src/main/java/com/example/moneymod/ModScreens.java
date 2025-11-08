package com.example.moneymod;

import net.minecraft.client.gui.screen.ingame.HandledScreens;

public final class ModScreens {
    private ModScreens() {
    }

    public static void registerClient() {
        HandledScreens.register(ModScreenHandlers.TRADING, TradingScreen::new);
    }
}
