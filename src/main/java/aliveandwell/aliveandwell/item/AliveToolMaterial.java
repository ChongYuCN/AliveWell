package aliveandwell.aliveandwell.item;

import aliveandwell.aliveandwell.registry.ItemInit;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum AliveToolMaterial implements ToolMaterial {

//    DIAMOND(MiningLevels.DIAMOND, 1561, 8.0f, 3.0f, 10, () -> Ingredient.ofItems(Items.DIAMOND)),
//    GOLD(MiningLevels.WOOD, 32, 12.0f, 0.0f, 22, () -> Ingredient.ofItems(Items.GOLD_INGOT)),
//    NETHERITE(MiningLevels.NETHERITE, 2031, 9.0f, 4.0f, 15, () -> Ingredient.ofItems(Items.NETHERITE_INGOT));
    COPPER(1, 131, 4.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ItemInit.COPPER_NUGGET});
    }),
    EN_GENSTONE(4, 2000, 9.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ItemInit.ITEM_EN_GENSTONE});
    }),
    WUJIN(3, 1000, 8.0F, 3.0F, 15, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ItemInit.nugget_wujin});
    }),
    MITHRIL(3, 2000, 9.0F, 5.0F, 15, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ItemInit.nugget_mithril});
    }),
    ADAMANTIUM(4, 2500, 10.0F, 7.0F, 15, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ItemInit.nugget_adamantium});
    }),
    ANCIENT(5, 3500, 15.0F, 10.0F, 15, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{Items.BLAZE_POWDER});
    });

    private final int miningLevel;//砂轮
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    private AliveToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy(repairIngredient);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairIngredient.get();
    }
}
