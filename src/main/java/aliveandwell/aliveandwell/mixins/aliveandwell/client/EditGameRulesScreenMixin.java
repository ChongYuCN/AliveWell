package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditGameRulesScreen.class)
@Environment(EnvType.CLIENT)
public abstract class EditGameRulesScreenMixin extends Screen {
    @Shadow private ButtonWidget doneButton;
    protected EditGameRulesScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"), cancellable = true)
    private void init(CallbackInfo ca) {
        this.doneButton.active = false;
    }
}
