package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import aliveandwell.aliveandwell.miningsblock.MiningPlayers;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {

        if (this.client != null && this.client.player != null && this.client.player.isDead()) {
            MiningPlayers.timeDead--;
        }

        if (this.client != null && this.client.player != null && this.client.player.isDead()) {
            if (MiningPlayers.timeDead / 20 == 0) {
                this.client.player.requestRespawn();
                MiningPlayers.timeDead = 2400;
            }
        }
    }

}
