package com.chongyu.aliveandwell.flintcoppertool.armor;

import com.chongyu.aliveandwell.registry.ItemInit;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class CopperArmorBase implements ArmorMaterial {

    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = (EnumMap) Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    private final EnumMap<ArmorItem.Type, Integer> PROTECTION = (EnumMap)Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    });

    @Override
    public int getDurability(ArmorItem.Type type) {
        return (Integer)BASE_DURABILITY.get(type) * 8;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return (Integer)PROTECTION.get(type);
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
