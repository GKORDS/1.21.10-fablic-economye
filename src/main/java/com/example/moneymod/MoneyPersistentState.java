package com.example.moneymod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class MoneyPersistentState extends PersistentState {
    private static final String NAME = MoneyMod.MOD_ID + "_money";
    private static final String BALANCES_KEY = "Balances";
    private static final String UUID_KEY = "Uuid";
    private static final String BALANCE_KEY = "Balance";

    private static final PersistentState.Type<MoneyPersistentState> TYPE = new PersistentState.Type<>(
            MoneyPersistentState::new, MoneyPersistentState::readNbt, null);

    private final Map<UUID, Long> balances = new HashMap<>();

    public long getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, 0L);
    }

    public void setBalance(UUID uuid, long value) {
        if (value <= 0) {
            balances.remove(uuid);
        } else {
            balances.put(uuid, value);
        }
        markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList list = new NbtList();
        for (Map.Entry<UUID, Long> entry : balances.entrySet()) {
            NbtCompound entryNbt = new NbtCompound();
            entryNbt.putUuid(UUID_KEY, entry.getKey());
            entryNbt.putLong(BALANCE_KEY, entry.getValue());
            list.add(entryNbt);
        }
        nbt.put(BALANCES_KEY, list);
        return nbt;
    }

    public static MoneyPersistentState readNbt(NbtCompound nbt) {
        MoneyPersistentState state = new MoneyPersistentState();
        NbtList list = nbt.getList(BALANCES_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            NbtCompound entry = list.getCompound(i);
            UUID uuid = entry.getUuid(UUID_KEY);
            long balance = entry.getLong(BALANCE_KEY);
            state.balances.put(uuid, balance);
        }
        return state;
    }

    public static MoneyPersistentState get(MinecraftServer server) {
        ServerWorld world = server.getOverworld();
        PersistentStateManager manager = world.getPersistentStateManager();
        return manager.getOrCreate(TYPE, NAME);
    }
}
