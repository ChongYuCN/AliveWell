package com.chongyu.aliveandwell.mixin.aliveandwell.client;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
@Environment(EnvType.CLIENT)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
    @Unique
    private int totalEx;
    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "handledScreenTick", at = @At("HEAD"))
    public void handledScreenTick(CallbackInfo ca) {
        if (this.client != null && this.client.player != null) {
            this.totalEx = this.client.player.totalExperience;
        }
    }

    @Inject(method = "drawForeground", at = @At("HEAD"))
    public void drawForeground(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        context.drawText(this.textRenderer, Text.translatable("XP:"+String.valueOf(this.totalEx)).formatted(Formatting.GREEN), this.backgroundWidth/2+12, -10, 175752,false);
//        context.drawText(this.textRenderer, Text.translatable("Day:"+AliveAndWellMain.day + (( AliveAndWellMain.day == 1 || AliveAndWellMain.day % AliveAndWellMain.dayTime == 0 && AliveAndWellMain.day<=48 || AliveAndWellMain.day % AliveAndWellMain.dayTimeEnd == 0 && AliveAndWellMain.day>48) ? "(2)":"(1)")).formatted(Formatting.YELLOW), this.backgroundWidth/2+12 , -20, 175752,false);
        context.drawText(this.textRenderer, Text.translatable("Day:"+AliveAndWellMain.day).formatted(Formatting.YELLOW), this.backgroundWidth/2+12 , -20, 175752,false);
    }
}
