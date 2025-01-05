package aliveandwell.aliveandwell.equipmentlevels.core;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public enum Ability {
    // 武器
    //击晕
    FROST("weapon", "passive", true, Formatting.AQUA, 0x55FFFF, 1, 5),
    //凋零
    INNATE("weapon", "passive", true, Formatting.DARK_RED, 0xAA0000, 2, 5),
    //致命一击
    CRITICAL_POINT("weapon", "passive", true, Formatting.DARK_GRAY, 0x555555, 3, 5),
    // passive
    //嘲讽
    ILLUMINATION("weapon", "passive", true, Formatting.YELLOW, 0xFFFF55, 2, 5),
    //吸血
    BLOODTHIRST("weapon", "passive", true, Formatting.DARK_PURPLE, 0xAA00AA, 3, 5),

    // 盔甲
    //保护
    PROTECT("armor", "active", true, Formatting.GREEN, 0x55FF55, 2, 5),
    //坚定
    FIRM("armor", "active", true, Formatting.BLUE, 0xFF55FF, 2, 5),
    //毒刺
    TOXIC("armor", "active", true, Formatting.DARK_GREEN, 0x00AA00, 2, 5),
    // passive
    //振奋
    BEASTIAL("armor", "passive", true, Formatting.DARK_RED, 0xAA0000, 2, 5),
    // 坚毅不催
    HARDENED("armor", "passive", true, Formatting.GRAY, 0xAAAAAA, 3, 5);

    public static final ArrayList<Ability> WEAPON_ABILITIES = new ArrayList<Ability>();
    public static final ArrayList<Ability> ARMOR_ABILITIES = new ArrayList<Ability>();
    public static final ArrayList<Ability> ALL_ABILITIES = new ArrayList<Ability>();
    public static int WEAPON_ABILITIES_COUNT = 0;
    public static int ARMOR_ABILITIES_COUNT = 0;

    static {
        for (int i = 0; i < Ability.values().length; i++) {
            Ability.ALL_ABILITIES.add(Ability.values()[i]);
            if (Ability.values()[i].getCategory().equals("weapon") && Ability.values()[i].enabled) {
                Ability.WEAPON_ABILITIES.add(Ability.values()[i]);
                Ability.WEAPON_ABILITIES_COUNT++;
            } else if (Ability.values()[i].getCategory().equals("armor") && Ability.values()[i].enabled) {
                Ability.ARMOR_ABILITIES.add(Ability.values()[i]);
                Ability.ARMOR_ABILITIES_COUNT++;
            }
        }
    }

    private final String category;
    private final String type;
    private final boolean enabled;
    private final String color;
    private final int hex;
    private final int tier;
    private final int maxlevel;

    Ability(String category, String type, boolean enabled, Object color, int hex, int tier, int maxlevel) {
        this.category = category;
        this.type = type;
        this.enabled = enabled;
        this.color = color.toString();
        this.hex = hex;
        this.tier = tier;
        this.maxlevel = maxlevel;
    }

    public boolean hasAbility(NbtCompound nbt) {
        return nbt != null && nbt.getInt(toString()) > 0;
    }

    public void addAbility(NbtCompound nbt) {
        nbt.putInt(toString(), 1);
        if (nbt.contains("ABILITIES"))
            nbt.putInt("ABILITIES", nbt.getInt("ABILITIES") + 1);
        else
            nbt.putInt("ABILITIES", 1);
    }

    public void removeAbility(NbtCompound nbt) {
        nbt.remove(toString());
        if (nbt.contains("ABILITIES"))
            if (nbt.getInt("ABILITIES") > 0)
                nbt.putInt("ABILITIES", nbt.getInt("ABILITIES") - 1);
    }

    public boolean hasEnoughExp(PlayerEntity player, NbtCompound nbt) {
        return getExpLevel(nbt) <= player.experienceLevel || player.isCreative();
    }

    public int getExpLevel(NbtCompound nbt) {
        int requiredExpLevel = 0;
        if (nbt.contains("ABILITIES"))
            requiredExpLevel = (getTier() + getMaxLevel()) * (nbt.getInt("ABILITIES") + 1) - 1;
        else
            requiredExpLevel = getTier() + getMaxLevel();
        return requiredExpLevel;
    }

    public void setLevel(NbtCompound nbt, int level) {
        nbt.putInt(toString(), level);
    }

    public int getLevel(NbtCompound nbt) {
        if (nbt != null) return nbt.getInt(toString());
        else return 0;
    }

    public boolean canUpgradeLevel(NbtCompound nbt) {
        if (getLevel(nbt) < this.maxlevel)
            return true;
        else
            return false;
    }

    public int getTier() {
        return tier;
    }

    public int getMaxLevel() {
        return maxlevel;
    }

    public String getColor() {
        return color;
    }

    public int getHex() {
        return hex;
    }

    public String getName() {
        return this.toString();
    }


    public String getName(NbtCompound nbt) {
        if (getLevel(nbt) == 2)
            return Text.translatable("enhancedarmaments.ability." + this.toString()).getString()  + " Ⅱ";
        else if (getLevel(nbt) == 3)
            return Text.translatable("enhancedarmaments.ability." + this.toString()).getString()  +" Ⅲ";
        else if (getLevel(nbt) == 4)
            return Text.translatable("enhancedarmaments.ability." + this.toString()).getString() +" Ⅳ";
        else if (getLevel(nbt) == 5)
            return Text.translatable("enhancedarmaments.ability." + this.toString()).getString() +" Ⅴ";
        else
            return Text.translatable("enhancedarmaments.ability." + this.toString()).getString() + " Ⅰ";
    }

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return Text.translatable("enhancedarmaments.ability.type." + type.toString()).getString();
    }

    public String getCategory() {
        return category;
    }
}
