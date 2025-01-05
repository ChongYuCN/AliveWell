package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientWorld.Properties.class)
public class ClientWorldPropertiesMixin {
    @Shadow private Difficulty difficulty;
    @Overwrite
    public boolean isDifficultyLocked() {
        return true;
    }
    @Overwrite
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = Difficulty.HARD;
    }

}
