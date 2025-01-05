package aliveandwell.aliveandwell.mixins.aliveandwell;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {

    @Shadow private final boolean generateStructures;

    public GeneratorOptionsMixin(boolean generateStructures) {
        this.generateStructures = generateStructures;
    }

    @Overwrite
    public boolean hasBonusChest() {
        return false;
    }


//    @Inject(at = @At("HEAD"), method = "shouldGenerateStructures", cancellable = true)
//    public void shouldGenerateStructures(CallbackInfoReturnable<Boolean> ca) {
//        if(AliveAndWellMain.day <= 64 ){
//            ca.setReturnValue(false);
//        }
//    }
}
