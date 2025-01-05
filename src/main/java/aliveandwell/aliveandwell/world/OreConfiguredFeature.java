package aliveandwell.aliveandwell.world;

import aliveandwell.aliveandwell.registry.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class OreConfiguredFeature {
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_MITHRIL_OVERWORLD;
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_EMERALD_OVERWORLD;
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_MITHRIL_DEEPSLATE_OVERWORLD;

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_WUJIN_OVERWORLD;
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_WUJIN_DEEPSLATE_OVERWORLD;

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_NITER_OVERWORLD;
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_NITER_DEEPSLATE_OVERWORLD;

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_EMERALD_DEEPSLATE_OVERWORLD;
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_ADAMANTIUM_NETHER;

    static
    {
        ORE_MITHRIL_OVERWORLD = ConfiguredFeatures.register("ore_mithril_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, BlockInit.ORE_MITHRIL.getDefaultState())), 3));
        ORE_EMERALD_OVERWORLD = ConfiguredFeatures.register("ore_emerald_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, BlockInit.ORE_EX.getDefaultState())), 4));//size:每处的最大数量，至少为3，以下几乎看不到
        ORE_MITHRIL_DEEPSLATE_OVERWORLD = ConfiguredFeatures.register("ore_mithril_deepslate_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.ORE_MITHRIL_DEEPSLATE.getDefaultState())), 3));
        ORE_EMERALD_DEEPSLATE_OVERWORLD = ConfiguredFeatures.register("ore_emerald_deepslate_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.ORE_EX_DEEPSLATE.getDefaultState())), 6));
        ORE_ADAMANTIUM_NETHER = ConfiguredFeatures.register("ore_adamantium_nether", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.NETHERRACK, BlockInit.ORE_ADAMANTIUM_NETHER.getDefaultState())), 4));

        ORE_WUJIN_OVERWORLD = ConfiguredFeatures.register("ore_wujin_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, BlockInit.WUJIN_ORE.getDefaultState())), 3));//size:每处的最大数量，至少为3，以下几乎看不到
        ORE_WUJIN_DEEPSLATE_OVERWORLD = ConfiguredFeatures.register("ore_wujin_deepslate_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.WUJIN_ORE_DEEPSLATE.getDefaultState())), 4));

        ORE_NITER_OVERWORLD = ConfiguredFeatures.register("ore_niter_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, BlockInit.NITER_ORE.getDefaultState())), 4));//size:每处的最大数量，至少为3，以下几乎看不到
        ORE_NITER_DEEPSLATE_OVERWORLD = ConfiguredFeatures.register("ore_niter_deepslate_overworld", Feature.ORE, new OreFeatureConfig(List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.NITER_ORE_DEEPSLATE.getDefaultState())), 4));
    }
}
