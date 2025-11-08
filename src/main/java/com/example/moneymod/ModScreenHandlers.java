package com.example.moneymod;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public final class ModScreenHandlers {
    public static final ScreenHandlerType<TradingScreenHandler> TRADING =
            ScreenHandlerRegistry.registerSimple(MoneyMod.id("trading"), TradingScreenHandler::new);

    private ModScreenHandlers() {
    }

    public static void register() {
        // Trigger static initialiser
    }
}
