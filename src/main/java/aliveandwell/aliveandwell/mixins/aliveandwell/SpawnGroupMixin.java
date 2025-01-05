package aliveandwell.aliveandwell.mixins.aliveandwell;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.*;

@Mixin(SpawnGroup.class)
public abstract class SpawnGroupMixin  implements StringIdentifiable {
    @Mutable
    @Final
    @Shadow private final boolean peaceful;
    protected SpawnGroupMixin(boolean peaceful, int capacity, int immediateDespawnRange) {
        this.peaceful = peaceful;
        this.capacity = capacity;
        this.immediateDespawnRange = immediateDespawnRange;
    }

    @Shadow public abstract String getName();
    @Mutable
    @Shadow @Final private final int capacity;
    @Mutable
    @Shadow @Final private final int immediateDespawnRange;

    @Overwrite
    public int getCapacity() {
        if (this.getName().equals("monster")) {
            if(AliveAndWellMain.ca >= 60){
                return 60;
            }else {
                return AliveAndWellMain.ca;
            }
        }
        if (this.getName().equals("creature")) {
            return 3;
        }

        return this.capacity;
    }

}
