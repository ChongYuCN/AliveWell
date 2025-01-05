package com.chongyu.aliveandwell.dimensions;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.registry.BlockInit;
import com.chongyu.aliveandwell.registry.ItemInit;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class PortalInit {

    public static void registerPortal() {
        //地下世界
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.AMETHYST_BLOCK)//紫水晶块
                .lightWithItem(ItemInit.FLINT_AND_STEEL)//
                .forcedSize(2,3)
                .destDimID(AliveAndWellMain.MOD_DIMENSION_ID)
                .customPortalBlock(BlockInit.UNDERWORLD_PORTAL)
                .registerPortal();

        //地狱
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.BUDDING_AMETHYST)//紫水晶块
                .lightWithItem(ItemInit.FLINT_AND_STEEL)//
                .forcedSize(2,3)
                .destDimID(new Identifier("the_nether"))
                .customPortalBlock(BlockInit.NETHERWORLD_PORTAL)
                .registerPortal();

        //秘银随机门
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.FRAME_MITHRIL)//传送框架（秘银）
                .lightWithItem(ItemInit.FLINT_AND_STEEL)//
                .forcedSize(2,3)
                .destDimID(new Identifier("overworld"))
                .customPortalBlock(BlockInit.RANDOM_A_PORTAL)
                .registerPortal();

        //艾德曼随机门
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.FRAME_ADAMANTIUM)//传送框架（艾德曼）
                .lightWithItem(ItemInit.FLINT_AND_STEEL)//
                .forcedSize(2,3)
                .destDimID(new Identifier("overworld"))
                .customPortalBlock(BlockInit.RANDOM_B_PORTAL)
                .registerPortal();

        //出生点门
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.FRAME_SPAWNPOINT)//传送框架（出生点）
                .lightWithItem(ItemInit.FLINT_AND_STEEL)//
                .forcedSize(2,3)
                .destDimID(new Identifier("overworld"))
                .customPortalBlock(BlockInit.SPAWNPOINT_PORTAL)
                .registerPortal();
    }
}
