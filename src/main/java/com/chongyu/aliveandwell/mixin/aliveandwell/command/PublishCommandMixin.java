package com.chongyu.aliveandwell.mixin.aliveandwell.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.PublishCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PublishCommand.class)
public class PublishCommandMixin {
    @Overwrite
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("publish").requires((source) -> {
            return source.hasPermissionLevel(4);
        }))));
    }
}
