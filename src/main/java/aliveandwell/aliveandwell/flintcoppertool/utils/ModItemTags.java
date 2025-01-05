package aliveandwell.aliveandwell.flintcoppertool.utils;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class ModItemTags {
	public static final TagKey<Item> WOODEN_TOOLS = register("wooden_tools");
	public static final TagKey<Item> STONE_TOOLS = register("stone_tools");

	private ModItemTags() { }

	private static TagKey<Item> register(String id) {
		return TagKey.of(Registry.ITEM_KEY, new Identifier(AliveAndWellMain.MOD_ID, id));
	}
}
