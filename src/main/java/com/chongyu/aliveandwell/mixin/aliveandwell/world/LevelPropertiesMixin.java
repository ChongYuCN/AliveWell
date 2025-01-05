package com.chongyu.aliveandwell.mixin.aliveandwell.world;

import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.*;
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
}
