package com.chongyu.aliveandwell.mixin.aliveandwell.client;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.SleepingChatScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SleepingChatScreen.class)
public class SleepingChatScreenMixin extends ChatScreen {
    @Shadow private ButtonWidget stopSleepingButton;
    public SleepingChatScreenMixin(String originalChatText) {
        super(originalChatText);
    }

    @Overwrite
    public void init() {
        super.init();
        this.stopSleepingButton = ButtonWidget.builder(Text.translatable("multiplayer.stopSleeping"), button -> this.stopSleeping()).dimensions(this.width / 2-12, this.height - 57, 24, 20).build();
        this.addDrawableChild(this.stopSleepingButton);
    }
    @Shadow
    private void stopSleeping() {
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.player.networkHandler;
        clientPlayNetworkHandler.sendPacket(new ClientCommandC2SPacket(this.client.player, ClientCommandC2SPacket.Mode.STOP_SLEEPING));
    }
}
