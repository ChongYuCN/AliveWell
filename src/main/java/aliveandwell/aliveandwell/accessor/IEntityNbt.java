package aliveandwell.aliveandwell.accessor;

import net.minecraft.entity.player.PlayerEntity;

public interface IEntityNbt {
    boolean entityNbt$contains(String key);

    boolean entityNbt$getBlooean(String key);
    void entityNbt$putBlooean(String key, boolean value);

    void entityNbt$putString(String key, String value);
    String entityNbt$getString(String key);

    PlayerEntity entityNbt$getPlayerLighting();
    void entityNbt$setPlayerLighting(PlayerEntity player);
}
