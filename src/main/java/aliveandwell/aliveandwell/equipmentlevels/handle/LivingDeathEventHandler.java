package aliveandwell.aliveandwell.equipmentlevels.handle;

import aliveandwell.aliveandwell.equipmentlevels.core.Experience;
import aliveandwell.aliveandwell.equipmentlevels.handle.callback.EntityEvents;
import aliveandwell.aliveandwell.equipmentlevels.util.EAUtil;
import aliveandwell.aliveandwell.equipmentlevels.util.NBTUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

/**
 * 使用有效武器杀死目标时更新武器信息。用于更新经验、等级、能力等
 */
public class LivingDeathEventHandler {

    public static void init() {
        EntityEvents.LIVING_ENTITY_DEATH.register((world, entity, source) -> {
            if (source.getSource() instanceof PlayerEntity player) {

                ItemStack stack;
                if (LivingHurtEventHandler.bowfriendlyhand == null)
                    stack = player.getStackInHand(player.getActiveHand());
                else
                    stack = player.getStackInHand(LivingHurtEventHandler.bowfriendlyhand);

                if (stack != ItemStack.EMPTY && EAUtil.canEnhanceMelee(stack.getItem())) {
                    NbtCompound nbt = NBTUtil.loadStackNBT(stack);
                    if (nbt.contains("EA_ENABLED")) {
//                        if (Ability.ETHEREAL.hasAbility(nbt)) {
//                            player.getInventory().getStack(player.getInventory().selectedSlot).setDamage((player.getInventory().getStack(player.getInventory().selectedSlot).getDamage() - (Ability.ETHEREAL.getLevel(nbt) * 2)));
//                        }
//                        addBonusExperience(player, stack, entity, nbt);
                        updateLevel(player, stack, nbt);
                        NBTUtil.saveStackNBT(stack, nbt);
                    }
                } else if (stack != ItemStack.EMPTY && EAUtil.canEnhanceRanged(stack.getItem())) {
                    NbtCompound nbt = NBTUtil.loadStackNBT(stack);
                    if (nbt.contains("EA_ENABLED")) {
//                        if (Ability.ETHEREAL.hasAbility(nbt)) {
//                            player.getInventory().getStack(player.getInventory().selectedSlot).setDamage((player.getInventory().getStack(player.getInventory().selectedSlot).getDamage() - (Ability.ETHEREAL.getLevel(nbt) * 2 + 1)));
//                        }
//                        addBonusExperience(player, stack, entity, nbt);
                        updateLevel(player, stack, nbt);
                    }
                }

            } else if (source.getSource() instanceof ArrowEntity) {

                if (source.getAttacker() instanceof PlayerEntity player && source.getAttacker() != null) {
                    ItemStack stack = player.getInventory().getStack(player.getInventory().selectedSlot);

                    if (stack != ItemStack.EMPTY) {
                        NbtCompound nbt = NBTUtil.loadStackNBT(stack);
//                        addBonusExperience(player, stack, entity, nbt);
                        updateLevel(player, stack, nbt);

                    }
                }
            }
        });

    }

    /**
     * 每次目标死亡时调用。根据目标的生命值增加额外经验。
     *
     */
//    private static void addBonusExperience(PlayerEntity player, ItemStack stack, Entity target, NbtCompound nbt) {
//        if (Experience.getLevel(nbt) < Static.MAX_LEVEL) {
//            if (target != null) {
//                int bonusExperience = 0;
//                LivingEntity entity = (LivingEntity) target;
//
//                if (entity.getMaxHealth() < 10) bonusExperience = 3;
//                else if (entity.getMaxHealth() > 9 && entity.getMaxHealth() < 20) bonusExperience = 6;
//                else if (entity.getMaxHealth() > 19 && entity.getMaxHealth() < 50) bonusExperience = 15;
//                else if (entity.getMaxHealth() > 49 && entity.getMaxHealth() < 100) bonusExperience = 50;
//                else if (entity.getMaxHealth() > 99) bonusExperience = 70;
//                Experience.setExperience(nbt, Experience.getExperience(nbt) + bonusExperience);
//
////                player.sendMessage(Text.literal(stack.getName().getString() + Formatting.GRAY + " " +
////                        Text.translatable("enhancedarmaments.misc.exp.get").getString() + " " +
////                        Formatting.BLUE + "" + bonusExperience + Formatting.GRAY + "!"));
//
//
//            }
//        }
//    }

    /**
     * 每次有目标时调用。用于更新武器的等级。
     *
     * @param player
     * @param stack
     * @param nbt
     */
    private static void updateLevel(PlayerEntity player, ItemStack stack, NbtCompound nbt) {
        int level = Experience.getNextLevel(player, stack, nbt, Experience.getLevel(nbt), Experience.getExperience(nbt));
        Experience.setLevel(nbt, level);
    }
}
