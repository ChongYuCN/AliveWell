package aliveandwell.aliveandwell.mixins.aliveandwell.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;
import org.spongepowered.asm.mixin.*;

@Mixin(ArmorMaterials.class)
public abstract class ArmorMaterialsMixin {

    @Mutable
    @Final
    @Shadow private final int[] protectionAmounts;

    @Mutable
    @Final
    @Shadow private final Lazy<Ingredient> repairIngredientSupplier;

    @Shadow public abstract String getName();

    protected ArmorMaterialsMixin(int[] protectionAmounts, Lazy<Ingredient> repairIngredientSupplier) {
        this.protectionAmounts = protectionAmounts;
        this.repairIngredientSupplier = repairIngredientSupplier;
    }

    //copper 2,4,6,2
    //iron 4,6,8,4
    //wujin 8,10,12,8
    //miyin 10, 12, 16, 10
    @Overwrite
    public int getProtectionAmount(EquipmentSlot slot) {
        if(this.getName().equals("iron")){
            if(slot == EquipmentSlot.FEET){
                return 4;
            }else if(slot == EquipmentSlot.LEGS){
                return 6;
            }else if(slot == EquipmentSlot.CHEST){
                return 8;
            }else if(slot == EquipmentSlot.HEAD){
                return 4;
            }
        }
        return this.protectionAmounts[slot.getEntitySlotId()];
    }

    @Overwrite
    public Ingredient getRepairIngredient() {
        if(this.getName().equals("iron")){
            return Ingredient.ofItems(new ItemConvertible[]{Items.IRON_NUGGET});
        }
        return this.repairIngredientSupplier.get();
    }
}
