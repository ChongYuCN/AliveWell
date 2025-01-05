package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ModStatus;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin {
    @Shadow @Nullable
    public ClientPlayerEntity player;
    @Shadow
    @Nullable
    private IntegratedServer server;
    @Shadow
    private boolean connectedToRealms;
    @Shadow
    @Nullable
    private ServerInfo currentServerEntry;

    @Overwrite
    private String getWindowTitle() {
        StringBuilder stringBuilder = new StringBuilder("Minecraft");
        if (getModStatus().isModded()) {
            stringBuilder.append("*");
        }

        stringBuilder.append(" ");
        stringBuilder.append(SharedConstants.getGameVersion().getName());
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.getNetworkHandler();
        if (clientPlayNetworkHandler != null && clientPlayNetworkHandler.getConnection().isOpen()) {
            stringBuilder.append(" - ");
            if (this.server != null && !this.server.isRemote()) {
                stringBuilder.append(I18n.translate("title.singleplayer", new Object[0]));
            } else if (this.isConnectedToRealms()) {
                stringBuilder.append(I18n.translate("title.multiplayer.realms", new Object[0]));
            } else if (this.server == null && (this.currentServerEntry == null || !this.currentServerEntry.isLocal())) {
                stringBuilder.append(I18n.translate("title.multiplayer.other", new Object[0]));
            } else {
                stringBuilder.append(I18n.translate("title.multiplayer.lan", new Object[0]));
            }
        }

       if(!AliveAndWellMain.VERSION.contains("-modrinth")){
            stringBuilder.append(" -危险世界求生整合包 (Dangerous World) "+ AliveAndWellMain.VERSION+" by ChongYu(重羽)");
        }

        return stringBuilder.toString();
    }

    @Shadow
    public static ModStatus getModStatus() {
        return ModStatus.check("vanilla", ClientBrandRetriever::getClientModName, "Client", MinecraftClient.class);
    }

    @Shadow
    @Nullable
    public ClientPlayNetworkHandler getNetworkHandler() {
        return this.player == null ? null : this.player.networkHandler;
    }
    @Shadow
    public boolean isConnectedToRealms() {
        return this.connectedToRealms;
    }
}
