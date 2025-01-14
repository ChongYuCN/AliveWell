package aliveandwell.aliveandwell.mixins.aliveandwell;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LocalDifficulty.class)
public class LocalDifficultyMixin {

//    @Final
//    @Shadow
//    private float localDifficulty;

//    @Overwrite
//    public float getClampedLocalDifficulty() {
//        if (this.localDifficulty < 2.0F) {
//            return 0.0F;
//        } else {
//            return this.localDifficulty > 4.0F ? 4.0F : (this.localDifficulty - 2.0F) / 2.0F;
//        }
//    }
//
//    @Overwrite
//    public float getLocalDifficulty() {
//        return 5.0f;
//    }

    @Overwrite
    private float setLocalDifficulty(Difficulty difficulty, long timeOfDay, long inhabitedTime, float moonSize) {
        if (difficulty == Difficulty.PEACEFUL) {
            return 0.0F;
        } else {
            boolean bl = difficulty == Difficulty.HARD;
            float f = 0.75F;
            float g = MathHelper.clamp(((float)timeOfDay + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
            f += g;
            float h = 0.0F;
            h += MathHelper.clamp((float)inhabitedTime / 3600000.0F, 0.0F, 1.0F) * (bl ? 1.0F : 0.75F);
            h += MathHelper.clamp(moonSize * 0.25F, 0.0F, g);
            if (difficulty == Difficulty.EASY) {
                h *= 0.5F;
            }

            f += h;
            float d = AliveAndWellMain.day*(10/120);
            if(d >= 10.0f){
                d = 10.0f;
            }
            f=f+d+2.47f;
            return (float)difficulty.getId() * f;
        }
    }
}
