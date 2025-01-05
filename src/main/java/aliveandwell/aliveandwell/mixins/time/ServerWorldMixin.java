package aliveandwell.aliveandwell.mixins.time;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.accessor.WorldTimeHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    @Final
    @Shadow
    private ServerWorldProperties worldProperties;
    @Final
    @Shadow
    private boolean shouldTickTime;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow
    public void setTimeOfDay(long time) {}
    @Final
    @Shadow
    private MinecraftServer server;

    @Unique
    boolean start = false;
    @Unique
    public void tickDoubleTime() {
        if(AliveAndWellMain.day == 1) {
            if (this.getLunarTime() - (AliveAndWellMain.day - 1) * 24000L < 15000L) {
                WorldTimeHelper timeHelper = ((WorldTimeHelper)properties);
                if (!start) {
                    timeHelper.SetDoubleTime(this.properties.getTime());
                    start = true;
                }
                timeHelper.SetDoubleTime(timeHelper.GetDoubleTime() + AliveAndWellMain.time_increment);
            }
        }else {
            if (this.getLunarTime() - (AliveAndWellMain.day - 1) * 24000L >= 6000L && this.getLunarTime() - (AliveAndWellMain.day - 1) * 24000L <= 9000L) {
                WorldTimeHelper timeHelper = ((WorldTimeHelper)properties);
                if (!start) {
                    timeHelper.SetDoubleTime(this.properties.getTime());
                    start = true;
                }
                timeHelper.SetDoubleTime(timeHelper.GetDoubleTime() + AliveAndWellMain.time_increment);
            }
        }
    }

    @Inject(at=@At("HEAD"), method="tickTime")
    public void tickTime(CallbackInfo info) {
        if(AliveAndWellMain.day == 1){
            if(this.getLunarTime()-(AliveAndWellMain.day-1)*24000L < 15000L){
                if (this.shouldTickTime) {
                    WorldTimeHelper timeHelper = ((WorldTimeHelper)properties);
                    tickDoubleTime();
                    long l = (long)timeHelper.GetDoubleTime();
                    this.worldProperties.setTime(l);
                    this.worldProperties.getScheduledEvents().processEvents(this.server, l);
                    if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                        //this.setTimeOfDay(this.properties.getTimeOfDay() + 1L);
                        this.setTimeOfDay((long)timeHelper.GetDoubleTime());
                    } else {
                        start = false;
                    }

                } else {
                    start = false;
                }
            }else {
                start = false;
            }
        }else {
            if(this.getLunarTime()-(AliveAndWellMain.day-1)*24000L >= 6000L && this.getLunarTime()-(AliveAndWellMain.day-1)*24000L <= 9000L){
                if (this.shouldTickTime) {
                    WorldTimeHelper timeHelper = ((WorldTimeHelper)properties);
                    tickDoubleTime();
                    long l = (long)timeHelper.GetDoubleTime();
                    this.worldProperties.setTime(l);
                    this.worldProperties.getScheduledEvents().processEvents(this.server, l);
                    if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                        //this.setTimeOfDay(this.properties.getTimeOfDay() + 1L);
                        this.setTimeOfDay((long)timeHelper.GetDoubleTime());
                    } else {
                        start = false;
                    }

                } else {
                    start = false;
                }
            }else {
                start = false;
            }
        }
    }
}
