package com.chongyu.aliveandwell.mixin.aliveandwell;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.util.ConfigLock;
import com.chongyu.aliveandwell.util.config.CommonConfig;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Objects;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) throws IOException {
        if(AliveAndWellMain.VERSION.contains("modrinth")){
            player.sendMessage(Text.translatable("危险世界求生 ( Alive Well ) ").append(AliveAndWellMain.VERSION).append("温馨提示：").formatted(Formatting.YELLOW));
            player.sendMessage(Text.translatable("aliveandwell.playermanager.info1").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("aliveandwell.playermanager.info2").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("3.传送插件已加载，可使用的指令有：sethome、home、delhome、tpa、tpahere、back、setback，其中home指令为home 家的名字等。").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("4.每日一遍【罗刹海市】，自省自悟，提神醒脑！").formatted(Formatting.YELLOW));
            player.sendMessage(Text.translatable("5.在关闭存档或服务器前务必用命名牌给村民命名（不可包含villager字样）！").formatted(Formatting.LIGHT_PURPLE));
            player.sendMessage(Text.translatable("aliveandwell.deathcount.lost1").append(Text.of(player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))+"/"+CommonConfig.deathCount)).append(Text.translatable("aliveandwell.deathcount.count")).formatted(Formatting.DARK_PURPLE));
        }else {
            player.sendMessage(Text.translatable("危险世界求生 ( Alive Well ) ").append(AliveAndWellMain.VERSION).append("温馨提示：").formatted(Formatting.YELLOW));
            player.sendMessage(Text.translatable("aliveandwell.playermanager.info1").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("aliveandwell.playermanager.info2").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("3.欢迎为爱发电：https://afdian.net/a/hyChongYu18807977").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("4.传送插件已加载，可使用的指令有：sethome、home、delhome、tpa、tpahere、back、setback，其中home指令为home 家的名字等。").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("5.每日一遍【罗刹海市】，自省自悟，提神醒脑！").formatted(Formatting.YELLOW));
            player.sendMessage(Text.translatable("6.在关闭存档或服务器前务必用命名牌给村民命名（不可包含villager字样）！").formatted(Formatting.LIGHT_PURPLE));
            player.sendMessage(Text.translatable("aliveandwell.deathcount.lost1").append(Text.of(player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))+"/"+CommonConfig.deathCount)).append(Text.translatable("aliveandwell.deathcount.count")).formatted(Formatting.DARK_PURPLE));
        }

        if(player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) > CommonConfig.deathCount){
            player.changeGameMode(GameMode.SPECTATOR);
            if(!player.changeGameMode(GameMode.SPECTATOR)){
                player.changeGameMode(GameMode.SPECTATOR);
            }
        } else {
            if(player.isSpectator()){
                player.changeGameMode(GameMode.SURVIVAL);
                player.removeStatusEffect(StatusEffects.NIGHT_VISION);
            }

            player.changeGameMode(GameMode.SURVIVAL);
            if (!player.changeGameMode(GameMode.SURVIVAL)) {
                player.changeGameMode(GameMode.SURVIVAL);
            }
        }

        if(!this.isOperator(player.getGameProfile())){
            if(!player.getGameProfile().getProperties().get("IsCheat2432").isEmpty()){
                player.networkHandler.onDisconnected(Text.translatable("检测到玩家作弊，禁止进入游戏！"));
            }
        }

        if(player.isCreative() && (!this.isOperator(player.getGameProfile()) || !AliveAndWellMain.canCreative)){
            if(!player.getGameProfile().getProperties().get("IsCheat").isEmpty()){
                player.networkHandler.onDisconnected(Text.translatable("检测到玩家作弊，禁止进入游戏！"));
            }else {
                player.getGameProfile().getProperties().put("IsCheat",new Property("IsCheat", "ischeat"));
                player.networkHandler.onDisconnected(Text.translatable("检测到玩家作弊，禁止进入游戏！"));
            }
        }

        if(!AliveAndWellMain.canCreative){
            try {
                if(!(new ConfigLock().isDefaultConfigConnect())){
                    player.networkHandler.onDisconnected(Text.translatable("检测到玩家作弊，禁止进入游戏！"));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        if(Back.cooldownTimeTooeasy <1800 || Homes.cooldownTimeTooeasy <1800 || Tpa.cooldownTimeTooeasy <1800){
//            if(!player.getGameProfile().getProperties().get("sb_teleport").isEmpty()){
//                player.networkHandler.onDisconnected(Text.translatable("SBBBBBBBBBBBBBBBB！"));
//            }else {
//                player.getGameProfile().getProperties().put("sb_teleport",new Property("sb_teleport", "sb_teleport"));
//                player.networkHandler.onDisconnected(Text.translatable("SBBBBBBBBBBBBBBBB！"));
//            }
//        }

//        ServerCommandSource serverCommandSource = player.server.getCommandSource();
//        //关闭游戏规则作弊选项
//        if(player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)//死亡不掉落true1===================================
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DROWNING_DAMAGE)//溺水伤害false2
//                || !player.getWorld().getGameRules().getBoolean(GameRules.FALL_DAMAGE)//掉落伤害false3
//                || !player.getWorld().getGameRules().getBoolean(GameRules.FREEZE_DAMAGE)//冰冻伤害false4
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)//禁用怪物破坏方块false5==================
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DO_FIRE_TICK)//火焰蔓延false7================================
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)//时间循环false8
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)//天气更替false9
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DO_INSOMNIA)//生成幻翼false10================================
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)//生成灾厄巡逻队false11
//                || !player.getWorld().getGameRules().getBoolean(GameRules.DO_WARDEN_SPAWNING)//生成监守者false12
//        ){
//            GameRules.BooleanRule rule1 = serverCommandSource.getServer().getGameRules().get(GameRules.KEEP_INVENTORY);
//            GameRules.BooleanRule rule2 = serverCommandSource.getServer().getGameRules().get(GameRules.DROWNING_DAMAGE);
//            GameRules.BooleanRule rule3 = serverCommandSource.getServer().getGameRules().get(GameRules.FALL_DAMAGE);
//            GameRules.BooleanRule rule4 = serverCommandSource.getServer().getGameRules().get(GameRules.FREEZE_DAMAGE);
//            GameRules.BooleanRule rule5 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING);
//            GameRules.BooleanRule rule7 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_FIRE_TICK);
//            GameRules.BooleanRule rule8 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
//            GameRules.BooleanRule rule9 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_WEATHER_CYCLE);
//            GameRules.BooleanRule rule10 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_INSOMNIA);
//            GameRules.BooleanRule rule11 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_PATROL_SPAWNING);
//            GameRules.BooleanRule rule12 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_WARDEN_SPAWNING);
//            rule1.set(false,player.server);
//            rule2.set(true,player.server);
//            rule3.set(true,player.server);
//            rule4.set(true,player.server);
//            rule5.set(true,player.server);
//            rule7.set(true,player.server);
//            rule8.set(true,player.server);
//            rule9.set(true,player.server);
//            rule10.set(true,player.server);
//            rule11.set(true,player.server);
//            rule12.set(true,player.server);
//        }

//        if(!AliveAndWellMain.canCreative){
//            try {
//                if(!(new ConfigLock().isDefaultConfig())){
//                    Objects.requireNonNull(player.getServer()).close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

//    @Shadow public abstract boolean isOperator(GameProfile profile);//服务端
    @Overwrite public boolean isOperator(GameProfile profile){return CommonConfig.areCheatAllowed;}//作弊端或不作弊

}