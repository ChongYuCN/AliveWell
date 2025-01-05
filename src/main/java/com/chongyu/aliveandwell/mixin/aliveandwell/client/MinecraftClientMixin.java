package com.chongyu.aliveandwell.mixin.aliveandwell.client;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin {

    @Inject(method = "getWindowTitle", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void injectedGetWindowTitle(CallbackInfoReturnable<String> cir, StringBuilder stringBuilder) {
        if(!AliveAndWellMain.VERSION.contains("-modrinth")){
            stringBuilder.append(" -危险世界求生整合包 (Dangerous World) "+ AliveAndWellMain.VERSION+" by ChongYu(重羽)");
        }
        cir.setReturnValue(stringBuilder.toString());
    }

}
