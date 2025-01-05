package aliveandwell.aliveandwell.mixins.aliveandwell.item;

import net.minecraft.item.FoodComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FoodComponent.class)
public class FoodComponentMixin {
    @Overwrite
    public boolean isAlwaysEdible() {
        return true;
    }
}
