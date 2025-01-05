package aliveandwell.aliveandwell.registry.events;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

public class PlayerAttackDistance {
    public static void init(){
        //附魔工具可以打哭泣天使
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            //==========================================================
            ItemStack selectedStack = player.getMainHandStack();
            if (entity.getName().toString().contains("weeping_angel")) {
                if (!selectedStack.hasEnchantments()) {
                    player.sendMessage(Text.translatable("使用附魔镐子对抗哭泣天使").formatted(Formatting.BLUE));
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });
    }
}
