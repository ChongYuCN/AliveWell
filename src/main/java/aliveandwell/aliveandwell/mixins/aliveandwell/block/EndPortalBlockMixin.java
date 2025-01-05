package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.util.CanJoinEnd;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin extends BlockWithEntity {
    protected EndPortalBlockMixin(Settings settings) {
        super(settings);
    }

    @Overwrite
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity instanceof ServerPlayerEntity player){
            if(AliveAndWellMain.canEnd){//test
                if (world instanceof ServerWorld && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals() && VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()))), state.getOutlineShape(world, pos), BooleanBiFunction.AND)) {
                    RegistryKey<World> registryKey = world.getRegistryKey() == World.END ? World.OVERWORLD : World.END;
                    ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
                    if (serverWorld == null) {
                        return;
                    }
                    entity.moveToWorld(serverWorld);
                }
            }else {
                try {
                    if(!CanJoinEnd.canJoinEnd1(player)){
                        player.sendMessage(Text.translatable("aliveandwell.endportal.advancement01").formatted(Formatting.YELLOW));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(!CanJoinEnd.canJoinEnd2(player)){
                        player.sendMessage(Text.translatable("aliveandwell.endportal.advancement02").formatted(Formatting.YELLOW));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if(FabricLoader.getInstance().isModLoaded("adventurez")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd3(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement03").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("bosses_of_mass_destruction")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd4(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement04").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("bosses_of_mass_destruction")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd5(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement05").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("bosses_of_mass_destruction")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd6(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement06").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("doom")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd7(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement07").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("doom")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd8(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement08").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("doom")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd9(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement09").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("twilightforest")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd10(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement10").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("minecells")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd11(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement11").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("doom")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd12(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement12").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("botania")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd13(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement13").formatted(Formatting.YELLOW));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(FabricLoader.getInstance().isModLoaded("soulsweapons")) {
                    try {
                        if (!CanJoinEnd.canJoinEnd14(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement14").formatted(Formatting.YELLOW));
                        }
                        if (!CanJoinEnd.canJoinEnd15(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement15").formatted(Formatting.YELLOW));
                        }
                        if (!CanJoinEnd.canJoinEnd16(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement16").formatted(Formatting.YELLOW));
                        }
                        if (!CanJoinEnd.canJoinEnd17(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement17").formatted(Formatting.YELLOW));
                        }
                        if (!CanJoinEnd.canJoinEnd18(player)) {
                            player.sendMessage(Text.translatable("aliveandwell.endportal.advancement18").formatted(Formatting.YELLOW));
                        }

                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }


                try {
                    if(CanJoinEnd.canJoinEnd(player)){
                        if (world instanceof ServerWorld && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals() && VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()))), state.getOutlineShape(world, pos), BooleanBiFunction.AND)) {
                            RegistryKey<World> registryKey = world.getRegistryKey() == World.END ? World.OVERWORLD : World.END;
                            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
                            if (serverWorld == null) {
                                return;
                            }
                            entity.moveToWorld(serverWorld);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }
}
