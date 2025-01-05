package aliveandwell.aliveandwell.flintcoppertool.utils;

import java.util.List;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class ModPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> STICK_TWIG;

    private static RegistryEntry<PlacedFeature> register(String id, PlacedFeature placedFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, new Identifier(AliveAndWellMain.MOD_ID, id), placedFeature);
    }

    static {
        STICK_TWIG = register("stick_twig_gen", new PlacedFeature(ModConfiguredFeatures.STICK_TWIG, List.of(
            SquarePlacementModifier.of(),
            PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP,
            BiomePlacementModifier.of()
        )));
    }
}
