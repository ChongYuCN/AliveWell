package aliveandwell.aliveandwell.hometpaback.util;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Timer;
import java.util.TimerTask;

public class TeleportUtils {
    public static void genericTeleport(boolean bossBar, double standStillTime, ServerPlayerEntity who, Runnable onCounterDone) {
        MinecraftServer server = who.server;
        final double[] counter = {standStillTime};
        final Vec3d[] lastPos = {who.getPos()};
        CommandBossBar standStillBar = null;//CommandBossBar
        if (bossBar) {//CustomBossEvents=BossBarManager
            standStillBar = server.getBossBarManager().add(new Identifier("standstill-" + who.getUuidAsString()), Text.empty());
            standStillBar.addPlayer(who);
            standStillBar.setColor(BossBar.Color.YELLOW);

        }
        who.networkHandler.sendPacket(new TitleFadeS2CPacket(0, 10, 5));//ServerPlayNetworkHandler=ServerGamePacketListenerImpl
        CommandBossBar finalStandStillBar = standStillBar;

        final ServerPlayerEntity[] whoFinal = {who};
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (counter[0] == 0) {
                    if (bossBar) {
                        finalStandStillBar.removePlayer(whoFinal[0]);
                        server.getBossBarManager().remove(finalStandStillBar);
                    } else {
                        whoFinal[0].sendMessage(Text.translatable("aliveandwell.hometpaback.teleporting").formatted(Formatting.GOLD), true);
                    }
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            whoFinal[0].networkHandler.sendPacket(new ClearTitleS2CPacket(true));
                        }
                    }, 500);
                    timer.cancel();
                    server.submit(onCounterDone);
                    return;
                }

                Vec3d currPos = whoFinal[0].getPos();
                if (whoFinal[0].isRemoved()) {
                    whoFinal[0] = server.getPlayerManager().getPlayer(whoFinal[0].getUuid());
                    assert whoFinal[0] != null;
                } else if (lastPos[0].equals(currPos)) {
                    counter[0] -= .25;
                } else {
                    lastPos[0] = currPos;
                    counter[0] = standStillTime;
                }

                if (bossBar) {
                    finalStandStillBar.setPercent((float) (counter[0] / standStillTime));
                } else {
                    whoFinal[0].sendMessage(Text.translatable("aliveandwell.hometpaback.stilling").formatted(Formatting.YELLOW)
                            .append(Text.translatable(Integer.toString((int) Math.floor(counter[0] + 1))).formatted(Formatting.GOLD))
                            .append(Text.translatable("aliveandwell.hometpaback.second").formatted(Formatting.LIGHT_PURPLE)), true);
                }
                whoFinal[0].networkHandler.sendPacket(new SubtitleS2CPacket(Text.translatable("aliveandwell.hometpaback.stilling")
                        .formatted(Formatting.RED, Formatting.YELLOW)));//副标题
                whoFinal[0].networkHandler.sendPacket(new TitleS2CPacket(Text.translatable("aliveandwell.hometpaback.teleporting")
                        .formatted(Formatting.GOLD, Formatting.BOLD)));
            }
        }, 0, 250);
    }
}
