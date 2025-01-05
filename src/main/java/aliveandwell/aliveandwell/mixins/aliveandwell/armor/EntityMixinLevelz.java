package aliveandwell.aliveandwell.mixins.aliveandwell.armor;

import aliveandwell.aliveandwell.util.PlayerEquipUtil;
import net.levelz.data.LevelLists;
import net.levelz.init.ConfigInit;
import net.levelz.stats.PlayerStatsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public abstract class EntityMixinLevelz
{
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    protected void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        if(source.getAttacker() instanceof PlayerEntity player){
            //levelz：等级不够无法造成伤害
            Item item = player.getMainHandStack().getItem();
            if (!item.equals(Items.AIR)) {
                ArrayList<Object> levelList = LevelLists.customItemList;
                if (!levelList.isEmpty() && levelList.contains(Registry.ITEM.getId(item).toString())) {
                    if (!PlayerStatsManager.playerLevelisHighEnough(player, levelList, Registry.ITEM.getId(item).toString(), true)) {
                        ci.cancel();
                    }
                }  else if (item instanceof ToolItem) {
                    levelList = null;
                    if (item instanceof SwordItem) {
                        levelList = LevelLists.swordList;
                    }else if (item instanceof AxeItem) {
                        if (ConfigInit.CONFIG.bindAxeDamageToSwordRestriction) {
                            levelList = LevelLists.swordList;
                        } else {
                            levelList = LevelLists.axeList;
                        }
                    } else if (item instanceof HoeItem) {
                        levelList = LevelLists.hoeList;
                    } else if (item instanceof PickaxeItem || item instanceof ShovelItem) {
                        levelList = LevelLists.toolList;
                    }

                    if (levelList != null && !PlayerStatsManager.playerLevelisHighEnough(player, levelList, ((ToolItem)item).getMaterial().toString().toLowerCase(), true)) {
                        ci.cancel();
                    }

                }
            }
        }
    }
}
