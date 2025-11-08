package com.example.moneymod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class PriceTable {
    private static final Map<Identifier, Integer> PRICES;

    static {
        Map<Identifier, Integer> prices = new HashMap<>();
        prices.put(Identifier.of("minecraft", "diamond"), 250);
        prices.put(Identifier.of("minecraft", "iron_ingot"), 40);
        prices.put(Identifier.of("minecraft", "gold_ingot"), 80);
        PRICES = Collections.unmodifiableMap(prices);
    }

    private PriceTable() {
    }

    public static long calculateStackValue(ItemStack stack) {
        Identifier id = Registries.ITEM.getId(stack.getItem());
        int price = PRICES.getOrDefault(id, 0);
        return (long) price * stack.getCount();
    }

    public static int getUnitPrice(Item item) {
        Identifier id = Registries.ITEM.getId(item);
        return PRICES.getOrDefault(id, 0);
    }
}
