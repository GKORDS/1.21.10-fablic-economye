package com.example.moneymod;

public final class ClientBalanceStorage {
    private static long balance;

    private ClientBalanceStorage() {
    }

    public static long getBalance() {
        return balance;
    }

    public static void setBalance(long value) {
        balance = value;
    }
}
