package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import aliveandwell.aliveandwell.accessor.IEntityNbt;
import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.init.ConfigInit;
import net.levelz.init.CriteriaInit;
import net.levelz.network.PlayerStatsServerPacket;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {
    @Shadow private int fireTicks = -this.getBurningDuration();
    @Shadow public World world;

    @Overwrite
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if(((IEntityNbt)lightning).entityNbt$contains("player_lighting_aliveandwell")){
            if((Entity)(Object)this instanceof ItemEntity || (Entity)(Object)this instanceof PlayerEntity || (Entity)(Object)this instanceof ExperienceOrbEntity) {
                this.setFireTicks(this.fireTicks + 1);
                if (this.fireTicks == 0) {
                    this.setOnFireFor(0);
                }
                this.damage(DamageSource.LIGHTNING_BOLT, 0.0f);
            }else {
                this.setFireTicks(this.fireTicks + 1);
                if (this.fireTicks == 0) {
                    this.setOnFireFor(8);
                }
                this.damage(DamageSource.LIGHTNING_BOLT, 25.0f);
            }
        }else {
            this.setFireTicks(this.fireTicks + 1);
            if (this.fireTicks == 0) {
                this.setOnFireFor(8);
            }
            this.damage(DamageSource.LIGHTNING_BOLT, 15.0f);
        }
    }
//    @Overwrite
//    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
//        if((Entity)(Object)this instanceof ServerPlayerEntity player) {
//            if(player.getStackInHand(player.getActiveHand()).getItem() == ItemInit.ANCIENT_SWORD){
//                this.setFireTicks(this.fireTicks + 1);
//                if (this.fireTicks == 0) {
//                    this.setOnFireFor(0);
//                }
//                this.damage(DamageSource.LIGHTNING_BOLT, 0.0f);
//            }
//        }
//
//        List<PlayerEntity> players = this.getWorld().<PlayerEntity>getEntitiesByClass(
//                PlayerEntity.class,
//                new Box(this.getBlockPos().getX()-8,this.getBlockPos().getY()-8,this.getBlockPos().getZ()-8,
//                        this.getBlockPos().getX()+8,this.getBlockPos().getY()+8,this.getBlockPos().getZ()+8),
//                (e) -> e instanceof PlayerEntity
//        );
//
//        boolean hasPlayer = false;
//
//        for (PlayerEntity player : players){
//            if (player != null && player.getStackInHand(player.getActiveHand()).getItem() == ItemInit.ANCIENT_SWORD) {
//                hasPlayer=true;
//                break;
//            }
//        }
//
//
//        if (hasPlayer) {
//            if((Entity)(Object)this instanceof ItemEntity || (Entity)(Object)this instanceof PlayerEntity || (Entity)(Object)this instanceof ExperienceOrbEntity){
//                this.setFireTicks(this.fireTicks + 1);
//                if (this.fireTicks == 0) {
//                    this.setOnFireFor(0);
//                }
//                this.damage(DamageSource.LIGHTNING_BOLT, 0.0f);
//            }else {
//                if((Entity)(Object)this instanceof LivingEntity entity){
//                    this.setFireTicks(this.fireTicks + 1);
//                    if (this.fireTicks == 0) {
//                        this.setOnFireFor(8);
//                    }
//
//                    if(entity.getHealth() <= 40.0f){
//                        this.damage(DamageSource.LIGHTNING_BOLT, 0.0f);//防止抢夺不生效
//                    }else {
//                        this.damage(DamageSource.LIGHTNING_BOLT, 40.0f);
//                    }
//                }
//            }
//        } else {
//            this.setFireTicks(this.fireTicks + 1);
//            if (this.fireTicks == 0) {
//                this.setOnFireFor(8);
//            }
//            this.damage(DamageSource.LIGHTNING_BOLT, 25.0f);
//        }
//    }

    @Shadow
    public abstract void setOnFireFor(int seconds);

    @Shadow
    public void setFireTicks(int fireTicks) {}

    @Shadow
    protected int getBurningDuration() {
        return 1;
    }

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow
    public World getWorld() {
        return this.world;
    }

//    @Inject(method = "moveToWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;removeFromDimension()V", shift = At.Shift.AFTER))
//    public void moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
//        Object entityPlayerAlive = ((Entity)(Object)this).getType().create(destination);
//        Object entityPlayerAlive1 = cir.getReturnValue();
//        if(entityPlayerAlive != null){
//            if(entityPlayerAlive instanceof ServerPlayerEntity serverPlayerEntity && entityPlayerAlive1 instanceof ServerPlayerEntity serverPlayerEntity1){
//                if (!ConfigInit.CONFIG.hardMode) {
//                    PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess)serverPlayerEntity).getPlayerStatsManager();
//                    PlayerStatsManager serverPlayerStatsManager = ((PlayerStatsManagerAccess)serverPlayerEntity).getPlayerStatsManager();
//                    PlayerStatsServerPacket.writeS2CSkillPacket(playerStatsManager, serverPlayerEntity);
//                    serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(serverPlayerEntity.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH));
//                    serverPlayerEntity.setHealth(serverPlayerEntity.getMaxHealth());
//                    serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(serverPlayerEntity.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
//                    serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(serverPlayerEntity.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
//                    serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(serverPlayerEntity.getAttributeBaseValue(EntityAttributes.GENERIC_ARMOR));
//                    serverPlayerEntity.getAttributeInstance(EntityAttributes.GENERIC_LUCK).setBaseValue(serverPlayerEntity.getAttributeBaseValue(EntityAttributes.GENERIC_LUCK));
//                    PlayerStatsServerPacket.writeS2CStrengthPacket(serverPlayerEntity);
//                    boolean keepInventory = destination.getGameRules().getBoolean(GameRules.KEEP_INVENTORY);
//                    serverPlayerStatsManager.setLevelProgress(keepInventory ? playerStatsManager.getLevelProgress() : (ConfigInit.CONFIG.resetCurrentXP ? 0.0F : playerStatsManager.getLevelProgress()));
//                    serverPlayerStatsManager.setTotalLevelExperience(keepInventory ? playerStatsManager.getTotalLevelExperience() : (ConfigInit.CONFIG.resetCurrentXP ? 0 : playerStatsManager.getTotalLevelExperience()));
//                    serverPlayerStatsManager.setOverallLevel(playerStatsManager.getOverallLevel());
//                    serverPlayerStatsManager.setSkillPoints(playerStatsManager.getSkillPoints());
//                    Skill[] var14 = Skill.values();
//                    int var15 = var14.length;
//
//                    for(int var16 = 0; var16 < var15; ++var16) {
//                        Skill skill = var14[var16];
//                        serverPlayerStatsManager.setSkillLevel(skill, playerStatsManager.getSkillLevel(skill));
//                    }
//                }
//
//                if (ConfigInit.CONFIG.hardMode) {
//                    serverPlayerEntity.server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, new ServerPlayerEntity[]{serverPlayerEntity}));
//                    serverPlayerEntity.getScoreboard().forEachScore(CriteriaInit.LEVELZ, serverPlayerEntity.getEntityName(), ScoreboardPlayerScore::clearScore);
//                }
//            }
//        }
//    }
}
