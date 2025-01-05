package com.chongyu.aliveandwell.flintcoppertool.utils;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModItemTags {
	public static final TagKey<Item> WOODEN_TOOLS = register("wooden_tools");
	public static final TagKey<Item> STONE_TOOLS = register("stone_tools");

	private ModItemTags() { }

	private static TagKey<Item> register(String id) {
		return TagKey.of(RegistryKeys.ITEM, new Identifier(AliveAndWellMain.MOD_ID, id));
	}
}
