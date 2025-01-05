package com.chongyu.aliveandwell.util;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

@SuppressWarnings("unused")
public class ModTags {

    public static class Structures {
        public static final TagKey<Structure> ANCIENT_CITY = createTag("ancient_city");

        private static TagKey<Structure> createTag(String id) {
            return TagKey.of(RegistryKeys.STRUCTURE, new Identifier(AliveAndWellMain.MOD_ID, id));
        }
    }
}
