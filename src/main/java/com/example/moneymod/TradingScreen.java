package com.example.moneymod;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class TradingScreen extends HandledScreen<TradingScreenHandler> {
    private ButtonWidget sellButton;
    private Text balanceText;

    public TradingScreen(TradingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 120;
        this.playerInventoryTitleX = 8;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        this.sellButton = ButtonWidget.builder(Text.translatable("gui.moneymod.sell_all"), button -> sendSellAll())
                .dimensions(x + 20, y + 60, 136, 20)
                .build();
        addDrawableChild(this.sellButton);
        updateBalanceText();
    }

    private void sendSellAll() {
        ClientPlayNetworking.send(NetworkHandler.SELL_INVENTORY, PacketByteBufs.empty());
    }

    private void updateBalanceText() {
        this.balanceText = Text.translatable("gui.moneymod.balance", ClientBalanceStorage.getBalance());
    }

    public void onBalanceUpdated() {
        updateBalanceText();
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        updateBalanceText();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.fill(x, y, x + this.backgroundWidth, y + this.backgroundHeight, 0xAA101010);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, 8, 6, 0xFFFFFF, false);
        if (this.balanceText != null) {
            context.drawText(this.textRenderer, this.balanceText, 8, 24, 0xFFD700, false);
        }
        context.drawText(this.textRenderer, this.playerInventoryTitle, 8, this.playerInventoryTitleY, 0xFFFFFF, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
