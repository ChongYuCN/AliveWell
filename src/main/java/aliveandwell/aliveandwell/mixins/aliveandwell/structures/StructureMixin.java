package aliveandwell.aliveandwell.mixins.aliveandwell.structures;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.data.SaveDatas;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.minecraft.world.gen.structure.StructureType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(Structure.class)
public abstract class StructureMixin {

//    @Shadow
//    @Final
//    protected Structure.Config config;
//    @Shadow public abstract StructureType<?> getType();
    //Structure.class
//    @Inject(at = @At("HEAD"), method = "isBiomeValid")
//    private static void isBiomeValid(Structure.StructurePosition result, ChunkGenerator chunkGenerator, NoiseConfig noiseConfig, Predicate<RegistryEntry<Biome>> validBiomes, CallbackInfoReturnable<Boolean> ca) {
//        BlockPos blockPos = result.position();
//
//        if(!SaveDatas.canSpawnStructure){
//            RegistryKey<Biome> biome = chunkGenerator.getBiomeSource().getBiome(BiomeCoords.fromBlock(blockPos.getX()), BiomeCoords.fromBlock(blockPos.getY()), BiomeCoords.fromBlock(blockPos.getZ()), noiseConfig.getMultiNoiseSampler()).getKey().get();
//            if(biome.getValue().toString().contains("twilightforest:")
//                || biome.getValue().toString().contains("minecells:")
//                || biome.getValue().toString().contains("aliveandwell:")
//                || biome.getValue().toString().contains("ad_astra:")
//            ){
//                ca.setReturnValue(validBiomes.test(chunkGenerator.getBiomeSource().getBiome(BiomeCoords.fromBlock(blockPos.getX()), BiomeCoords.fromBlock(blockPos.getY()), BiomeCoords.fromBlock(blockPos.getZ()), noiseConfig.getMultiNoiseSampler())));
//            }else {
//                ca.setReturnValue(false);
//            }
//        }
//    }

}
