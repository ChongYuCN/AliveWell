package com.chongyu.aliveandwell.dimensions;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class DimsRegistry {

    public static final RegistryKey<World> UNDER_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, AliveAndWellMain.MOD_DIMENSION_ID);
    public static final RegistryKey<DimensionType> UNDER_WORLD_KEY_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, AliveAndWellMain.MOD_DIMENSION_ID);


    public static void setupDimension() {
        //TBD - refer to BumbleZone in the future for ideas
    }

}
