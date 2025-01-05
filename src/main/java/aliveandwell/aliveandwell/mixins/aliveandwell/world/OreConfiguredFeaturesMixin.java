package aliveandwell.aliveandwell.mixins.aliveandwell.world;

import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(OreConfiguredFeatures.class)
public class OreConfiguredFeaturesMixin {
    //ͭ��=================================================================================================================
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=ore_copper_small")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int copperCountModifyMax(int original){
        return 6;
    }
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_copper_large")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int copperDeepCountModifyMax(int original){
        return 15;
    }

    //����=================================================================================================================
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=ore_iron")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int ironCountModifyMax(int original){
        return 2;
    }
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_iron_small")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int ironDeepCountModifyMax(int original){
        return 4;
    }

    //���=================================================================================================================
//    @ModifyArg(
//            method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_gold")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
//            index = 1)
//    private static int goldCountModifyMax(int original){
//        return 1;
//    }
//    @ModifyArg(
//            method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_gold_buried")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
//            index = 1)
//    private static int goldDeepCountModifyMax(int original){
//        return 5;
//    }

    //ú��=================================================================================================================
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_coal")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int coalCountModifyMax(int original){
        return 10;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_coal_buried")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int coalDeepCountModifyMax(int original){
        return 10;
    }

    //���ʯ��=================================================================================================================
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_lapis")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int lapisCountModifyMax(int original){
        return 5;
    }
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_lapis_buried")),
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
            index = 1)
    private static int lapisDeepCountModifyMax(int original){
        return 4;
    }

//    @ModifyArg(
//            method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_diamond_small")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
//            index = 1)
//    private static int diamond_smallCountModifyMax(int original){
//        return 1;
//    }
//    @ModifyArg(
//            method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_diamond_large")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
//            index = 1)
//    private static int diamond_largeCountModifyMax(int original){
//        return 3;
//    }
//    @ModifyArg(
//            method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=ore_diamond_buried")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Ljava/util/List;I)V",ordinal = 0),
//            index = 1)
//    private static int diamond_buriedCountModifyMax(int original){
//        return 1;
//    }
}
