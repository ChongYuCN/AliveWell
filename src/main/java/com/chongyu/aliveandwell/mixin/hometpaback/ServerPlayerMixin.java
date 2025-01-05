package com.chongyu.aliveandwell.mixin.hometpaback;

import com.chongyu.aliveandwell.hometpaback.HomeComponent;
import com.chongyu.aliveandwell.hometpaback.util.IStoreHome;
import com.chongyu.aliveandwell.xpgui.network.PlayerStatsServerPacket;
import com.mojang.authlib.GameProfile;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin  extends PlayerEntity implements IStoreHome {
    public ServerPlayerMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Shadow public abstract void sendMessage(Text message);

    @Shadow @Final public MinecraftServer server;
    @Unique
    private List<HomeComponent> homes = new ArrayList<>();
    @Unique
    private int maxHomes;

    @Unique
    public Vec3d backPos = null;
    @Unique
    public RegistryKey<World> backDimension = null;

//    @Unique private int plusHealth;

    @Inject(at = @At("RETURN"), method = "readCustomDataFromNbt")
    public void readAdditionalSaveData(NbtCompound nbt, CallbackInfo ci) {
        homes.clear();
        NbtList list = nbt.getList("homes_homeback", NbtCompound.COMPOUND_TYPE);
        for (NbtElement t : list) {
            NbtCompound homeNbt = (NbtCompound) t;
            HomeComponent home = new HomeComponent(homeNbt.getDouble("x"),homeNbt.getDouble("y"), homeNbt.getDouble("z"),homeNbt.getFloat("pitch"),homeNbt.getFloat("yaw"), RegistryKey.of(RegistryKeys.WORLD, new Identifier(homeNbt.getString("dim"))),homeNbt.getString("name"));
            homes.add(home);
        }

        maxHomes = nbt.getInt("maxHomes_homeback");

        NbtCompound data = null;
        if (nbt.contains("back_homeback")) {
            data = nbt.getCompound("back_homeback");
        }
        if (data != null && data.contains("x") && data.contains("y") && data.contains("z") && data.contains("dim")) {
            backPos = new Vec3d(data.getDouble("x"), data.getDouble("y"), data.getDouble("z"));
            backDimension = RegistryKey.of(RegistryKeys.WORLD, new Identifier(data.getString("dim")));
        }

//        plusHealth = nbt.getInt("plusHealth");
    }

    @Inject(at = @At("RETURN"), method = "writeCustomDataToNbt")
    public void addAdditionalSaveData(NbtCompound nbt, CallbackInfo ci) {
        NbtList homeTag = new NbtList();
        homes.forEach(homeComponent -> {
            if(homeComponent != null){
                NbtCompound data = new NbtCompound();
                data.putDouble("x",homeComponent.getX());
                data.putDouble("y",homeComponent.getY());
                data.putDouble("z",homeComponent.geyZ());
                data.putFloat("pitch",homeComponent.getPitch());
                data.putFloat("yaw",homeComponent.getYaw());
                data.putString("dim",homeComponent.getDimID().getValue().toString());
                data.putString("name",homeComponent.getName());
                homeTag.add(data);
            }
        });
        nbt.put("homes_homeback", homeTag);
        nbt.putInt("maxHomes_homeback", maxHomes);

        if (backPos != null && backDimension != null) {
            NbtCompound data = new NbtCompound();
            data.putDouble("x", backPos.x);
            data.putDouble("y", backPos.y);
            data.putDouble("z", backPos.z);
            data.putString("dim", backDimension.getValue().toString());
            nbt.put("back_homeback", data);
        }

//        nbt.putInt("plusHealth",plusHealth);
    }

//    @Inject(method = "onSpawn", at = @At(value = "TAIL"))
//    private void onSpawnMixin(CallbackInfo info) {
//        if(FabricLoader.getInstance().isModLoaded("levelz") || FabricLoader.getInstance().isModLoaded("puffish_skills") || FabricLoader.getInstance().isModLoaded("playerex")){
//            Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH))
//                    .setBaseValue(this.getMaxHealth()- plusHealth);
//            plusHealth = 0;
//        }
//    }

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = ((ServerPlayerEntity)(Object)this);
        backPos = new Vec3d(player.getX(), player.getY(), player.getZ());
        backDimension = player.getWorld().getRegistryKey();
        player.sendMessage(Text.translatable("aliveandwell.hometpaback.setback").append(Text.of("("+player.getBlockPos().getX()+","+ player.getBlockPos().getY()+","+player.getBlockPos().getZ()+")"+"【"+backDimension.getValue().getPath()+"】")).formatted(Formatting.LIGHT_PURPLE));

//        plusHealth  = (this.experienceLevel/5) * 2;
//        this.sendMessage(Text.translatable("死亡时plusHealth=" + plusHealth));
    }

    @Inject(at = @At("RETURN"), method = "copyFrom")
    public void restoreFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayerMixin serverPlayerMixin = (ServerPlayerMixin) (Object) oldPlayer;
        homes = serverPlayerMixin.homes;
        backPos = serverPlayerMixin.backPos;
        backDimension = serverPlayerMixin.backDimension;

//        plusHealth = serverPlayerMixin.plusHealth;
    }

    @Override
    public List<HomeComponent> getHomes() {
        return homes;
    }

    @Override
    public int getMaxHomes() {
        return maxHomes;
    }

    @Override
    public boolean addHome(HomeComponent home) {
        if (homes.stream().anyMatch(v -> v.getName().equalsIgnoreCase(home.getName()))) return false;
        return homes.add(home);
    }

    @Override
    public boolean removeHome(String name) {
        if (homes.stream().noneMatch(v -> v.getName().equalsIgnoreCase(name))) return false;
        return homes.removeIf(v -> v.getName().equalsIgnoreCase(name));
    }

    @Override
    public void addBack(Vec3d pos, RegistryKey<World> dimension) {
        backPos = pos;
        backDimension = dimension;
    }
    @Override
    public Pair<Vec3d, RegistryKey<World>> getBack() {
        return Pair.of(backPos, backDimension);
    }
}
