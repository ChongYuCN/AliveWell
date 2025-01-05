package aliveandwell.aliveandwell.flintcoppertool.utils;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class ModBlockTags {
    public static final TagKey<Block> ROCK_PLACEABLE_ON = register("rock_placeable_on");

	private ModBlockTags() { }

	private static TagKey<Block> register(String id) {
		return TagKey.of(Registry.BLOCK_KEY, new Identifier(AliveAndWellMain.MOD_ID, id));
	}
}
