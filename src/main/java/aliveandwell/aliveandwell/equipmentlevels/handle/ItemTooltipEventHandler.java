package aliveandwell.aliveandwell.equipmentlevels.handle;

import aliveandwell.aliveandwell.equipmentlevels.core.Ability;
import aliveandwell.aliveandwell.equipmentlevels.core.Experience;
import aliveandwell.aliveandwell.equipmentlevels.handle.callback.ItemTooltipCallback;
import aliveandwell.aliveandwell.equipmentlevels.util.EAUtil;
import aliveandwell.aliveandwell.equipmentlevels.util.NBTUtil;
import aliveandwell.aliveandwell.equipmentlevels.util.Static;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.List;

/**
 * 将鼠标悬停时显示有关武器的信息。
 */
public class ItemTooltipEventHandler {
    /**
     * 每当需要显示工具的提示时调用。
     */
    public static void addInformation() {
        ItemTooltipCallback.LIVING_TICK.register((itemStack, entityPlayer, list, flags) -> {
            Item item = itemStack.getItem();

            if (EAUtil.canEnhance(item)) {
                NbtCompound nbt = NBTUtil.loadStackNBT(itemStack);

                if (Experience.isEnabled(nbt)) {
//                    Rarity rarity = Rarity.getRarity(nbt);
                    int level = Experience.getLevel(nbt);
                    int experience = Experience.getExperience(nbt);
                    int maxExperience = Experience.getMaxLevelExp(level);

//                    changeTooltips(list, itemStack, rarity);

                    // add tooltips

                    // level
                    if (level >= Static.MAX_LEVEL)
                        list.add(Text.literal(I18n.translate("enhancedarmaments.misc.level") + ": " + Formatting.RED + I18n.translate("enhancedarmaments.misc.max")));
                    else
                        list.add(Text.literal(I18n.translate("enhancedarmaments.misc.level") + ": " + Formatting.WHITE + (level-1)));

                    // experience
                    if (level >= Static.MAX_LEVEL)
                        list.add(Text.literal(I18n.translate("enhancedarmaments.misc.experience") + ": " + I18n.translate("enhancedarmaments.misc.max")));
                    else
                        list.add(Text.literal(I18n.translate("enhancedarmaments.misc.experience") + ": " + experience + " / " + maxExperience));

                    // durability
//                    list.add(Text.literal(I18n.translate("enhancedarmaments.misc.durability") + ": " + (itemStack.getMaxDamage() - itemStack.getDamage()) + " / " + itemStack.getMaxDamage()));

                    // abilities
//                    list.add(Text.literal(""));
                    if (Screen.hasShiftDown()) {
                        list.add(Text.literal(Formatting.GOLD + "" + Formatting.ITALIC + Formatting.LIGHT_PURPLE + I18n.translate("enhancedarmaments.misc.abilities")));
//                        list.add(Text.literal(""));

                        if (EAUtil.canEnhanceWeapon(item)) {
                            for (Ability ability : Ability.WEAPON_ABILITIES) {
                                if (ability.hasAbility(nbt)) {
                                    list.add(Text.translatable("-" + ability.getColor() + ability.getName(nbt)));
                                }
                            }
                        } else if (EAUtil.canEnhanceArmor(item)) {
                            for (Ability ability : Ability.ARMOR_ABILITIES) {
                                if (ability.hasAbility(nbt)) {
                                    list.add(Text.translatable("-" + ability.getColor() + ability.getName(nbt) ));
                                }
                            }
                        }
                    }
                    else list.add(Text.literal(Formatting.GOLD + "" + Formatting.ITALIC + Formatting.LIGHT_PURPLE+ I18n.translate("enhancedarmaments.misc.abilities.shift")));
                }
            }
        });

    }

//    private static void changeTooltips(List<Text> tooltip, ItemStack stack, Rarity rarity) {
//        // rarity after the name
//        tooltip.set(0, Text.literal(stack.getName().getString() + rarity.getColor() + " (" + Formatting.ITALIC + I18n.translate("enhancedarmaments.rarity." + rarity.getName()) + ")"));
//
//        if (EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.mainHand")) && !(stack.getItem() instanceof BowItem)) {
//            Multimap<EntityAttribute, EntityAttributeModifier> map = stack.getItem().getAttributeModifiers(stack, EquipmentSlot.MAINHAND);
//            Collection<EntityAttributeModifier> damageCollection = map.get(EntityAttributes.GENERIC_ATTACK_DAMAGE);
//            EntityAttributeModifier damageModifier = (EntityAttributeModifier) damageCollection.toArray()[0];
//            double damage = ((damageModifier.getValue() + 1) * rarity.getEffect()) + damageModifier.getValue() + 1;
//            String d = String.format("%.1f", damage);
//
//            if (rarity.getEffect() != 0)
//                tooltip.set(EAUtil.lineContainsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.mainHand")) + 2, Text.literal(rarity.getColor() + " " + d + Formatting.GRAY + " " + I18n.translate("enhancedarmaments.misc.tooltip.attackdamage")));
//        }
//
//        if (EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.head")) || EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.body")) || EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.legs")) || EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.feet"))) {
//            String p = String.format("%.1f", 100 - (100 / (1.0F + (rarity.getEffect() / 5F))));
//            float percentage = Float.parseFloat(p);
//            int line = 2;
//            if (EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.head")))
//                line = EAUtil.lineContainsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.head"));
//            if (EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.body")))
//                line = EAUtil.lineContainsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.body"));
//            if (EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.legs")))
//                line = EAUtil.lineContainsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.legs"));
//            if (EAUtil.containsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.feet")))
//                line = EAUtil.lineContainsString(tooltip, I18n.translate("enhancedarmaments.misc.pos.feet"));
//            if (percentage != 0)
//                tooltip.add(line + 1, Text.literal(" " + Formatting.BLUE + "+" + rarity.getColor() + percentage + Formatting.BLUE + "% " + I18n.translate("enhancedarmaments.misc.rarity.armorreduction")));
//        }
//
//        if (EAUtil.canEnhanceRanged(stack.getItem()) && rarity.getEffect() != 0) {
//            String b = String.format("%.1f", rarity.getEffect() / 3 * 100);
//            tooltip.add(1, Text.literal(I18n.translate("enhancedarmaments.misc.rarity.arrowpercentage") + " " + rarity.getColor() + "+" + b + "%"));
//        }
//    }
}
