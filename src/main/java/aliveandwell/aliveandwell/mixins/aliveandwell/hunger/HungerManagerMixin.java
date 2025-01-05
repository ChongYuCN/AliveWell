package aliveandwell.aliveandwell.mixins.aliveandwell.hunger;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
    @Unique
    private int saturationDamageTick;

    @Shadow private int foodLevel = 20;
    @Shadow private float saturationLevel = 5.0f;
    @Shadow private float exhaustion;
    @Shadow private int foodTickTimer;
    @Shadow private int prevFoodLevel = 20;

    @Inject(at=@At("HEAD"),method="update",cancellable = true)
    public void update(PlayerEntity player, CallbackInfo ci) {
        int max_food_level = Math.min(20, (player.experienceLevel / 5 + 3) * 2);//+++
        boolean bl;
        Difficulty difficulty = player.getWorld().getDifficulty();
        this.prevFoodLevel = this.foodLevel;

        //+++
        if (this.saturationLevel > 4) {
            this.saturationLevel = 4;
        }

        if (this.exhaustion > 4.0f) {
            this.exhaustion -= 4.0f;
            if (this.saturationLevel > 0.0f) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0f, 0.0f);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(foodLevel - 1, 0);
            }
        }

        //this.foodLevel >= 20改：this.foodLevel >= getHungerCount(player):当玩家经验等级低时有问题：消耗都很大。
        if (difficulty != Difficulty.PEACEFUL && this.foodLevel > 0) {//foodLevel >= 0
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {//每4秒增加消耗exhaustion
//                player.heal(f / 6.0f);//移除满饱食度治疗效果
                this.addExhaustion(0.3f);
                this.foodTickTimer = 0;
            }
        } else if (this.foodLevel <= 0 ) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                //++++
                if(this.saturationLevel - 0.5f <= 0){
                    this.saturationLevel = 0;
                }else {
                    this.saturationLevel -= 0.5f;
                }
                this.foodTickTimer = 0;
            }

            if (player.getHealth() > 0.0f && this.saturationLevel <= 0 && difficulty != Difficulty.PEACEFUL) {
                ++this.saturationDamageTick;
                if(saturationDamageTick >= 80){
                    player.damage(DamageSource.STARVE, 1.0f);//饱食度为0时收到伤害直至死亡player.getHealth() > 1.0f 更 player.getHealth() > 0.0f
                    this.saturationDamageTick = 0;
                }
            }else {
                this.saturationDamageTick = 0;
            }

        } else {
            this.foodTickTimer = 0;
        }

        foodLevel = (int)Math.max(Math.min(foodLevel, max_food_level), 0);//++
        saturationLevel = (float)Math.max(Math.min(saturationLevel, 8), 0);//++

        ci.cancel();
    }

    @Overwrite
    public void addExhaustion(float exhaustion) {
        this.exhaustion += exhaustion * 1.5f;
        if (this.exhaustion > 40.0f) this.exhaustion = 40.0f;
        if (this.exhaustion < 0) this.exhaustion = 0;
    }

    @Overwrite
    public void add(int food, float saturationModifier) {
        this.foodLevel = Math.min(food + this.foodLevel, 20);
        this.saturationLevel = Math.min(this.saturationLevel + (float)(food == 0 ? 1 : food)* saturationModifier * 2.0f, 8.0f);
    }

    @Unique
    private int getHungerCount(PlayerEntity player) {
        int count = 6 + 2*(int)Math.floor(player.experienceLevel / 5.0);
        if (count > 20) count = 20;
        return count;
    }
}
