package aliveandwell.aliveandwell.mixins.aliveandwell.command;

import aliveandwell.aliveandwell.AliveAndWellMain;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Collections;

@Mixin(GameModeCommand.class)
public class GameModeCommandMixin {

    @Overwrite
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder) CommandManager.literal("gamemode").requires(source -> source.hasPermissionLevel(2));
        if(AliveAndWellMain.canCreative){
            for (GameMode gameMode : GameMode.values()) {
                literalArgumentBuilder.then(((LiteralArgumentBuilder)CommandManager.literal(gameMode.getName()).executes(context -> execute(context, Collections.singleton(((ServerCommandSource)context.getSource()).getPlayerOrThrow()), gameMode))).then(CommandManager.argument("target", EntityArgumentType.players()).executes(context -> execute(context, EntityArgumentType.getPlayers(context, "target"), gameMode))));
            }
        }
        dispatcher.register(literalArgumentBuilder);
    }

    @Shadow
    private static int execute(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> targets, GameMode gameMode) {
        int i = 0;
        for (ServerPlayerEntity serverPlayerEntity : targets) {
            if (!serverPlayerEntity.changeGameMode(gameMode)) continue;
            sendFeedback(context.getSource(), serverPlayerEntity, gameMode);
            ++i;
        }
        return i;
    }

    @Shadow
    private static void sendFeedback(ServerCommandSource source, ServerPlayerEntity player, GameMode gameMode) {
        MutableText text = Text.translatable("gameMode." + gameMode.getName());
        if (source.getEntity() == player) {
            source.sendFeedback(Text.translatable("commands.gamemode.success.self", text), true);
        } else {
            if (source.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {
                player.sendMessage(Text.translatable("gameMode.changed", text));
            }
            source.sendFeedback(Text.translatable("commands.gamemode.success.other", player.getDisplayName(), text), true);
        }
    }
}
