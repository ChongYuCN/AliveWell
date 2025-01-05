package com.chongyu.aliveandwell.crafttime.config;

import com.chongyu.aliveandwell.crafttime.Constants;
import com.chongyu.aliveandwell.registry.ItemInit;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.HashMap;

public class ConfigLoader {

	public HashMap<Integer, Float> difficultyMap = new HashMap<>();
	private static Path cfgPath = FabricLoader.getInstance().getConfigDir();
	public static String GLOBAL = "global_multiplier";
	public static String MODS = "mods";
	public static String MOD_MULTIPLIER = "mod_multiplier";
	public static String ITEMS = "items";

	public ConfigLoader() {
	}

	public static void genSampleConfig() {
		HashMap<String, JsonObject> nameSpaceMap = new HashMap<>();
		Registries.ITEM.getIds().forEach(rkey -> {
			String namespace = rkey.getNamespace();
			String path = rkey.getPath();
			if (!nameSpaceMap.containsKey(namespace)) {
				JsonObject array = new JsonObject();
				nameSpaceMap.put(namespace, array);
			}
			nameSpaceMap.get(namespace).addProperty(path, 20F);
		});
		
		JsonObject all = new JsonObject();
		all.addProperty(GLOBAL, 1F);
		JsonObject mod_list = new JsonObject();
		all.add(MODS, mod_list);
		nameSpaceMap.forEach((name, array) -> {
			JsonObject mod = new JsonObject();
			mod.addProperty(MOD_MULTIPLIER, 1F);
			mod.add(ITEMS, array);
			mod_list.add(name, mod);
		});

		try {
			File cfgSampleFile = cfgPath.resolve(Constants.CONFIG_FILENAME).toFile();
			if (cfgSampleFile.exists()) {
				cfgSampleFile = cfgPath.resolve(Constants.SAMPLE_CONFIG_FILENAME).toFile();
			}
			FileWriter writer = new FileWriter(cfgSampleFile);
			writer.write(all.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parserFrom(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(jsonString);
		float global_multiplier = object.getAsJsonPrimitive(GLOBAL).getAsFloat();
		JsonObject mod_list = object.getAsJsonObject(MODS);
		mod_list.entrySet().forEach(e -> {
			String namespace = e.getKey() + ':';
			JsonObject mod = (JsonObject) e.getValue();
			float mod_multiplier = mod.getAsJsonPrimitive(MOD_MULTIPLIER).getAsFloat() * global_multiplier;
			JsonObject items = mod.getAsJsonObject(ITEMS);
			items.entrySet().forEach(i -> {
				String item = namespace + i.getKey();
				int id = Item.getRawId(Registries.ITEM.get(new Identifier(item)));
				float value = i.getValue().getAsFloat() * mod_multiplier;
				this.setDifficulty(id, value);
			});
		});
	}

	public float getDifficulty(Item item) {
		int rkey = Item.getRawId(item);
		if(item==Items.IRON_BLOCK){
			return 360;
		}
		if(item==Items.IRON_INGOT){
			return 40F;
		}
		if(item==Items.GOLD_INGOT){
			return 30F;
		}
		if(item==Items.DIAMOND){
			return 60F;
		}
		if(item==ItemInit.INGOT_WUJIN){
			return 60F;
		}
		if(item==Items.NETHERITE_INGOT){
			return 100F;
		}
		if(item== ItemInit.INGOT_MITHRIL){
			return 120F;
		}
		if(item==ItemInit.INGOT_ADAMANTIUM){
			return 150F;
		}
		if (difficultyMap.containsKey(rkey)) {
			return difficultyMap.get(rkey);
		}
		return 20F;
	}

	public void setDifficulty(int id, float difficulty) {
		this.difficultyMap.put(id, difficulty);
	}
}
