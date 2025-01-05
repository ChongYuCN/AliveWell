package aliveandwell.aliveandwell.mixins.aliveandwell;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.accessor.SPlayerAccessorNbt;
import aliveandwell.aliveandwell.data.SaveDatas;
import aliveandwell.aliveandwell.util.ConfigLock;
import aliveandwell.aliveandwell.config.CommonConfig;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.logging.LogUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import net.minecraft.world.WorldSaveHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Objects;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Shadow @Final private MinecraftServer server;
    @Mutable
    @Final
    @Shadow  private final WorldSaveHandler saveHandler;
    @Shadow  private static final Logger LOGGER = LogUtils.getLogger();

    protected PlayerManagerMixin(WorldSaveHandler saveHandler) {
        this.saveHandler = saveHandler;
    }

    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ca) throws IllegalAccessException {
        if(AliveAndWellMain.VERSION.contains("modrinth")){
            player.sendMessage(Text.translatable("危险世界求生 ( Alive Well ) ").append(AliveAndWellMain.VERSION).append("温馨提示：").formatted(Formatting.YELLOW));
            player.sendMessage(Text.translatable("->每日一遍【罗刹海市】，自省自悟，提神醒脑！").formatted(Formatting.YELLOW));
            player.sendMessage(Text.translatable("aliveandwell.deathcount.lost1").append(Text.of(player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS))+"/"+CommonConfig.deathCount)).append(Text.translatable("aliveandwell.deathcount.count")).formatted(Formatting.DARK_PURPLE));
        }else {
            player.sendMessage(Text.translatable("危险世界求生 ( Alive Well ) ").append(AliveAndWellMain.VERSION).append("温馨提示：").formatted(Formatting.YELLOW));
            player.sendMessage(Text.translatable("1.欢迎为爱发电：https://afdian.net/a/hyChongYu18807977").formatted(Formatting.GOLD));
            player.sendMessage(Text.translatable("2.每日一遍【罗刹海市】，自省自悟，提神醒脑！").formatted(Formatting.YELLOW));
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

            //反作弊：玩家死亡次数
            SaveDatas serverState = SaveDatas.getServerState(Objects.requireNonNull(player.getServer()));
            if(serverState.randomPlayerMap.containsKey(player.getUuid())){
                int death = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));
                if(death != serverState.randomPlayerMap.get(player.getUuid())){
                    player.getGameProfile().getProperties().put("IsCheat",new Property("IsCheat", "ischeat"));
                    player.networkHandler.onDisconnected(Text.translatable("检测到玩家作弊，禁止进入游戏！"));
                }
            }
            //反作弊：玩家存档时间
            if(serverState.randomPlayerTimeMap.containsKey(player.getUuid())){
                long gameTimePlayer = ((SPlayerAccessorNbt) (Object)player).splayerNbt$getLong("aliveandewell_"+player.getUuidAsString());
                long map = serverState.randomPlayerTimeMap.get(player.getUuid());
                if(gameTimePlayer != map){
                    //旧：数据-1714900917L
                    //新：数据-1714900955L ，读取map：1714900716 ，读取player：1714900716(单机档读取的是level里的数据：即无效）
                    //只在服务端起作用
                    player.getGameProfile().getProperties().put("IsCheat",new Property("IsCheat", "ischeat"));
//                    connection.disconnect(Text.translatable("检测到玩家作弊，禁止进入游戏！"));
                    player.networkHandler.onDisconnected(Text.translatable("检测到玩家作弊，禁止进入游戏！"));
                }
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

    }

//        @Shadow public abstract boolean isOperator(GameProfile profile);//服务端
    @Overwrite public boolean isOperator(GameProfile profile){return CommonConfig.areCheatAllowed;}

}