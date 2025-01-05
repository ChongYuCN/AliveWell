package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.OptionalLong;

@Mixin(MoreOptionsDialog.class)
public abstract class MoreOptionsDialogMixin implements Drawable {

    @Shadow private MultilineText amplifiedInfoText;
    @Shadow private GeneratorOptionsHolder generatorOptionsHolder;
    @Shadow private Optional<RegistryEntry<WorldPreset>> presetEntry;
    @Shadow private OptionalLong seed;

    @Inject(at=@At("RETURN"), method="<init>")
    public void Constructor(GeneratorOptionsHolder generatorOptionsHolder, Optional<RegistryKey<WorldPreset>> presetKey, OptionalLong seed, CallbackInfo info) {
        this.amplifiedInfoText = MultilineText.EMPTY;
        this.generatorOptionsHolder = generatorOptionsHolder;
        this.presetEntry = presetKey.flatMap((key) -> {
            return generatorOptionsHolder.dynamicRegistryManager().get(Registry.WORLD_PRESET_KEY).getEntry(WorldPresets.LARGE_BIOMES);
        });
        this.seed = seed;
    }

}
