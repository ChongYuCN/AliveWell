package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
@Environment(EnvType.CLIENT)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
    @Unique
    private int totalEx;

    @Shadow
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "handledScreenTick", at = @At("HEAD"))
    public void handledScreenTick(CallbackInfo ca) {
        if (this.client != null && this.client.player != null) {
            this.totalEx = this.client.player.totalExperience;
        }
    }

    @Inject(method = "drawForeground", at = @At("HEAD"))
    public void drawForeground(MatrixStack matrices, int mouseX, int mouseY,CallbackInfo ca) {
        this.textRenderer.draw(matrices, Text.translatable("XP:"+String.valueOf(this.totalEx)).formatted(Formatting.GREEN), (float) this.backgroundWidth /2+12, -10, 175752);
        this.textRenderer.draw(matrices, Text.translatable("Day:"+AliveAndWellMain.day ).formatted(Formatting.YELLOW), (float) this.backgroundWidth /2+12 , -20, 175752);
    }
}
