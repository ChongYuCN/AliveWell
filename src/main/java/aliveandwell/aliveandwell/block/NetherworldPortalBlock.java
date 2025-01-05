package aliveandwell.aliveandwell.block;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.dimensions.DimsRegistry;
import aliveandwell.aliveandwell.util.TeleporterHelper;
import aliveandwell.aliveandwell.config.CommonConfig;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.interfaces.EntityInCustomPortal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherworldPortalBlock extends CustomPortalBlock {

    public NetherworldPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        EntityInCustomPortal entityInPortal = (EntityInCustomPortal) entity;
        entityInPortal.tickInPortal(pos.toImmutable());
        if (!entityInPortal.didTeleport()) {
            if (entityInPortal.getTimeInPortal() >= entity.getMaxNetherPortalTime()) {
                entityInPortal.setDidTP(true);
                if (!world.isClient){
                    if(entity.world.getRegistryKey() == World.NETHER || entity.world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
                        if(AliveAndWellMain.day >= CommonConfig.netherDay ){
                            TeleporterHelper.TPToDim(world, entity, getPortalBase(world, pos), pos);
                        }else {
                            if(entity instanceof PlayerEntity player){
                                player.sendMessage(Text.of(String.valueOf(CommonConfig.netherDay)).copy().append(Text.translatable("aliveandewell.to_netherworld")).formatted(Formatting.YELLOW));
                            }
                        }
                    }
                }
            }
        }
    }
}
