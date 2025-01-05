package aliveandwell.aliveandwell.equipmentlevels.handle;

import aliveandwell.aliveandwell.equipmentlevels.core.Ability;
import aliveandwell.aliveandwell.equipmentlevels.core.Experience;
import aliveandwell.aliveandwell.equipmentlevels.handle.callback.EntityEvents;
import aliveandwell.aliveandwell.equipmentlevels.handle.callback.PlayerEvents;
import aliveandwell.aliveandwell.equipmentlevels.handle.callback.ProjectileImpactCallback;
import aliveandwell.aliveandwell.equipmentlevels.util.EAUtil;
import aliveandwell.aliveandwell.equipmentlevels.util.NBTUtil;
import aliveandwell.aliveandwell.equipmentlevels.util.Static;
import com.google.common.collect.Multimap;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.Random;

public class LivingHurtEventHandler {
    public static Hand bowfriendlyhand;

    public static void onArrowHit() {
        ProjectileImpactCallback.EVENT.register((proj, hit) -> {
            if (!(proj instanceof ArrowEntity arrow)) return false;
            final var owner = arrow.getOwner();
            if (!(owner instanceof PlayerEntity player)) return false;
            if (hit == null) bowfriendlyhand = player.getActiveHand();
            return false;
        });

    }

    public static void onArrowShoot() {
        PlayerEvents.ON_ARROW_LOOSE.register((player, bow, world, charge, hasAmmo) -> {
            bowfriendlyhand = player.getActiveHand();
            return charge;
        });

    }

    public static void onHurt() {
        EntityEvents.ON_LIVING_DAMAGE_CALC.register((world, entity, damageSource, damageAmount) -> {

            if (damageSource.getAttacker() instanceof PlayerEntity player) {
                LivingEntity target = (LivingEntity) entity;
                ItemStack stack;
                if (bowfriendlyhand == null)
                    stack = player.getStackInHand(player.getActiveHand());
                else
                    stack = player.getStackInHand(bowfriendlyhand);

                if (stack != ItemStack.EMPTY && EAUtil.canEnhanceWeapon(stack.getItem())) {
                    NbtCompound nbt = NBTUtil.loadStackNBT(stack);
                    if (nbt.contains("EA_ENABLED")) {
                        updateExperience(nbt, damageAmount/8);
                        updateLevel(player, stack, nbt);
                        float damage1 = useRarity(damageAmount, stack, nbt);
                        return useWeaponAbilities(damage1, player, target, nbt);
                    }
                }
            } else if (entity instanceof PlayerEntity player) {//PLAYER IS GETTING HURT
                Entity target = damageSource.getAttacker();

                ItemStack stack = player.getInventory().armor.get(world.random.nextInt(4));

                if (stack != ItemStack.EMPTY) {
                    if (EAUtil.canEnhanceArmor(stack.getItem())) {
                        NbtCompound nbt = NBTUtil.loadStackNBT(stack);
                        if (nbt.contains("EA_ENABLED")) {
                            if (EAUtil.isDamageSourceAllowed(damageSource)) {
                                if (damageAmount < (player.getMaxHealth() + player.getArmor())) {
                                    updateExperience(nbt, damageAmount + (float) (new Random().nextInt(player.getArmor()+1) + 1) /10);
                                }else {
                                    updateExperience(nbt, (int)(damageAmount/4));
                                }
                                updateLevel(player, stack, nbt);
                            }
                            float damage1 = useRarity(damageAmount, stack, nbt);
                            return useArmorAbilities(damage1, player, target, nbt);
                        }
                    }
                }
            }
            return damageAmount;

        });
    }

    /**
     * 每次目标受伤时调用。用于为造成伤害的武器增加经验
     */
    private static void updateExperience(NbtCompound nbt, float dealedDamage) {
        if (Experience.getLevel(nbt) < Static.MAX_LEVEL) {
            Experience.setExperience(nbt, Experience.getExperience(nbt) + 1 + (int) dealedDamage);
        }
    }

    /**
     * 每次目标受伤时调用。用于造成更多伤害或更少伤害。
     */
    private static float useRarity(float amount, ItemStack stack, NbtCompound nbt) {
//            if (EAUtil.canEnhanceMelee(stack.getItem())) {
//                Multimap<EntityAttribute, EntityAttributeModifier> map = stack.getItem().getAttributeModifiers(stack, EquipmentSlot.MAINHAND);
//                Collection<EntityAttributeModifier> damageCollection = map.get(EntityAttributes.GENERIC_ATTACK_DAMAGE);
//                EntityAttributeModifier damageModifier = (EntityAttributeModifier) damageCollection.toArray()[0];
//                double damage = damageModifier.getValue();
//                return (float) (amount);
//            } else if (EAUtil.canEnhanceRanged(stack.getItem())) {
//                return (float) (amount );
//            } else if (EAUtil.canEnhanceArmor(stack.getItem()))
//                return (float) (amount);

        return amount;
    }

