package aliveandwell.aliveandwell.mixins.aliveandwell.structures;

import aliveandwell.aliveandwell.data.SaveDatas;
import aliveandwell.aliveandwell.accessor.IStructureAssorWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Mutable
    @Final
    @Shadow
    protected final BiomeSource biomeSource;

    public ChunkGeneratorMixin(BiomeSource biomeSource) {
        this.biomeSource = biomeSource;
    }

    @Inject(at = @At("HEAD"), method = "trySetStructureStart", cancellable = true)
    private void trySetStructureStart(StructureSet.WeightedEntry weightedEntry, StructureAccessor structureAccessor, DynamicRegistryManager dynamicRegistryManager, NoiseConfig noiseConfig, StructureTemplateManager structureManager, long seed, Chunk chunk, ChunkPos pos, ChunkSectionPos sectionPos, CallbackInfoReturnable<Boolean> cir) {
        if (!aliveandwell$canGeneratorStructure(weightedEntry.structure().value(),dynamicRegistryManager,structureAccessor)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean aliveandwell$canGeneratorStructure(Structure structure, DynamicRegistryManager dynamicRegistryManager, StructureAccessor structureAccessor){
        if(((IStructureAssorWorld)structureAccessor).aliveAndWell$getWorldAccess() instanceof ServerWorld world ){
            SaveDatas serverState = SaveDatas.getServerState(Objects.requireNonNull(world.getServer()));
            if(world.getRegistryKey() == World.OVERWORLD
                    && !serverState.canSpawnStructure
                    && structure != dynamicRegistryManager.getManaged(Registry.STRUCTURE_KEY).get(StructureKeys.MINESHAFT)){
                return false;
            }
        }
        return true;
    }
}
