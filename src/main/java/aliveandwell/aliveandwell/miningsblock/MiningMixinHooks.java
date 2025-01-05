package aliveandwell.aliveandwell.miningsblock;

import aliveandwell.aliveandwell.miningsblock.logic.MiningLogic;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiningMixinHooks {
    public static void tryHarvest(ServerPlayerEntity player, BlockPos pos, BlockState source) {
        if (MiningPlayers.canStartMining(player) && !MiningPlayers.isVeinMining(player)) {
            MiningPlayers.startMining(player);
            MiningLogic.startMining(player, pos, source);
            MiningPlayers.stopMining(player);
        }
    }

    public static BlockPos getActualSpawnPos(World level, BlockPos pos) {
        return MiningPlayers.getNewSpawnPosForDrop(level, pos).orElse(pos);
    }

//    public static <T extends LivingEntity> boolean shouldCancelItemDamage(T entity) {
//        return entity instanceof PlayerEntity player && !MiningPlayers.isVeinMining(player);
//    }

    public static int modifyItemDamage(int damage) {
        int newDamage = damage;

        float multiplier = MiningPlayers.toolDamageMultiplier;
        newDamage = Math.max(0, (int) ((float) newDamage * multiplier));

        return newDamage;
    }
}
