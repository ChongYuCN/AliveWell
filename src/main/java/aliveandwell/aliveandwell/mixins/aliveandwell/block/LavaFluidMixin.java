package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import net.minecraft.fluid.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {


    /**
     * @author
     * @reason
     */

    //是否可以无限水
    @Overwrite
    public boolean isInfinite() {
        return true;
    }
}
