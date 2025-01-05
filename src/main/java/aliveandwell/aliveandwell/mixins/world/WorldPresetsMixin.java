package aliveandwell.aliveandwell.mixins.aliveandwell.world;

import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WorldPresets.class)
public class WorldPresetsMixin {

    @Overwrite
    public static GeneratorOptions createDefaultOptions(DynamicRegistryManager dynamicRegistryManager, long seed, boolean generateStructures, boolean bonusChest) {
        return ((WorldPreset)dynamicRegistryManager.get(Registry.WORLD_PRESET_KEY).entryOf(WorldPresets.LARGE_BIOMES).value()).createGeneratorOptions(seed, generateStructures, bonusChest);
    }
}
