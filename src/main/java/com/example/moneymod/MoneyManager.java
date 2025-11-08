package com.example.moneymod;

import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public final class MoneyManager {
    private MoneyManager() {
    }

    public static long getBalance(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return 0L;
        }
        return getState(server).getBalance(player.getUuid());
    }

    public static void setBalance(ServerPlayerEntity player, long amount) {
        long clamped = Math.max(0L, amount);
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        getState(server).setBalance(player.getUuid(), clamped);
        NetworkHandler.syncBalance(player, clamped);
    }

    public static void addBalance(ServerPlayerEntity player, long delta) {
        long newValue = Math.max(0L, getBalance(player) + delta);
        setBalance(player, newValue);
    }

    public static void syncBalance(ServerPlayerEntity player) {
        NetworkHandler.syncBalance(player, getBalance(player));
    }

    public static void handleSellInventory(ServerPlayerEntity player) {
        long totalEarned = 0L;
        // Iterate main inventory slots (0-35) and ignore armor/offhand automatically
        for (int slot = 0; slot < player.getInventory().main.size(); slot++) {
            ItemStack stack = player.getInventory().getStack(slot);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.isOf(ModItems.TRADING_STICK)) {
                continue;
            }
            long value = PriceTable.calculateStackValue(stack);
            if (value <= 0) {
                continue;
            }
            totalEarned += value; // Accumulate sell value for the stack
            player.getInventory().setStack(slot, ItemStack.EMPTY);
        }
        if (totalEarned > 0) {
            player.getInventory().markDirty();
            addBalance(player, totalEarned);
        } else {
            syncBalance(player);
        }
    }

    private static MoneyPersistentState getState(MinecraftServer server) {
        return MoneyPersistentState.get(server);
    }
}
