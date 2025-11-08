package com.example.moneymod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class TradingScreenHandler extends ScreenHandler {
    private final PlayerEntity player;

    public TradingScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.TRADING, syncId);
        this.player = playerInventory.player;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
