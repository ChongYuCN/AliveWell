package aliveandwell.aliveandwell.flintcoppertool.armor;

import aliveandwell.aliveandwell.registry.ItemInit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class CopperArmorBase implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] {
        13, 15, 16, 11
    };

    private static final int[] PROTECTION = new int[] {
        2, 4, 6, 2
    };

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()]*8;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public SoundEvent getEquipSound() {
        
        return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
    }

    @Override
    public Ingredient getRepairIngredient() {
        
        return Ingredient.ofItems(ItemInit.COPPER_NUGGET);
    }

    @Override
    public String getName() {
        
        return "copper";
    }

    @Override
    public float getToughness() {
        
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        
        return 0;
    }
    
}
