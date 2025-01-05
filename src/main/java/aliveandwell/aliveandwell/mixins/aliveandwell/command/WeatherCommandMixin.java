package aliveandwell.aliveandwell.mixins.aliveandwell.command;

import aliveandwell.aliveandwell.AliveAndWellMain;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.WeatherCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherCommand.class)
public class WeatherCommandMixin {
    @Inject(at = @At("HEAD"), method = "register", cancellable = true)
    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo ci) {
        if (!AliveAndWellMain.canCreative) {
            ci.cancel();
        }
    }
}
