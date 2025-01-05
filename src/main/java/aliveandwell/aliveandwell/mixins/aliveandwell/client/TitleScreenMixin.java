package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Final
    @Shadow private  boolean doBackgroundFade;
    @Shadow private long backgroundFadeStart;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        String string1 = "";

        string1 = "危险世界求生核心DangerousWorldCore (Alive Well-"+ AliveAndWellMain.VERSION+")";

//        String string1 = "危险世界求生整合包"+ AliveAndWellMain.VERSION+"-hard(困难) by ChongYu";
        float f = this.doBackgroundFade ? (float)(Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F : 1.0F;
        float g = this.doBackgroundFade ? MathHelper.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        int l = MathHelper.ceil(g * 255.0F) << 24;
        drawStringWithShadow(matrices, this.textRenderer, string1, 2, this.height - 20, 16777215 | l);
    }
}
