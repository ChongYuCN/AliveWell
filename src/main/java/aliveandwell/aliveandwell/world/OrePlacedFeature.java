package aliveandwell.aliveandwell.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;
import java.util.function.Predicate;

public class OrePlacedFeature {

    public static final RegistryEntry<PlacedFeature> ORE_MITHRIL_OVERWORLD;
    public static final RegistryEntry<PlacedFeature> ORE_EMERALD_OVERWORLD;
    public static final RegistryEntry<PlacedFeature> ORE_MITHRIL_DEEPSLATE_OVERWORLD;
    public static final RegistryEntry<PlacedFeature> ORE_EMERALD_DEEPSLATE_OVERWORLD;
    public static final RegistryEntry<PlacedFeature> ORE_ADAMANTIUM_NETHER;

    public static final RegistryEntry<PlacedFeature> ORE_WUJIN_OVERWORLD;
    public static final RegistryEntry<PlacedFeature> ORE_WUJIN_DEEPSLATE_OVERWORLD;

    public static final RegistryEntry<PlacedFeature> ORE_NITER_OVERWORLD;
    public static final RegistryEntry<PlacedFeature> ORE_NITER_DEEPSLATE_OVERWORLD;

    static
    {
        ORE_MITHRIL_OVERWORLD = PlacedFeatures.register("ore_mithril_overworld", OreConfiguredFeature.ORE_MITHRIL_OVERWORLD, modifiersWithCount(1, HeightRangePlacementModifier.uniform(YOffset.fixed(0),YOffset.fixed(20)) ));
        ORE_EMERALD_OVERWORLD = PlacedFeatures.register("ore_emerald_overworld", OreConfiguredFeature.ORE_EMERALD_OVERWORLD, modifiersWithCount(4, HeightRangePlacementModifier.uniform(YOffset.fixed(0),YOffset.fixed(40)) ));//count:生成率
        ORE_MITHRIL_DEEPSLATE_OVERWORLD = PlacedFeatures.register("ore_mithril_deepslate_overworld", OreConfiguredFeature.ORE_MITHRIL_DEEPSLATE_OVERWORLD, modifiersWithCount(1, HeightRangePlacementModifier.uniform(YOffset.fixed(-64),YOffset.fixed(0)) ));
        ORE_EMERALD_DEEPSLATE_OVERWORLD = PlacedFeatures.register("ore_emerald_deepslate_overworld", OreConfiguredFeature.ORE_EMERALD_DEEPSLATE_OVERWORLD, modifiersWithCount(4, HeightRangePlacementModifier.uniform(YOffset.fixed(-64),YOffset.fixed(0)) ));
        ORE_ADAMANTIUM_NETHER = PlacedFeatures.register("ore_adamantium_nether", OreConfiguredFeature.ORE_ADAMANTIUM_NETHER, modifiersWithCount(4, HeightRangePlacementModifier.uniform(YOffset.fixed(0),YOffset.fixed(125))));

        ORE_WUJIN_OVERWORLD = PlacedFeatures.register("ore_wujin_overworld", OreConfiguredFeature.ORE_WUJIN_OVERWORLD, modifiersWithCount(5, HeightRangePlacementModifier.uniform(YOffset.fixed(0),YOffset.fixed(20)) ));//count:生成率
        ORE_WUJIN_DEEPSLATE_OVERWORLD = PlacedFeatures.register("ore_wujin_deepslate_overworld", OreConfiguredFeature.ORE_WUJIN_DEEPSLATE_OVERWORLD, modifiersWithCount(5, HeightRangePlacementModifier.uniform(YOffset.fixed(-64),YOffset.fixed(0)) ));

        ORE_NITER_OVERWORLD = PlacedFeatures.register("ore_niter_overworld", OreConfiguredFeature.ORE_NITER_OVERWORLD, modifiersWithCount(10, HeightRangePlacementModifier.uniform(YOffset.fixed(0),YOffset.fixed(50)) ));//count:生成率
        ORE_NITER_DEEPSLATE_OVERWORLD = PlacedFeatures.register("ore_niter_deepslate_overworld", OreConfiguredFeature.ORE_NITER_DEEPSLATE_OVERWORLD, modifiersWithCount(10, HeightRangePlacementModifier.uniform(YOffset.fixed(-64),YOffset.fixed(0)) ));
    }


    public static void register()
    {
        // Cast the RegistryEntry to RegistryKey for use with BiomModification API
        RegistryKey<PlacedFeature> overworldMithrilBlock = ORE_MITHRIL_OVERWORLD.getKey().get();
        RegistryKey<PlacedFeature> overworldEmeraldBlock = ORE_EMERALD_OVERWORLD.getKey().get();
        RegistryKey<PlacedFeature> overworldMithrilBlockDeepslate = ORE_MITHRIL_DEEPSLATE_OVERWORLD.getKey().get();
        RegistryKey<PlacedFeature> overworldEmeraldBlockDeepslate = ORE_EMERALD_DEEPSLATE_OVERWORLD.getKey().get();
        RegistryKey<PlacedFeature> netherAdamantiumOre = ORE_ADAMANTIUM_NETHER.getKey().get();

        RegistryKey<PlacedFeature> overworldWujinOre = ORE_WUJIN_OVERWORLD.getKey().get();
        RegistryKey<PlacedFeature> overworldWujinOreDeepslate = ORE_WUJIN_DEEPSLATE_OVERWORLD.getKey().get();

        RegistryKey<PlacedFeature> overworldNiterOre = ORE_NITER_OVERWORLD.getKey().get();
        RegistryKey<PlacedFeature> overworldNiterOreDeepslate = ORE_NITER_DEEPSLATE_OVERWORLD.getKey().get();

        // Inject into Biomes
        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldMithrilBlock);
        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldEmeraldBlock);
        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldMithrilBlockDeepslate);
        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldEmeraldBlockDeepslate);
        BiomeModifications.addFeature(netherSelector(), GenerationStep.Feature.UNDERGROUND_ORES, netherAdamantiumOre);

        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldWujinOre);
        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldWujinOreDeepslate);

        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldNiterOre);
        BiomeModifications.addFeature(overworldSelector(), GenerationStep.Feature.UNDERGROUND_ORES, overworldNiterOreDeepslate);
    }

    public static Predicate<BiomeSelectionContext> overworldSelector()
    {
        return context -> context.getBiomeRegistryEntry().isIn(BiomeTags.IS_OVERWORLD);
    }

    public static Predicate<BiomeSelectionContext> netherSelector()
    {
        return context -> context.getBiomeRegistryEntry().isIn(BiomeTags.IS_NETHER);
    }

    public static Predicate<BiomeSelectionContext> endSelector()
    {
        return context -> context.getBiomeRegistryEntry().isIn(BiomeTags.IS_END);
    }

    // Used here because the vanilla ones are private
    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
