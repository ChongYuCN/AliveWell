package aliveandwell.aliveandwell.mixins.spawnerlimit;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @Shadow private int minSpawnDelay;
    @Shadow private int maxSpawnDelay;
    @Shadow private int spawnCount;
    @Shadow private int maxNearbyEntities;
    @Shadow private int requiredPlayerRange;
    @Shadow private int spawnRange;

    @Shadow private int spawnDelay;
    @Shadow @Nullable private MobSpawnerEntry spawnEntry;

    @Unique
    private short spawns = 0;

    @Shadow
    private boolean isPlayerInRange(World world, BlockPos pos) {
        return world.isPlayerInRange((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, (double)this.requiredPlayerRange);
    }
    @Shadow
    private void updateSpawns(World world, BlockPos pos){}

    @Inject(method = "serverTick", at = @At("HEAD"), cancellable = true)
    public void serverTick(ServerWorld world, BlockPos pos, CallbackInfo ci) {
        if (!this.isPlayerInRange(world, pos)) {
            return;
        }
        if (this.spawnDelay == -1) {
            this.updateSpawns(world, pos);
        }
        if (this.spawnDelay > 0) {
            --this.spawnDelay;
            return;
        }
        boolean bl = false;
        for (int i = 0; i < this.spawnCount; ++i) {
            MobSpawnerEntry.CustomSpawnRules customSpawnRules;
            double f;
            NbtCompound nbtCompound = this.spawnEntry.getNbt();

            //counts++++++++++↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓+++++++++++++
            world.getBlockEntity(pos).markDirty();
            world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

            if (spawns >=16) {
                nbtCompound.putShort("RequiredPlayerRange", (short) 0);
                ((MobSpawnerLogic)(Object)this).readNbt(world, pos, nbtCompound);
                ((MobSpawnerLogic)(Object)this).setEntityId(EntityType.AREA_EFFECT_CLOUD);
                world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
                return;
            }
            //counts++++++++++++↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑+++++++++++++++++

            Optional<EntityType<?>> optional = EntityType.fromNbt(nbtCompound);
            if (optional.isEmpty()) {
                this.updateSpawns(world, pos);
                return;
            }
            NbtList nbtList = nbtCompound.getList("Pos", NbtElement.DOUBLE_TYPE);
            int j = nbtList.size();
            Random random = world.getRandom();
            double d = j >= 1 ? nbtList.getDouble(0) : (double)pos.getX() + (random.nextDouble() - random.nextDouble()) * (double)this.spawnRange + 0.5;
            double e = j >= 2 ? nbtList.getDouble(1) : (double)(pos.getY() + random.nextInt(3) - 1);
            double d2 = f = j >= 3 ? nbtList.getDouble(2) : (double)pos.getZ() + (random.nextDouble() - random.nextDouble()) * (double)this.spawnRange + 0.5;
            if (!world.isSpaceEmpty(optional.get().createSimpleBoundingBox(d, e, f))) continue;
            BlockPos blockPos = new BlockPos(d, e, f);


            if (!this.spawnEntry.getCustomSpawnRules().isPresent()) {
                if (!((EntityType)optional.get()).getSpawnGroup().isPeaceful() && world.getDifficulty() == Difficulty.PEACEFUL) {
                    continue;
                }
            }

            Entity entity2 = EntityType.loadEntityWithPassengers(nbtCompound, world, entity -> {
                entity.refreshPositionAndAngles(d, e, f, entity.getYaw(), entity.getPitch());
                return entity;
            });
            if (entity2 == null) {
                this.updateSpawns(world, pos);
                return;
            }
            int k = world.getNonSpectatingEntities(entity2.getClass(), new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).expand(this.spawnRange)).size();
            if (k >= this.maxNearbyEntities) {
                this.updateSpawns(world, pos);
                return;
            }
            entity2.refreshPositionAndAngles(entity2.getX(), entity2.getY(), entity2.getZ(), random.nextFloat() * 360.0f, 0.0f);
            if (entity2 instanceof MobEntity) {
                MobEntity mobEntity = (MobEntity)entity2;

//                if (this.spawnEntry.getCustomSpawnRules().isEmpty() && !mobEntity.canSpawn(world, SpawnReason.SPAWNER) || !mobEntity.canSpawn(world)) continue;

                if (this.spawnEntry.getNbt().getSize() == 1 && this.spawnEntry.getNbt().contains("id", NbtElement.STRING_TYPE)) {
                    ((MobEntity)entity2).initialize(world, world.getLocalDifficulty(entity2.getBlockPos()), SpawnReason.SPAWNER, null, null);
                }
            }
            if (!world.spawnNewEntityAndPassengers(entity2)) {
                this.updateSpawns(world, pos);
                return;
            }

            //counts++++++++++↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓+++++++++++++
            NbtCompound nbt = new NbtCompound();
            nbt = ((MobSpawnerLogic)(Object)this).writeNbt(nbt);
            String entity_string = nbt.get("SpawnData").toString();
            entity_string = entity_string.substring(entity_string.indexOf("\"") + 1);
            entity_string = entity_string.substring(0, entity_string.indexOf("\""));
            if(entity_string.contains("area_effect_cloud"))
                return;
            spawns++;
            //counts++++++++++++↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑+++++++++++++++++

            world.syncWorldEvent(WorldEvents.SPAWNER_SPAWNS_MOB, pos, 0);
            world.emitGameEvent(entity2, GameEvent.ENTITY_PLACE, blockPos);
            if (entity2 instanceof MobEntity) {
                ((MobEntity)entity2).playSpawnEffects();
            }
            bl = true;
        }
        if (bl) {
            this.updateSpawns(world, pos);
        }
        ci.cancel();
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void injectReadNbt(World world, BlockPos pos, NbtCompound nbt, CallbackInfo ci) {
        this.minSpawnDelay = 60;
        this.maxSpawnDelay = 120;
        this.spawnCount = 5;
        this.maxNearbyEntities = 8;
        this.requiredPlayerRange = 20;
        this.spawnRange = 5;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getShort(Ljava/lang/String;)S"), method = "readNbt")
    private void readNbt(World world, BlockPos pos, NbtCompound nbt, CallbackInfo info) {
        spawns = nbt.getShort("counts_spawns");
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putShort(Ljava/lang/String;S)V"), method = "writeNbt")
    private void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        nbt.putShort("counts_spawns", spawns);
    }

}
