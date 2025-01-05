package aliveandwell.aliveandwell.miningsblock.logic;

import aliveandwell.aliveandwell.miningsblock.BlockGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class BlockProcessor {
    private static final Map<String, Boolean> checkedBlocks = new HashMap<>();
//    private static final Map<String, Map<String, Boolean>> checkedPairs = new HashMap<>();
    private static final Map<String, Set<String>> checkedTags = new HashMap<>();

    public static void rebuild() {
        checkedBlocks.clear();
//        checkedPairs.clear();
        checkedTags.clear();
    }

    public static boolean isValidTarget(BlockState state, ServerWorld world, BlockPos pos, Block source) {
        Block block = world.getBlockState(pos).getBlock();
        return !state.isAir() && checkedBlocks.computeIfAbsent(Registry.BLOCK.getId(block).toString(),
                (name) -> BlockProcessor.checkBlock(state)) && matches(source, block);
    }

    private static boolean matches(Block origin, Block target) {
        if (origin == target) {
            return true;
        }
//        else {
//            String originName = Registry.BLOCK.getId(origin).toString();
//            String targetName = Registry.BLOCK.getId(target).toString();
//            boolean useOriginKey = originName.compareTo(targetName) >= 0;
//
//            if (useOriginKey) {
//                return checkedPairs.computeIfAbsent(originName, (name) -> new HashMap<>())
//                        .computeIfAbsent(targetName, (name) -> checkMatch(origin, target));
//            } else {
//                return checkedPairs.computeIfAbsent(targetName, (name) -> new HashMap<>())
//                        .computeIfAbsent(originName, (name) -> checkMatch(origin, target));
//            }
//        }
        return false;
    }

    private static boolean checkBlock(BlockState blockState) {
        Set<String> ids = new HashSet<>();
        String blockId = Registry.BLOCK.getId(blockState.getBlock()).toString();
        ids.add(blockId);
        Set<String> tags = checkedTags.computeIfAbsent(blockId, (name) -> getTagsFor(blockState));
        tags.forEach(tag -> ids.add("#" + tag));

        Set<String> configs = BlockGroups.getGroup(blockId);

        if(blockState.getBlock() == Blocks.COBBLESTONE){
            return true;
        }

        for (String id : configs) {
            if (ids.contains(id)) {
                return true;
            }
        }

        return false;
    }


    private static Set<String> getTagsFor(BlockState blockState) {
        Set<String> tags = new HashSet<>();
        Registry.BLOCK.streamTags().forEach(blockTagKey -> {
            if (blockState.isIn(blockTagKey)) {
                tags.add(blockTagKey.id().toString());
            }
        });
        return tags;
    }

//    private static boolean checkMatch(Block origin, Block target) {
//        Set<String> group =
//                BlockGroups.getGroup(Registry.BLOCK.getId(origin).toString());
//
//        if (group != null) {
//            return group.contains(Registry.BLOCK.getId(target).toString());
//        }
//        return false;
//    }

}
