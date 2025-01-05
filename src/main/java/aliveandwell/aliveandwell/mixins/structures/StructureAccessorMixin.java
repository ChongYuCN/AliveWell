package aliveandwell.aliveandwell.mixins.aliveandwell.structures;

import aliveandwell.aliveandwell.accessor.IStructureAssorWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StructureAccessor.class)
public class StructureAccessorMixin implements IStructureAssorWorld {
    @Mutable
    @Final
    @Shadow
    private final WorldAccess world;

    public StructureAccessorMixin(WorldAccess world) {
        this.world = world;
    }

    @Override
    public WorldAccess aliveAndWell$getWorldAccess() {
        return world;
    }
}
