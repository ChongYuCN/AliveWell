package aliveandwell.aliveandwell.flintcoppertool.init;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.flintcoppertool.block.StickTwigBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlocksInit {
    
    // stick
    public static final Block
        STICK_TWIG_BLOCK;

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, name), block);
    }

    public static void init() {
        // stick
        register("stick_twig", STICK_TWIG_BLOCK);
    }
    
    static {
        // stick
        STICK_TWIG_BLOCK = new StickTwigBlock();
    }
}