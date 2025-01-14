package aliveandwell.aliveandwell.mixins.aliveandwell.command;

import aliveandwell.aliveandwell.AliveAndWellMain;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.GameRuleCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRuleCommand.class)
public class GameRuleCommandMixin {
//    @Overwrite
//    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
//        final LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = (LiteralArgumentBuilder) CommandManager.literal("gamerule").requires((source) -> {
//            return source.hasPermissionLevel(2);
//        });
////        dispatcher.register(literalArgumentBuilder);
//    }

    @Overwrite
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        final LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)CommandManager.literal("gamerule").requires(source -> source.hasPermissionLevel(4));
        if(AliveAndWellMain.canCreative){
            GameRules.accept(new GameRules.Visitor(){
                @Override
                public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
                    literalArgumentBuilder.then(((LiteralArgumentBuilder)CommandManager.literal(GameRules.KEEP_INVENTORY.getName()).executes(context -> executeQuery((ServerCommandSource)context.getSource(), GameRules.KEEP_INVENTORY))).then(type.argument("value").executes(context -> executeSet(context, GameRules.KEEP_INVENTORY))));
                }
            });
        }
        dispatcher.register(literalArgumentBuilder);
    }

    @Shadow
    static <T extends GameRules.Rule<T>> int executeSet(CommandContext<ServerCommandSource> context, GameRules.Key<T> key) {
        ServerCommandSource serverCommandSource = context.getSource();
        T rule = serverCommandSource.getServer().getGameRules().get(key);
        ((GameRules.Rule)rule).set(context, "value");
        serverCommandSource.sendFeedback(Text.translatable("commands.gamerule.set", key.getName(), ((GameRules.Rule)rule).toString()), true);
        return ((GameRules.Rule)rule).getCommandResult();
    }

    @Shadow
    static <T extends GameRules.Rule<T>> int executeQuery(ServerCommandSource source, GameRules.Key<T> key) {
        T rule = source.getServer().getGameRules().get(key);
        source.sendFeedback(Text.translatable("commands.gamerule.query", key.getName(), ((GameRules.Rule)rule).toString()), false);
        return ((GameRules.Rule)rule).getCommandResult();
    }

}
