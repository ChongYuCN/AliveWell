package aliveandwell.aliveandwell.flintcoppertool.item;

import aliveandwell.aliveandwell.registry.ItemInit;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class CopperToolBase implements ToolMaterial {

    @Override
    public int getDurability() {
        return 131;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 4.0f;
    }

    @Override
    public float getAttackDamage() {
        return 1.0f;
    }

    @Override
    public int getMiningLevel() {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ItemInit.COPPER_NUGGET);
    }
    
}
