package com.chongyu.aliveandwell.mixin.aliveandwell.command;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.util.timeutil.WorldTimeHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TimeCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TimeCommand.class)
public class TimeCommandMixin {

    @Overwrite
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
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
