package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import aliveandwell.aliveandwell.miningsblock.MiningPlayers;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {
    @Unique
    private Text timeText;
//    private int time = 2400;

    @Shadow
    private int ticksSinceDeath;
    @Mutable
    @Final
    @Shadow private final Text message;
    @Mutable
    @Final
    @Shadow private final boolean isHardcore;
    @Shadow private Text scoreText;
    @Final
    @Shadow private final List<ButtonWidget> buttons = Lists.newArrayList();

    protected DeathScreenMixin(Text title, Text message, boolean isHardcore) {
        super(title);
        this.message = message;
        this.isHardcore = isHardcore;
    }

//    @Inject(at = @At("HEAD"), method = "init")
//    public void init(CallbackInfo info) {
//        this.timeText = Text.translatable("aliveandwell.deathscreen.info1").append(": ").append(Text.literal(Integer.toString(this.client.player.getScore())).formatted(Formatting.YELLOW));
//    }

    @Overwrite
    public void init() {
        this.timeText = Text.translatable("aliveandwell.deathscreen.info1").append(": ").append(Text.literal(Integer.toString(this.client.player.getScore())).formatted(Formatting.YELLOW));

        this.ticksSinceDeath = 0;
        this.buttons.clear();
        this.buttons.add(this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 72, 200, 20, this.isHardcore ? Text.translatable("deathScreen.spectate") : Text.translatable("deathScreen.respawn"), button -> {
            this.client.player.requestRespawn();
            MiningPlayers.timeDead = 2400;
            this.client.setScreen(null);
        })));
        this.buttons.add(this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 96, 200, 20, Text.translatable("deathScreen.titleScreen"), button -> {
            this.quitLevel();
        })));
        for (ButtonWidget buttonWidget : this.buttons) {
            buttonWidget.active = false;
        }
        this.scoreText = Text.translatable("deathScreen.score").append(": ").append(Text.literal(Integer.toString(this.client.player.getScore())).formatted(Formatting.YELLOW));
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        DeathScreen.drawCenteredText(matrices, this.textRenderer, this.timeText, this.width / 2, 110, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        if (this.client != null && this.client.player != null && this.client.player.isDead()) {
            MiningPlayers.timeDead--;
        }
        this.timeText = Text.translatable("aliveandwell.deathscreen.info1").append(": ").append(Text.literal(Integer.toString(MiningPlayers.timeDead/20)).append(Text.translatable("s")).formatted(Formatting.YELLOW));
        if(MiningPlayers.timeDead /20 == 0){
            if (this.client.player != null) {
                this.client.player.requestRespawn();
            }
            MiningPlayers.timeDead = 2400;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if ( keyCode == GLFW.GLFW_KEY_T || keyCode == GLFW.GLFW_KEY_SLASH) {
            if (client != null) {
                client.setScreen(new ChatScreen(""));
            }
        }
        return true;
    }

    @Shadow
    private void quitLevel() {
        if (this.client.world != null) {
            this.client.world.disconnect();
        }
        this.client.disconnect(new MessageScreen(Text.translatable("menu.savingLevel")));
        this.client.setScreen(new TitleScreen());
    }
}
