package aliveandwell.aliveandwell.mixins.aliveandwell.world;

import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(OrePlacedFeatures.class)
public class OrePlacedFeaturesMixin {

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_copper")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int copperSmallHighCountModifyMax(int count){
        return 15;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_coal_upper")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int coalUperCountModifyMax(int count){
        return 20;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_coal_lower")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int coalSmallCountModifyMax(int count){
        return 15;
    }

    //-------------------------------------------铁-----------------------------------------------
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_iron_upper")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;trapezoid(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 0)
    private static YOffset ironUperHighModifyMax1(YOffset minOffset){
        return YOffset.fixed(-60);
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_iron_upper")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;trapezoid(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 1)
    private static YOffset ironUperHighModifyMax(YOffset minOffset){
        return YOffset.fixed(60);
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_iron_upper")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int ironUperHighCountModifyMax(int count){
        return 1;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_iron_middle")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int coalSmallHighCountModifyMax(int count){
        return 1;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_iron_small")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int ore_iron_smallCountModifyMax(int count){
        return 10;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_iron_small")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;uniform(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 1)
    private static YOffset ironSmallHighCountModifyMax(YOffset minOffset){
        return YOffset.fixed(60);
    }
    //----------------------------------------铁--------------------------------------------

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_copper")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;trapezoid(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 0)
    private static YOffset copperDeepCountModifyMax(YOffset minOffset){
        return YOffset.fixed(-60);
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_copper_large")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;trapezoid(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 0)
    private static YOffset copperDeepHighCountModifyMax(YOffset minOffset){
        return YOffset.fixed(-60);
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_emerald")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;trapezoid(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 0)
    private static YOffset ore_emeraldHighCountModifyMax(YOffset minOffset){
        return YOffset.fixed(-60);
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_emerald")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;trapezoid(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 1)
    private static YOffset emeraldHighCountModifyMax(YOffset minOffset){
        return YOffset.fixed(60);
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_lapis_buried")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;uniform(Lnet/minecraft/world/gen/YOffset;Lnet/minecraft/world/gen/YOffset;)Lnet/minecraft/world/gen/placementmodifier/HeightRangePlacementModifier;",ordinal = 0),
            index = 1)
    private static YOffset lapisBuriedHighCountModifyMax(YOffset minOffset){
        return YOffset.fixed(30);
    }

//    @ModifyArg(
//            method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_gold_extra")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
//            index = 0)
//    private static int ore_gold_extraCountModifyMax(int count){
//        return 1;
//    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_diamond")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int ore_diamondCountModifyMax(int count){
        return 1;
    }
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_diamond_large")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithRarity(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int ore_diamond_largeCountModifyMax(int count){
        return 5;
    }
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_diamond_buried")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OrePlacedFeatures;modifiersWithCount(ILnet/minecraft/world/gen/placementmodifier/PlacementModifier;)Ljava/util/List;",ordinal = 0),
            index = 0)
    private static int ore_diamond_buriedCountModifyMax(int count){
        return 1;
    }

}
