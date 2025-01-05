package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import net.minecraft.entity.ExperienceOrbEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExperienceOrbEntity.class)
public interface ExperienceOrbEntityAssessor {
    @Accessor("pickingCount")
    int getPickingCount();

    @Accessor("orbAge")
    int getOrbAge();

    @Accessor("amount")
    int getAmount();

    @Accessor("amount")
    void setAmount(int i);
}
