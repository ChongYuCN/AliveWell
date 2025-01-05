package aliveandwell.aliveandwell.equipmentlevels.handle.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class PlayerEvents {

    public static final Event<Arrow_Loose> ON_ARROW_LOOSE = EventFactory.createArrayBacked(Arrow_Loose.class, callbacks -> (player, bow, world, charge, hasAmmo) -> {
        for (Arrow_Loose callback : callbacks) {
            int newCharge = callback.onArrowLoose(player, bow, world, charge, hasAmmo);
            if (newCharge != charge) {
                return newCharge;
            }
        }
        return -1;
    });

    public static final Event<PlayerInventoryInsert> PLAYER_INVENTORY_INSERT = EventFactory.createArrayBacked(PlayerInventoryInsert.class, callbacks -> (player,itemStack) -> {
        for (PlayerInventoryInsert callback : callbacks) {
            callback.onPlayerInventoryInsert(player,itemStack);
        }
    });

    @FunctionalInterface
    public interface Arrow_Loose {
        int onArrowLoose(PlayerEntity player, @NotNull ItemStack bow, World world, int charge, boolean hasAmmo);
    }

    @FunctionalInterface
    public interface PlayerInventoryInsert {
        void onPlayerInventoryInsert(PlayerEntity player,@NotNull ItemStack itemStack);
    }
}
