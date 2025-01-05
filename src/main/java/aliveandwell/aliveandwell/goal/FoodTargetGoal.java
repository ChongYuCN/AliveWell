package aliveandwell.aliveandwell.goal;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.StewItem;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FoodTargetGoal extends Goal {
    @Nullable
    protected ItemEntity targetEntity;
    public boolean onlyEatsMeat;
    public boolean onlyEatsPlants;
    public MobEntity mob;

    private boolean active = false;

    public FoodTargetGoal(MobEntity mob, boolean checkVisibility) {
        this.mob = mob;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        this.findClosestTarget();
        return this.targetEntity != null;
    }

    public int getFollowRange() {
        return 8;
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0D, distance);
    }

    protected void findClosestTarget() {
        //    default <T extends Entity> List<T> getEntitiesByClass(Class<T> entityClass, Box box, Predicate<? super T> predicate) {
        var items = this.mob.world.getEntitiesByClass(
                ItemEntity.class,
                this.getSearchBox(this.getFollowRange()),
                (entity) -> {

                    if (onlyEatsPlants) {
                        if (entity instanceof ItemEntity &&
                                ((ItemEntity)entity).getStack() != null) {
                            Item item = ((ItemEntity)entity).getStack().getItem();
                            if (item == Items.WHEAT ||
                                    item == Items.GRASS ||
                                    item == Items.TALL_GRASS ||
                                    item == Items.SEAGRASS ||
                                    item == Items.DANDELION ||
                                    item == Items.KELP ) {
                                return true;
                            }
                        }
                    }

                    if (entity instanceof ItemEntity &&
                            ((ItemEntity)entity).getStack() != null &&
                            ((ItemEntity)entity).getStack().getItem().isFood()) {
                        if (!((ItemEntity)entity).getStack().getItem().getFoodComponent().isMeat() &&  onlyEatsMeat ||
                                ((ItemEntity)entity).getStack().getItem().getFoodComponent().isMeat() &&  onlyEatsPlants) {
                            return false;
                        }
                        return true;
                    }
                    return false;
                });
        float distance = Float.MAX_VALUE;
        for (ItemEntity entity : items) {
            float dist = entity.distanceTo(mob);
            if (dist < distance) {
                distance = dist;
                targetEntity = entity;
            }
        }
    }

    public void start() {
        this.mob.setTarget(null);
        this.mob.getNavigation().startMovingTo(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), 1);
        this.active = true;

        super.start();
    }

    public void stop() {
        this.active = false;
    }

    public void tick() {
        super.tick();
        if (targetEntity.distanceTo(mob) <= 1) {
            if (targetEntity.getStack().getItem() instanceof StewItem) {
                targetEntity.setStack(new ItemStack(Items.BOWL));
            } else {
                targetEntity.kill();
            }

            stop();
        }
    }

    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle() || targetEntity == null || (targetEntity != null && !targetEntity.isAlive());
    }
}