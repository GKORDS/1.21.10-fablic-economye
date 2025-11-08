package com.example.moneymod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TradingStickItem extends Item {
    private static final Text TITLE = Text.translatable("screen.moneymod.trading");

    public TradingStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new TradingScreenHandler(syncId, inventory), TITLE));
            MoneyManager.syncBalance(serverPlayer);
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }
}
