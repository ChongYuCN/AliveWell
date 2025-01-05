package aliveandwell.aliveandwell.dimensions;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class DimsRegistry {

    public static final RegistryKey<World> UNDER_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, AliveAndWellMain.MOD_DIMENSION_ID);

    public static void setupDimension() {
        //TBD - refer to BumbleZone in the future for ideas
    }

}
