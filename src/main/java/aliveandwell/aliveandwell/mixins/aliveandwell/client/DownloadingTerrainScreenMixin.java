package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DownloadingTerrainScreen.class)
public abstract class DownloadingTerrainScreenMixin extends Screen {

    protected DownloadingTerrainScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        String config = Text.translatable("aliveandwell.loadgame.info1").getString();
        String config1 = Text.translatable("aliveandwell.loadgame.info2").getString();
        DownloadingTerrainScreen.drawCenteredText(matrices, this.textRenderer, config, this.width / 2, this.height / 2 - this.textRenderer.fontHeight / 2 + 60, 0xFFFFFF);
        DownloadingTerrainScreen.drawCenteredText(matrices, this.textRenderer, config1, this.width / 2, this.height / 2 - this.textRenderer.fontHeight / 2 + 80, 0xFFFFFF);
    }
}
