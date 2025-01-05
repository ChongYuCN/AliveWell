package com.chongyu.aliveandwell.mixin.aliveandwell.client.disable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GameOptions.class)
@Environment(EnvType.CLIENT)
public class GameOptionsMixin {

    @Overwrite
    public SimpleOption<Double> getGamma() {
        return new SimpleOption("options.gamma", SimpleOption.emptyTooltip(), (optionText, value) -> {
            int i = 0;
            if (i == 0) {
                return Text.translatable("options.gamma.min");
            } else if (i == 50) {
                return Text.translatable("options.gamma.default");
            } else {
                return i == 100 ? Text.translatable("options.gamma.max") : Text.literal(Integer.toString(i));
            }
        }, SimpleOption.DoubleSliderCallbacks.INSTANCE, 0.0, (value) -> {
        });
    }
}
