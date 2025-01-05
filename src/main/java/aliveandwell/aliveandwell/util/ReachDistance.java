package aliveandwell.aliveandwell.util;

import aliveandwell.aliveandwell.mixins.aliveandwell.enity.LivingEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ReachDistance {
    public static float getReachDistance(MinecraftClient client, GameMode gameMode) {
        if (gameMode.isCreative()) {
            return 5.0f;
        }

        float reach = 1.5f;

        assert client.player != null;
        ItemStack selectedStack = client.player.getMainHandStack();
        if (selectedStack != null) {
            Item item = selectedStack.getItem();
            if (item == Items.STICK) reach += 0.6F;
            else if (item == Items.BONE)reach += 0.6f;
            else if (item instanceof TridentItem) reach += 1.25f;
            else if (item instanceof ShearsItem) reach += 0.6f;
            else if (item instanceof ShovelItem && item != Items.STONE_SHOVEL) reach += 0.75f;
            else if (item instanceof PickaxeItem) reach += 0.75f;
            else if (item instanceof AxeItem) reach += 0.75f;
            else if (item instanceof SwordItem) reach += 0.75f;
            else if (item instanceof HoeItem) reach += 0.75f;
            else reach += 0.55f;
        }

        //跳跃攻击怪物
        if(!client.player.isOnGround()){
            reach += 0.75f;
        }

        //跳跃放置方块
        if(((LivingEntityAccessor)(Object)(client.player)).getJumping()){
            reach += 0.75f;
        }

        //搭建三格方块在怪物上方攻击怪物
        if (client.crosshairTarget != null && client.crosshairTarget.getPos().getY() < client.player.getBlockPos().getY()) {
            reach += 0.5f;
        }

//        if (client.crosshairTarget != null) {
//            client.player.sendMessage(Text.translatable("指针："+client.crosshairTarget.getPos().getY()));//66
//        }
//        client.player.sendMessage(Text.translatable("玩家："+client.player.getBlockPos().getY()));//67
        return reach;
    }
}
