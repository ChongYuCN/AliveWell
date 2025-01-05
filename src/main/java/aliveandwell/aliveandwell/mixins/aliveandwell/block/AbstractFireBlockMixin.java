package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin extends Block {

    public AbstractFireBlockMixin(Settings settings) {
        super(settings);
    }

    @Overwrite
    private static boolean isOverworldOrNether(World world) {
        return false;
    }
}
