package aliveandwell.aliveandwell.item.food;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedGoldenCarrotItem extends Item {
    public EnchantedGoldenCarrotItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
