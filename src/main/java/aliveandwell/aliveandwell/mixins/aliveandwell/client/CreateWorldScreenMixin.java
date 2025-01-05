package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {

    protected CreateWorldScreenMixin(Text title) {
        super(title);
    }

    @Overwrite
    private Difficulty getDifficulty() {
        return Difficulty.HARD;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        Screens.getButtons((CreateWorldScreen)(Object)this).get(0).active = false;
        Screens.getButtons((CreateWorldScreen)(Object)this).get(1).active = false;
        Screens.getButtons((CreateWorldScreen)(Object)this).get(2).active = false;
        Screens.getButtons((CreateWorldScreen)(Object)this).get(3).active = false;
        Screens.getButtons((CreateWorldScreen)(Object)this).get(4).active = false;
    }

}
