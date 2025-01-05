package aliveandwell.aliveandwell.mixins.aliveandwell.loot;

import net.minecraft.data.server.EntityLootTableGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
@Mixin(EntityLootTableGenerator.class)
public abstract class EntityLootTableGeneratorMixin implements Consumer<BiConsumer<Identifier, LootTable.Builder>> {

//    @ModifyArg(method = "accept(Ljava/util/function/BiConsumer;)V",
//            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityType;VINDICATOR:Lnet/minecraft/entity/EntityType;")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/loot/entry/ItemEntry;builder(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/loot/entry/LeafEntry$Builder;",ordinal = 0)
//            )
//    private ItemConvertible injected(ItemConvertible drop) {
//        return Items.DIAMOND;
//    }
//
//    @ModifyArg(method = "accept(Ljava/util/function/BiConsumer;)V",
//            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityType;EVOKER:Lnet/minecraft/entity/EntityType;")),
//            at = @At(value = "INVOKE",
//                    target = "Lnet/minecraft/loot/entry/ItemEntry;builder(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/loot/entry/LeafEntry$Builder;",ordinal = 0)
//    )
//    private ItemConvertible injected1(ItemConvertible drop) {
//        return Items.LAPIS_LAZULI;
//    }

}
