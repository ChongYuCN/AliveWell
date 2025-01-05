package com.chongyu.aliveandwell.mixin.aliveandwell.client.disable;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.level.LevelInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.OptionalLong;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {
    protected CreateWorldScreenMixin(Text title) {
        super(title);
    }

    @Overwrite
    public static CreateWorldScreen create(MinecraftClient client, @Nullable Screen parent, LevelInfo levelInfo, GeneratorOptionsHolder generatorOptionsHolder, @Nullable Path dataPackTempDir) {
        CreateWorldScreen createWorldScreen = new CreateWorldScreen(client, parent, generatorOptionsHolder, WorldPresets.getWorldPreset(generatorOptionsHolder.selectedDimensions().dimensions()), OptionalLong.of(generatorOptionsHolder.generatorOptions().getSeed()));
//        createWorldScreen.recreated = true;
        createWorldScreen.worldCreator.setWorldName(levelInfo.getLevelName());
        createWorldScreen.worldCreator.setCheatsEnabled(levelInfo.areCommandsAllowed());
        createWorldScreen.worldCreator.setDifficulty(levelInfo.getDifficulty());
        createWorldScreen.worldCreator.getGameRules().setAllValues(levelInfo.getGameRules(), null);
        if (levelInfo.isHardcore()) {
            createWorldScreen.worldCreator.setGameMode(WorldCreator.Mode.SURVIVAL);
        } else if (levelInfo.getGameMode().isSurvivalLike()) {
            createWorldScreen.worldCreator.setGameMode(WorldCreator.Mode.HARDCORE);
        } else if (levelInfo.getGameMode().isCreative()) {
            createWorldScreen.worldCreator.setGameMode(WorldCreator.Mode.SURVIVAL);
        }
        createWorldScreen.dataPackTempDir = dataPackTempDir;
        return createWorldScreen;
    }

}
