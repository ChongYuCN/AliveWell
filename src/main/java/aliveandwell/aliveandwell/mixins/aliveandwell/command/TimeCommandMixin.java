package aliveandwell.aliveandwell.mixins.aliveandwell.command;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.accessor.WorldTimeHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TimeCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TimeCommand.class)
public class TimeCommandMixin {

    @Inject(at=@At("HEAD"), method = "register", cancellable = true)
    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo ci) {
        if(FabricLoader.getInstance().isModLoaded("magicmoon")){
            if(AliveAndWellMain.canCreative){
                dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("time").requires((source) -> {
                    return source.hasPermissionLevel(2);
                })).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) ((LiteralArgumentBuilder)CommandManager.literal("setday"))))).then(CommandManager.argument("time", TimeArgumentType.time()).executes((context) -> {

                    return executeSet((ServerCommandSource)context.getSource(), (IntegerArgumentType.getInteger(context, "time")-1)*24000);}))
                ).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) ((LiteralArgumentBuilder)CommandManager.literal("setdayNight"))))).then(CommandManager.argument("time", TimeArgumentType.time()).executes((context) -> {
                    return executeSet((ServerCommandSource)context.getSource(), (IntegerArgumentType.getInteger(context, "time")-1)*24000+13000);}))
                ).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) ((LiteralArgumentBuilder)CommandManager.literal("setdaySleeping"))))).then(CommandManager.argument("time", TimeArgumentType.time()).executes((context) -> {
                    return executeSet((ServerCommandSource)context.getSource(), (IntegerArgumentType.getInteger(context, "time")-1)*24000+14900);}))
                ).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) ((LiteralArgumentBuilder)CommandManager.literal("setdayMorning"))))).then(CommandManager.argument("time", TimeArgumentType.time()).executes((context) -> {
                    return executeSet((ServerCommandSource)context.getSource(), (IntegerArgumentType.getInteger(context, "time")-1)*24000+23900);}))
                ))));
            }
            ci.cancel();
        }
    }

    //time++++++++++++++++++
    @Overwrite
    private static int getDayTime(ServerWorld world) {
        return (int)((((WorldTimeHelper)world.getLevelProperties()).GetDoubleTime()) % 24000L);
    }

//    @Overwrite
//    private static int getDayTime(ServerWorld world) {
//        return (int)(world.getTimeOfDay() % 24000L);
//    }


    @Overwrite
    public static int executeSet(ServerCommandSource source, int time) {
        for (ServerWorld serverWorld : source.getServer().getWorlds()) {
            //time++++++++++++++++++
            WorldTimeHelper timeHelper = ((WorldTimeHelper) serverWorld.getLevelProperties());
            timeHelper.SetDoubleTime(time);
            long l = (long) timeHelper.GetDoubleTime();
            ((ServerWorldProperties) serverWorld.getLevelProperties()).setTime(l);
            ((ServerWorldProperties) serverWorld.getLevelProperties()).getScheduledEvents().processEvents(serverWorld.getServer(), l);
            serverWorld.setTimeOfDay((long) timeHelper.GetDoubleTime());

            //原
//            serverWorld.setTimeOfDay(time);
        }

        return getDayTime(source.getWorld());
    }

}
