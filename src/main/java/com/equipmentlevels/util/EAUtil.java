package com.chongyu.aliveandwell.equipmentlevels.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.*;
import net.minecraft.text.Text;

import java.util.List;
import java.util.UUID;

public class EAUtil {
    public static final ImmutableList<Item> vanillaItems = ImmutableList.of(Items.IRON_SWORD, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_BOOTS, Items.IRON_CHESTPLATE, Items.IRON_HELMET,
            Items.IRON_LEGGINGS, Items.DIAMOND_AXE, Items.DIAMOND_HOE, Items.DIAMOND_SWORD, Items.DIAMOND_BOOTS, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_HELMET, Items.DIAMOND_LEGGINGS,
            Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SWORD, Items.GOLDEN_BOOTS, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_HELMET, Items.GOLDEN_LEGGINGS,
            Items.STONE_AXE, Items.STONE_HOE, Items.STONE_SWORD, Items.WOODEN_AXE, Items.WOODEN_HOE, Items.WOODEN_SWORD, Items.BOW, Items.CROSSBOW, Items.TRIDENT,
            Items.NETHERITE_AXE, Items.NETHERITE_HOE, Items.NETHERITE_SWORD, Items.NETHERITE_BOOTS, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_HELMET, Items.NETHERITE_LEGGINGS,
            Items.CHAINMAIL_BOOTS, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_LEGGINGS);

    public static boolean canEnhance(Item item) {
        return item instanceof SwordItem || item instanceof AxeItem || item instanceof HoeItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof CrossbowItem || item instanceof TridentItem;
    }

    public static boolean canEnhanceWeapon(Item item) {
        return canEnhance(item) && !(item instanceof ArmorItem);
    }

    /**
     * 近战
     */
    public static boolean canEnhanceMelee(Item item) {
        return canEnhance(item) && !(item instanceof ArmorItem) && !(item instanceof BowItem) && !(item instanceof CrossbowItem);
    }

    /**
     * 远程
     */
    public static boolean canEnhanceRanged(Item item) {
        return canEnhance(item) && (item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem);
    }


    public static boolean canEnhanceArmor(Item item) {
        return canEnhance(item) && item instanceof ArmorItem;
    }


    public static Entity getEntityByUniqueId(UUID uniqueId)
    {
        for (Entity entity : MinecraftClient.getInstance().world.getEntities())
        {
            if (entity.getUuid().equals(uniqueId))
                return entity;
        }

        return null;
    }

    public static boolean isDamageSourceAllowed(DamageSource damageSource) {
//        return !(damageSource == DamageSource.FALL ||
//                damageSource == DamageSource.DROWN ||
//                damageSource == DamageSource.CACTUS ||
//                damageSource == DamageSource.STARVE ||
//                damageSource == DamageSource.IN_WALL ||
//                damageSource == DamageSource.IN_FIRE ||
//                damageSource == DamageSource.OUT_OF_WORLD)
//                || damageSource.getSource() instanceof LivingEntity;
        return damageSource.getSource() instanceof LivingEntity;
    }

    public static boolean containsString(List<Text> tooltip, String string) {
        if (tooltip.size() <= 0) return false;

        for (Text component : tooltip) {
            if (component.getString().equals(string))
                return true;
        }
        return false;
    }

    public static int lineContainsString(List<Text> tooltip, String string) {
        if (tooltip.size() <= 0) return -1;

        for (int i = 0; i < tooltip.size(); i++) {
            if (tooltip.get(i).getString().equals(string))
                return i;
        }
        return -1;
    }
}
