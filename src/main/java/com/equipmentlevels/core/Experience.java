package com.chongyu.aliveandwell.equipmentlevels.core;

import com.chongyu.aliveandwell.equipmentlevels.util.EAUtil;
import com.chongyu.aliveandwell.equipmentlevels.util.Static;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Experience {
    public static int getNextLevel(PlayerEntity player, ItemStack stack, NbtCompound nbt, int currentLevel, int experience) {

        int newLevel = currentLevel;

        while (currentLevel < Static.MAX_LEVEL && experience >= Experience.getMaxLevelExp(currentLevel)) {
            newLevel = currentLevel + 1;
            currentLevel++;
            Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) + 1);
            player.sendMessage(Text.translatable("aliveandwell.levelupinfo").formatted(Formatting.GRAY).append(Text.translatable(Formatting.GRAY + "【")).append(stack.getName().copy().formatted(Formatting.GRAY)).append(Text.translatable(Formatting.GRAY +"】")) .append(Text.translatable("enhancedarmaments.misc.level.leveledup").formatted(Formatting.GRAY)).append(Text.translatable(" " + Formatting.GOLD  + (newLevel-1)).append(Text.translatable("aliveandwell.levelupinfo1").formatted( Formatting.GRAY))));

            int canLevel = 1 ;
            while (canLevel == 1){
                Ability ability_weapon = Ability.WEAPON_ABILITIES.get(player.getWorld().random.nextInt(Ability.WEAPON_ABILITIES.size()));
                Ability  ability_armor = Ability.ARMOR_ABILITIES.get(player.getWorld().random.nextInt(Ability.ARMOR_ABILITIES.size()));

                //武器
                if(EAUtil.canEnhance(stack.getItem()) && EAUtil.canEnhanceWeapon(stack.getItem())){
                    if(!ability_weapon.hasAbility(nbt)){
                        ability_weapon.addAbility(nbt);
                        player.sendMessage(Text.translatable("aliveandwell.levelupinfo").formatted(Formatting.LIGHT_PURPLE).append(Text.translatable(Formatting.GOLD +"【")).append(stack.getName().copy().formatted(Formatting.GOLD)).append(Text.translatable( Formatting.GOLD +"】")) .append(Text.translatable("enhancedarmaments.misc.ability.get").formatted(Formatting.LIGHT_PURPLE)).append(Text.translatable(ability_weapon.getName(nbt)).copy().formatted(Formatting.GOLD)).append(Text.of(Formatting.GRAY  + " !")) );
                        canLevel = 0;
                    }else {
                        if(ability_weapon.getLevel(nbt) < ability_weapon.getMaxLevel()){
                            ability_weapon.setLevel(nbt,ability_weapon.getLevel(nbt)+1);
                            player.sendMessage(Text.translatable("aliveandwell.levelupinfo").formatted(Formatting.LIGHT_PURPLE).append(Text.translatable(Formatting.GOLD +"【")).append(stack.getName().copy().formatted(Formatting.GOLD)).append(Text.translatable( Formatting.GOLD +"】")) .append(Text.translatable("enhancedarmaments.misc.ability.get").formatted(Formatting.LIGHT_PURPLE)).append(Text.translatable(ability_weapon.getName(nbt)).copy().formatted(Formatting.GOLD)).append(Text.of(Formatting.GRAY  + " !")) );
                            canLevel = 0;
                        }
                    }
                }

                if(EAUtil.canEnhance(stack.getItem()) && EAUtil.canEnhanceArmor(stack.getItem())){
                    if(!ability_armor.hasAbility(nbt)){
                        ability_armor.addAbility(nbt);
                        player.sendMessage(Text.translatable("aliveandwell.levelupinfo").formatted(Formatting.LIGHT_PURPLE).append(Text.translatable(Formatting.GOLD +"【")).append(stack.getName().copy().formatted(Formatting.GOLD)).append(Text.translatable( Formatting.GOLD +"】")) .append(Text.translatable("enhancedarmaments.misc.ability.get").formatted(Formatting.LIGHT_PURPLE)).append(Text.translatable(ability_armor.getName(nbt)).copy().formatted(Formatting.GOLD)).append(Text.of(Formatting.GRAY  + " !")) );
                        canLevel = 0;
                    }else {
                        if(ability_armor.getLevel(nbt) < ability_armor.getMaxLevel()){
                            ability_armor.setLevel(nbt,ability_armor.getLevel(nbt)+1);
                            player.sendMessage(Text.translatable("aliveandwell.levelupinfo").formatted(Formatting.LIGHT_PURPLE).append(Text.translatable(Formatting.GOLD +"【")).append(stack.getName().copy().formatted(Formatting.GOLD)).append(Text.translatable( Formatting.GOLD +"】")) .append(Text.translatable("enhancedarmaments.misc.ability.get").formatted(Formatting.LIGHT_PURPLE)).append(Text.translatable(ability_armor.getName(nbt)).copy().formatted(Formatting.GOLD)).append(Text.of(Formatting.GRAY  + " !")) );
                            canLevel = 0;
                        }
                    }
                }

            }

        }

        return newLevel;
    }

    public static int getLevel(NbtCompound nbt) {
        return nbt != null ? Math.max(nbt.getInt("LEVEL"), 1) : 1;
    }

    public static boolean canLevelUp(NbtCompound nbt) {
        return getLevel(nbt) < Static.MAX_LEVEL;
    }

    public static void setLevel(NbtCompound nbt, int level) {
        if (nbt != null) {
            if (level > 1)
                nbt.putInt("LEVEL", level);
            else
                nbt.remove("LEVEL");
        }
    }

    public static int getNeededExpForNextLevel(NbtCompound nbt) {
        return Experience.getMaxLevelExp(Experience.getLevel(nbt)) - Experience.getExperience(nbt);
    }

    public static int getExperience(NbtCompound nbt) {
        return nbt.contains("EXPERIENCE") ? nbt.getInt("EXPERIENCE") : 0;
    }

    public static void setExperience(NbtCompound nbt, int experience) {
        if (nbt != null) {
            if (experience > 0)
                nbt.putInt("EXPERIENCE", experience);
            else
                nbt.remove("EXPERIENCE");
        }
    }

    public static int getMaxLevelExp(int level) {
        int maxLevelExp = Static.level1Experience;
        for (int i = 1; i < level; i++)
            maxLevelExp *= Static.experienceMultiplier;
        return (int) maxLevelExp;
    }

    public static void setAbilityTokens(NbtCompound nbt, int tokens) {
        if (nbt != null) {
            if (tokens > 0)
                nbt.putInt("TOKENS", tokens);
            else
                nbt.remove("TOKENS");
        }
    }

    public static int getAbilityTokens(NbtCompound nbt) {
        return nbt != null ? nbt.getInt("TOKENS") : 0;
    }

    public static void enable(NbtCompound nbt, boolean value) {
        if (nbt != null) {
            if (value)
                nbt.putBoolean("EA_ENABLED", true);
            else
                nbt.remove("EA_ENABLED");
        }
    }

    public static boolean isEnabled(NbtCompound nbt) {
        return nbt != null && nbt.getBoolean("EA_ENABLED");
    }
}

