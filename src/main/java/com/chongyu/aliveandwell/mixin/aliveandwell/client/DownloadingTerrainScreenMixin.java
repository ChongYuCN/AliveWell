package com.chongyu.aliveandwell.mixin.aliveandwell.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DownloadingTerrainScreen.class)
public abstract class DownloadingTerrainScreenMixin extends Screen {
    protected DownloadingTerrainScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "render")
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        String config = Text.translatable("aliveandwell.loadgame.info1").getString();
        String config1 = Text.translatable("aliveandwell.loadgame.info2").getString();
        context.drawCenteredTextWithShadow(this.textRenderer, config, this.width / 2, this.height / 2 - this.textRenderer.fontHeight / 2 + 60, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, config1, this.width / 2, this.height / 2 - this.textRenderer.fontHeight / 2 + 80, 0xFFFFFF);
    }
}
