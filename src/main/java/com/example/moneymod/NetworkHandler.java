package com.example.moneymod;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public final class NetworkHandler {
    public static final Identifier SELL_INVENTORY = MoneyMod.id("sell_inventory");
    public static final Identifier SYNC_BALANCE = MoneyMod.id("sync_balance");

    private NetworkHandler() {
    }

    public static void registerServerListeners() {
        ServerPlayNetworking.registerGlobalReceiver(SELL_INVENTORY, (server, player, handler, buf, responseSender) ->
                server.execute(() -> MoneyManager.handleSellInventory(player)));
    }

    public static void registerClientListeners() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_BALANCE, (client, handler, buf, responseSender) -> {
            long balance = buf.readLong();
            client.execute(() -> {
                ClientBalanceStorage.setBalance(balance);
                if (client.currentScreen instanceof TradingScreen tradingScreen) {
                    tradingScreen.onBalanceUpdated();
                }
            });
        });
    }

    public static void syncBalance(ServerPlayerEntity player, long balance) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeLong(balance);
        ServerPlayNetworking.send(player, SYNC_BALANCE, buf);
    }
}
