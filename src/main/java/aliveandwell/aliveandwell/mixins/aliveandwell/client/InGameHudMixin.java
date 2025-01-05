package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Objects;


@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow
    private PlayerEntity getCameraPlayer() {
        return null;
    }

    @ModifyConstant(method = "renderStatusBars",
            constant = @Constant(intValue = 10),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/entity/effect/StatusEffects;HUNGER:Lnet/minecraft/entity/effect/StatusEffect;")
            )
    )
    public int renderStatusBarsFood(int constant) {
        return getHungerCount();
    }

    @Unique
    private int getHungerCount() {
        int count = 3 + (int)Math.floor(Objects.requireNonNull(this.getCameraPlayer()).experienceLevel / 5.0);
        if (count > 10) count = 10;
        return count;
    }
}