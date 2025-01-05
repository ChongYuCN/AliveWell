package aliveandwell.aliveandwell.flintcoppertool.utils;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.flintcoppertool.world.StickTwigGen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public abstract class ModFeature {
   
   public static final Feature<DefaultFeatureConfig> STICK_TWIG_FEATURE = Registry.register(
      Registry.FEATURE,
      new Identifier(AliveAndWellMain.MOD_ID, "stick_twig_gen"),
      new StickTwigGen(DefaultFeatureConfig.CODEC));
}
