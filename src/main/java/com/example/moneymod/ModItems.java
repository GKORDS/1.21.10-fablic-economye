package com.example.moneymod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class ModItems {
    public static final Item TRADING_STICK = register("trading_stick", new TradingStickItem(new Item.Settings().maxCount(1)));
    public static final Item COIN = register("coin", new CoinItem(new Item.Settings()));

    private ModItems() {
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(TRADING_STICK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(COIN));
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, MoneyMod.id(name), item);
    }
}
