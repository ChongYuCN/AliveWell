package com.chongyu.aliveandwell.hometpaback;

import com.chongyu.aliveandwell.hometpaback.util.IStoreHome;
import com.chongyu.aliveandwell.hometpaback.util.TeleportUtils;
import com.chongyu.aliveandwell.util.config.CommonConfig;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;

public class Back {
    private final HashMap<UUID, Long> recentRequests = new HashMap<>();
    public static int cooldownTimeTooeasy = CommonConfig.tptime;
    private int xpCostTime=CommonConfig.xptime;

    public  void register(){

        Predicate<ServerCommandSource> isPlayer = source -> {
            try {
                source.getPlayerOrThrow();
                return true;
            } catch(CommandSyntaxException e) {
                return false;
            }
        };
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            dispatcher.register(CommandManager.literal("back")
                    .requires(isPlayer).executes(command -> backInit(command, null))
            );
            dispatcher.register(CommandManager.literal("setback")
                    .requires(isPlayer)
                    .executes(ctx -> backSet(ctx, null))
            );
        });

    }

    private boolean dfafsfsa(ServerPlayerEntity tFrom) {
        if (recentRequests.containsKey(tFrom.getUuid())) {
            long diff = Instant.now().getEpochSecond() - recentRequests.get(tFrom.getUuid());
            String nima1 = "你的脸皮真是你身上最神奇的地方，可大可小，可薄可厚，甚至可有可无！";
            String nima2 = "没爹没娘的傻逼真他妈可怜！";
            String nima3 = "恬不知耻说的就是你这个孤儿！";
            String nima4 = "去你妈的傻逼玩意儿，傻逼！！！！！！！！！！！！！！！";
            if (diff < cooldownTimeTooeasy) {
                tFrom.sendMessage(Text.translatable("aliveandwell.hometpaback.cooldowntime").append(Text.translatable(String.valueOf(cooldownTimeTooeasy - diff)).append(Text.translatable("aliveandwell.hometpaback.second"))).formatted(Formatting.RED), false);
                return true;
            }

        }
        return false;
    }

    int backInit(CommandContext<ServerCommandSource> ctx, Object o) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
        Pair<Vec3d, RegistryKey<World>> back = ((IStoreHome) player).getBack();
        if(back.getLeft() != null && back.getRight() != null) {
            if(xpCostTime<0){
                xpCostTime=0;
            }

            int experience1 = ((int)(player.getWorld().getLevelProperties().getTimeOfDay()/ 24000L)+1)*xpCostTime;
            if (experience1 >= 1000 ){
                experience1 = 1000;
            }
            if(player.totalExperience < experience1){
                player.sendMessage(Text.translatable("aliveandwell.hometpaback.xpno").append(Text.translatable("aliveandwell.hometpaback.xpneed")).append(Text.literal(String.valueOf(experience1))).append(Text.translatable("aliveandwell.hometpaback.xpcount")).formatted(Formatting.RED),false);
                return 0;
            }
            String st1 = player.getWorld().getRegistryKey().toString().substring(0,player.getWorld().getRegistryKey().toString().indexOf(":"));
            String st2 = back.getRight().toString().substring(0,back.getRight().toString().indexOf(":"));
            if(!player.getWorld().getRegistryKey().equals(back.getRight()) && (st1.equals(st2) && !st1.contains("minecells")) && !st1.contains("twilightforest")
                    || !st1.equals(st2)
            ){
                ctx.getSource().sendFeedback(() -> {
                    return Text.translatable("aliveandwell.hometpaback.nosameworld").formatted(Formatting.RED);
                }, false);
                return 0;
            }

            if (dfafsfsa(player)) return 1;

            TeleportUtils.genericTeleport(true, 3, player, () -> {
                int experience = ((int)(player.getWorld().getLevelProperties().getTimeOfDay()/ 24000L)+1)*xpCostTime;
                if (experience >= 1000 ){
                    experience = 1000;
                }

                if((player.totalExperience >= experience && player.getWorld().getRegistryKey().equals(back.getRight())  && (st1.equals(st2) && st1.contains("minecraft")))
                        || (st1.equals(st2) && st1.contains("minecells")) || (st1.equals(st2) && st1.contains("twilightforest"))){

                    player.teleport(
                            Objects.requireNonNull(ctx.getSource().getServer().getWorld(back.getRight())),
                            back.getLeft().x, back.getLeft().y, back.getLeft().z,
                            player.getYaw(), player.getPitch());
                    player.addExperience(-experience);//每次传送消耗经验=============================================
                    recentRequests.put(player.getUuid(), Instant.now().getEpochSecond());

                    player.sendMessage(Text.translatable("aliveandwell.hometpaback.xpcost").append(Text.literal(String.valueOf(experience))).append(Text.translatable("aliveandwell.hometpaback.xpcount")).formatted(Formatting.GREEN),false);
                }else if (player.totalExperience < experience && player.getWorld().getRegistryKey().equals(back.getRight())){//
                    int finalExperience = experience;
                    ctx.getSource().sendFeedback(() -> {
                        return Text.translatable("aliveandwell.hometpaback.xpno1").append(Text.literal(String.valueOf(finalExperience))).append(Text.translatable("aliveandwell.hometpaback.notp")).formatted(Formatting.RED);
                } ,false);
                } else if(!player.getWorld().getRegistryKey().equals(back.getRight()) && (st1.equals(st2) && !st1.contains("minecells")) && !st1.contains("twilightforest")
                        || !st1.equals(st2)
                ){
                    ctx.getSource().sendFeedback(() -> {
                       return Text.translatable("aliveandwell.hometpaback.nosameworld").formatted(Formatting.RED);
                    }, false);
                }
            });

            return 1;
        } else {
            player.sendMessage(Text.translatable("aliveandwell.hometpaback.nosetback"), false);
        }
        return 0;
    }

    int backSet(CommandContext<ServerCommandSource> ctx, Object o) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
        ((IStoreHome) player).addBack(new Vec3d(player.getX(), player.getY(), player.getZ()), player.getWorld().getRegistryKey());
        player.sendMessage(Text.translatable("aliveandwell.hometpaback.setback").append(Text.literal("("+player.getBlockPos().getX()+","+ player.getBlockPos().getY()+","+player.getBlockPos().getZ()+")"+"【"+player.getWorld().getRegistryKey().getValue().getPath()+"】")).formatted(Formatting.LIGHT_PURPLE), false);
        return 1;
    }
}
