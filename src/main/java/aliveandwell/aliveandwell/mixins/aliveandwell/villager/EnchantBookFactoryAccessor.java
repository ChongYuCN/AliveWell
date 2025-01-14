package aliveandwell.aliveandwell.mixins.aliveandwell.villager;

import net.minecraft.village.TradeOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TradeOffers.EnchantBookFactory.class)
public interface EnchantBookFactoryAccessor {
    @Accessor("experience")
    int getExperience();
}
