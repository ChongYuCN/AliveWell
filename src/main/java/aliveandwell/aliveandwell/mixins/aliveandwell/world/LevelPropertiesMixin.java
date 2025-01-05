package aliveandwell.aliveandwell.mixins.aliveandwell.world;

import com.mojang.serialization.Lifecycle;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelProperties.class)
public abstract class LevelPropertiesMixin implements ServerWorldProperties, SaveProperties {
    @Shadow private LevelInfo levelInfo;
    @Overwrite
    public GameMode getGameMode() {
        return GameMode.SURVIVAL;
    }
    @Overwrite
    public void setGameMode(GameMode gameMode) {
        this.levelInfo = this.levelInfo.withGameMode(GameMode.SURVIVAL);
    }

    @Overwrite
    public boolean areCommandsAllowed() {
        return false;
    }
    @Overwrite
    public void setDifficulty(Difficulty difficulty) {
        this.levelInfo = this.levelInfo.withDifficulty(Difficulty.HARD);
    }
    @Overwrite
    public Difficulty getDifficulty() {
        return Difficulty.HARD;
    }

    @Overwrite
    public boolean isDifficultyLocked() {
        return true;
    }

    @Shadow
    @Final
    private Lifecycle lifecycle;

    @Unique
    private static final Logger log = LogManager.getLogger("com.parzivail.herebenodragons.mixin.LevelPropertiesMixin");

    @Inject(method = "getLifecycle()Lcom/mojang/serialization/Lifecycle;", at = @At("HEAD"), cancellable = true)
    private void getLifecycle(CallbackInfoReturnable<Lifecycle> cir) {
        if (lifecycle == Lifecycle.experimental()) {
            log.warn("Supressing EXPERIMENTAL level lifecycle");
            cir.setReturnValue(Lifecycle.stable());
            cir.cancel();
        }
    }
}
