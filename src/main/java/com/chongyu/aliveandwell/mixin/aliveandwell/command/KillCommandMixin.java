package com.chongyu.aliveandwell.mixin.aliveandwell.command;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.KillCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KillCommand.class)
public class KillCommandMixin {
    @Inject(at = @At("HEAD"), method = "register", cancellable = true)
    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo ci) {
        if(!AliveAndWellMain.canCreative){
            ci.cancel();
        }
    }
}