    private static float useWeaponAbilities(float amount, PlayerEntity player, LivingEntity target, NbtCompound nbt) {
        if (target != null) {
            String name = Registry.ENTITY_TYPE.getId(target.getType()).toString();
            boolean b = name.contains("doom:arch_maykr")
                    || name.contains("minecells:conjunctivius")
                    || name.contains("bosses_of_mass_destruction:gauntlet")
                    || name.contains("doom:gladiator")
                    || name.contains("doom:motherdemon")
                    || name.contains("adventurez:stone_golem")
                    || name.contains("bosses_of_mass_destruction:void_blossom")
                    || name.contains("bosses_of_mass_destruction:lich")
//                    || name.contains("minecraft.warden")
                    || (name.contains("minecraft:wither") && !name.contains("skeleton"))
                    || name.contains("bosses_of_mass_destruction:obsidilith")
                    || name.contains("ender_drongon")
                    || name.contains("lich")
                    || name.contains("naga")
                    || name.contains("snow_queen")
                    || name.contains("mored_giant")
                    || name.contains("yeti")
                    || name.contains("minoshroom")
                    || name.contains("soulsweapons:draugr_boss")
                    || name.contains("soulsweapons:returning_knight")
                    || name.contains("soulsweapons:accursed_lord_boss")
                    || name.contains("soulsweapons:moonknight")
                    || name.contains("soulsweapons:chaos_monarch")
                    || name.contains("invade:raider_knight")
                    || name.contains("illagerinvasion:invokert");
//                    || name.contains("minecraft.zombie")//test===============================================
            // active
//            //烈焰：在短时间内燃烧敌人
//            if (Ability.FIRE.hasAbility(nbt) && (int) (Math.random() * Static.firechance) == 0) {
//                double multiplier = (Ability.FIRE.getLevel(nbt) + Ability.FIRE.getLevel(nbt) * 4) / 4D;
//                target.setOnFireFor((int) (multiplier));
//            }

            //击晕：冻晕敌人，在原地冻结一小段时间。
            if (Ability.FROST.hasAbility(nbt) ) {
                int level = Ability.FROST.getLevel(nbt) ;
                if(!b){
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * level,  level));
                }

            }

            //凋零：使敌人凋零
            if (Ability.INNATE.hasAbility(nbt) && (int) (Math.random() * (Static.innatechance-Ability.INNATE.getLevel(nbt)/2)) == 0) {
                double multiplier = (Ability.INNATE.getLevel(nbt) + Ability.INNATE.getLevel(nbt) * 4) / 3D;
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, (int) (20 * multiplier), Ability.INNATE.getLevel(nbt)));
            }

            //致命一击：根据敌人的最大生命值造成额外的伤害，触发几率很小。
            if (Ability.CRITICAL_POINT.hasAbility(nbt) && (int) (Math.random() * (Static.criticalpointchance-Ability.CRITICAL_POINT.getLevel(nbt)/10)) == 0) {
                float multiplier = 0.0F;
                if(!b){
                    if (Ability.CRITICAL_POINT.getLevel(nbt) == 1) multiplier = 0.1F;
                    else if (Ability.CRITICAL_POINT.getLevel(nbt) == 2) multiplier = 0.15F;
                    else if (Ability.CRITICAL_POINT.getLevel(nbt) == 3) multiplier = 0.2F;
                    else if (Ability.CRITICAL_POINT.getLevel(nbt) == 4) multiplier = 0.25F;
                    else if (Ability.CRITICAL_POINT.getLevel(nbt) == 5) multiplier = 0.3F;
                }
                float damage = target.getHealth() * multiplier;
                if(!b){
                    if(damage >= 50){
                        damage = 50;
                    }
                }
                return amount + damage;
            }

            // passive
            //嘲讽：削弱敌人
            if (Ability.ILLUMINATION.hasAbility(nbt) && (int) (Math.random() * (Static.illumchance-Ability.ILLUMINATION.getLevel(nbt)/10)) == 0) {
                if(!b){
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, (20 * 5), Ability.ILLUMINATION.getLevel(nbt)));
                }
            }

            //吸血：将用户所受到的伤害的一部分复原。
            if (Ability.BLOODTHIRST.hasAbility(nbt) && (int) (Math.random() * (Static.bloodthirstchance-Ability.BLOODTHIRST.getLevel(nbt)/10)) == 0) {
                float addition = amount * Ability.BLOODTHIRST.getLevel(nbt) / 50;
                player.setHealth(player.getHealth() + addition);
            }

        }
        return amount;
    }

    private static float useArmorAbilities(float amount, PlayerEntity player, Entity target,  NbtCompound nbt) {
        if (target != null) {
            // active
            //保护：伤害减免
            if (Ability.PROTECT.hasAbility(nbt) ) {
                return DamageUtil.getInflictedDamage(amount, (float) Ability.PROTECT.getLevel(nbt));
            }

            //坚定：亡灵伤害减免
            if (Ability.FIRM.hasAbility(nbt) && target instanceof Monster) {
                return amount - amount/(10-Ability.FIRM.getLevel(nbt)) ;
            }

            //毒刺：在短时间内使敌人中毒
            if (Ability.TOXIC.hasAbility(nbt) && (int) (Math.random() * Static.toxicchance) == 0 && target instanceof LivingEntity realTarget) {
                double multiplier = (Ability.TOXIC.getLevel(nbt) + Ability.TOXIC.getLevel(nbt) * 4) / 4D;
                realTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, (int) (20 * multiplier), Ability.TOXIC.getLevel(nbt)));
            }

            // passive
            //振奋：生命值低时，玩家将获得抗性
            if (Ability.BEASTIAL.hasAbility(nbt) ) {
                if (player.getHealth() <= (player.getMaxHealth() * 0.2F))
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * (3+Ability.BEASTIAL.getLevel(nbt)), 0));
            }

            // 坚毅不催：有几率抵消所有伤害
            if (Ability.HARDENED.hasAbility(nbt) && (int) (Math.random() * (Static.hardenedchance-Ability.HARDENED.getLevel(nbt)/2)) == 0) {
                return 0;
            }
        }
        return amount;
    }

    /**
     * 每次目标受伤时调用。用于检查武器是否应该升级。
     */
    private static void updateLevel(PlayerEntity player, ItemStack stack, NbtCompound nbt) {
        int level = Experience.getNextLevel(player, stack, nbt, Experience.getLevel(nbt), Experience.getExperience(nbt));
        Experience.setLevel(nbt, level);
        NBTUtil.saveStackNBT(stack, nbt);
    }
}