package aliveandwell.aliveandwell.mixins.aliveandwell.world;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.data.SaveDatas;
import aliveandwell.aliveandwell.dimensions.DimsRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
    @Unique
    private static final String[] enityName = {"screecher","imp","nightmare_imp","iconofsin","painelemental","shotgunguy","chaingunner","marauder","imp2016","cyberdemon","cyberdemon2016","possessed_scientist","possessed_worker","possessed_soldier","arachnotron","zombieman","mechazombie","gore_nest","cueball","stone_imp","maykr_drone","arch_maykr","tyrant","tentacle","turret","gladiator"};
   @Shadow @Final private ServerChunkManager chunkManager;

    @Shadow @Final private MinecraftServer server;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @Shadow public abstract @NotNull MinecraftServer getServer();

    @Shadow public abstract List<ServerPlayerEntity> getPlayers();

    @Inject(at = @At("TAIL"), method = "tickEntity")
    public void tickEntity(Entity entity, CallbackInfo ca)  {
        SaveDatas serverState = SaveDatas.getServerState(Objects.requireNonNull(server));

        if(AliveAndWellMain.day>=48 && !serverState.canSpawnStructure){
            serverState.canSpawnStructure = true;
        }

        if(entity instanceof ItemEntity itemEntity){
            //主世界不掉落亚金能源。
            if (entity.world.getRegistryKey() != World.OVERWORLD) {
                if(entity.getName().toString().contains("argent_energy") ){
                    if(entity.getName().toString().contains("doom.")) {
                        if(itemEntity.getStack().hasNbt()){
                            if(!Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                                setNbt(itemEntity.getStack());
                            }
                        }else {
                            setNbt(itemEntity.getStack());
                        }
                    }
                }
            }

            if (entity.world.getRegistryKey() == World.NETHER) {
                if (entity.getName().toString().contains("netherite_scrap")
                        || entity.getName().toString().contains("blaze_rod")
                ) {
                    if (entity.getName().toString().contains("minecraft.")) {
                        if (itemEntity.getStack().hasNbt()) {
                            if (!Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                                setNbt(itemEntity.getStack());
                            }
                        }else {
                            setNbt(itemEntity.getStack());
                        }
                    }
                }
            }

            //
//            if(entity.getName().toString().contains("endrem.") ){
//                if(entity.getName().toString().contains("_eye")) {
//                    if(itemEntity.getStack().hasNbt()){
//                        if(!Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {
//                            setNbt(itemEntity.getStack());
//                        }
//                    }else {
//                        setNbt(itemEntity.getStack());
//                    }
//                }
//            }
        }

    }

    @Inject(at = @At("TAIL"), method = "tickChunk")
    public void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ca) {

        int i = (int) (AliveAndWellMain.day/5);

        int players = this.server.getCurrentPlayerCount();
        if(i>=30){
            i = 30;
        }

        if(this.getRegistryKey() == World.END){
            AliveAndWellMain.ca = 15;
        }else {
            if(AliveAndWellMain.day == 1){
                AliveAndWellMain.ca = 10;
            } else {
                if(players <= 2){
                    AliveAndWellMain.ca = 21+players+i;
                }else if(players <= 6){
                    AliveAndWellMain.ca = 18+players+i;
                }else if(players <= 10){
                    AliveAndWellMain.ca = 16+players+i;
                }else{
                    AliveAndWellMain.ca = 23+(int) (players/10)+i;
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "shouldCancelSpawn", cancellable = true)
    public void shouldCancelSpawnEvent(Entity entity, CallbackInfoReturnable<Boolean> ca) {
        if (entity instanceof LivingEntity ) {
            if(!(entity instanceof EnderDragonEntity) && ((LivingEntity) entity).getMaxHealth() >= 20000.0f){
                ca.setReturnValue(true);
            }

            SaveDatas serverState = SaveDatas.getServerState(Objects.requireNonNull(this.getServer()));
            if(!serverState.canSpawnVillager){
                if(entity.getName().toString().contains("villager") && !entity.getName().toString().contains("zombie")){
                    ca.setReturnValue(true);
                }
            }

            if (entity.getName().toString().contains("mooshroom")) {
                ca.setReturnValue(true);
            }


            if (entity.getName().toString().contains("earthtojavamobs.")) {
                if (entity.getName().toString().contains("tropical_slime")
                ) {
                    ca.setReturnValue(true);
                }
            }

            if (entity.getName().toString().contains("mobz.")) {
                if (entity.getName().toString().contains("smallzombie")
                ) {
                    ca.setReturnValue(true);
                }
            }
            if (entity.getName().toString().contains("doom.")) {
                if (entity.getName().toString().contains("blood_maykr")
                ) {
                    ca.setReturnValue(true);
                }
            }

            if (entity.getName().toString().contains("ad_astra.")) {
                if (entity.getName().toString().contains("lunarian") && !entity.getName().toString().contains("corrupted_")
                ) {
                    ca.setReturnValue(true);
                }
            }

            if (entity.getName().toString().contains("doom.")) {
                if (entity.getEntityWorld().getRegistryKey() == World.OVERWORLD) {
                    if(entity.getBlockPos().getY()>=120){
                        ca.setReturnValue(true);
                    }
                }
            }

            if (AliveAndWellMain.day <= 16) {
                if (entity.getName().toString().contains("minecraft.") || entity.getName().toString().contains("creeperoverhaul.")) {
                    if (entity.getName().toString().contains("creeper")) {
                        if (!(entity.world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY) && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")) {//除了地下世界
                            ca.setReturnValue(true);
                        }
                    }
                }
                if (entity.getName().toString().contains("minecraft.")) {
                    if (entity.getName().toString().contains("stray")) {
                        if (!(entity.getWorld().getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY)&& !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")) {//除了地下世界
                            ca.setReturnValue(true);
                        }
                    }
                }
                if (entity.getName().toString().contains("minecraft.")) {
                    if (entity.getName().toString().contains("phantom")) {
                        ca.setReturnValue(true);
                    }
                }
                if (entity.getName().toString().contains("mobz.")) {
                    if (entity.getName().toString().contains("skeli2")) {
                        if (!(entity.getWorld().getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY) && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")) {//除了地下世界
                            ca.setReturnValue(true);
                        }
                    }
                }

            }

            //32天前不生成
            if (AliveAndWellMain.day <= 20) {
                if (!entity.getName().toString().contains("minecraft.")) {
                    if (entity.getName().toString().contains("goose")
                            || entity.getName().toString().contains("boar")
                            || entity.getName().toString().contains("coyote")
                            || entity.getName().toString().contains("bear")
                            || entity.getName().toString().contains("wolf")
                    ) {
                        if (entity.getName().toString().contains("betteranimalsplus.")) {
                            ca.setReturnValue(true);
                        }
                    }
                }

                if (entity.getName().toString().contains("naturalist.")) {
                    if (entity.getName().toString().contains("lion")
                            || entity.getName().toString().contains("coyote")
                            || entity.getName().toString().contains("bear")
                    ) {
                        ca.setReturnValue(true);
                    }
                }
            }

            if (AliveAndWellMain.day <= 50) {
                if (entity.getName().toString().contains("archer")
                        || entity.getName().toString().contains("knight2")) {
                    if (entity.getName().toString().contains("mobz.")) {
                        ca.setReturnValue(true);
                    }
                }

                //哭泣天使前25天不生成
//                if (entity.getName().toString().contains("weeping_angel")) {
//                    if (entity.getName().toString().contains("weeping_angels.")) {
//                        ca.setReturnValue(true);
//                    }
//                }

                if (entity.getName().toString().contains("mobz.")) {
                    if (entity.getName().toString().contains("mage")) {
                        ca.setReturnValue(true);
                    }
                }
            }

            if (AliveAndWellMain.day <= 32) {
                if (entity.world.getRegistryKey() == World.OVERWORLD) {
                    if(entity instanceof ZombieEntity || entity instanceof AbstractSkeletonEntity || entity instanceof SpiderEntity || entity instanceof CreeperEntity || entity instanceof SilverfishEntity || entity instanceof EndermanEntity || entity instanceof WitchEntity) {
                        if (entity.hasCustomName()) {
                            ca.setReturnValue(true);
                        }
                    }
                }

                if (entity.getName().toString().contains("mythicmounts.")) {
                    ca.setReturnValue(true);
                }
                if (entity.getName().toString().contains("terrarianslimes.")) {
                    ca.setReturnValue(true);
                }
            }

            if (AliveAndWellMain.day <= 48) {
                if (entity.getWorld().getRegistryKey() == World.OVERWORLD) {
                    for (String name : enityName) {
                        if (entity.getName().toString().contains(name)) {
                                ca.setReturnValue(true);
                        }
                    }
                }
            }

            //只在地狱生成：巨脑魔、丧魂
            if (entity.getName().toString().contains("doom.")) {
                if (entity.getName().toString().contains("cacodemon")
                        || entity.getName().toString().contains("lost_soul")
                ) {
                    if (!(entity.world.getRegistryKey() == World.NETHER) ) {
                        ca.setReturnValue(true);
                    }
                }
            }

            //*****************************************************************************
            if (AliveAndWellMain.day <= 48) {
                if (entity.getName().toString().contains("doom.")) {
                    if (entity.getName().toString().contains("prowler")
                            || entity.getName().toString().contains("gargoyle")
                            || entity.getName().toString().contains("revenant")//亡魂
                            || entity.getName().toString().contains("spidermastermind")//蜘蛛首脑
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost") && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")){
                            ca.setReturnValue(true);
                        }
                    }
                }
            }

            if (AliveAndWellMain.day <= 64) {
                if (entity.getName().toString().contains("doom.")) {
                    if (entity.getName().toString().contains("stone_imp")//石皮幼魔
                            || entity.getName().toString().contains("doom_hunter")//毁灭战士猎人
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost") && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")){
                            ca.setReturnValue(true);
                        }
                    }
                }

                if (entity.getName().toString().contains("hwg.")) {
                    if (entity.getName().toString().contains("spy")
                            || entity.getName().toString().contains("merc")
                            || entity.getName().toString().contains("lesser")
                            || entity.getName().toString().contains("greater")
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost") && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")){
                            ca.setReturnValue(true);
                        }
                    }
                }

                if (entity.getName().toString().contains("gigeresque.")) {
                    ca.setReturnValue(true);
                }
            }

            if (AliveAndWellMain.day <= 72) {
                if (entity.getName().toString().contains("hwg.")) {
                    if (entity.getName().toString().contains("spy")
                            || entity.getName().toString().contains("merc")
                            || entity.getName().toString().contains("lesser")
                            || entity.getName().toString().contains("greater")
                            || entity.getName().toString().contains("motherdemon")
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost") && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")){
                            ca.setReturnValue(true);
                        }
                    }
                }
            }

            if (AliveAndWellMain.day <= 96) {//64天前不生成。
                if (entity.getName().toString().contains("doom.")) {
                    if (entity.getName().toString().contains("baron")
                            || entity.getName().toString().contains("whiplash")
                            || entity.getName().toString().contains("dreadknight")
                            || entity.getName().toString().contains("archvile")
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost") && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")){
                            ca.setReturnValue(true);
                        }
                    }
                }

                //hellknight,mancubus,arachnotroneternal
                if (entity.getName().toString().contains("doom.")) {
                    if (entity.getName().toString().contains("hellknight")
                            || entity.getName().toString().contains("mancubus")
                            || entity.getName().toString().contains("arachnotroneternal")
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost") && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")){
                            ca.setReturnValue(true);
                        }
                    }
                }
            }

            if (AliveAndWellMain.day <= 120) {//64天前不生成。
                if (entity.getName().toString().contains("doom.")) {
                    if (entity.getName().toString().contains("spectre")
                            || entity.getName().toString().contains("unwilling")
                            || entity.getName().toString().contains("summoner")//撒拉弗
                            || entity.getName().toString().contains("pinky")
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost") && !entity.getWorld().getRegistryKey().getValue().toString().contains("twilightforest")){
                            ca.setReturnValue(true);
                        }
                    }
                }
            }

            if (entity.getWorld().getRegistryKey() == World.NETHER) {
                if (entity.getName().toString().contains("doom.")) {
                    if (entity.getName().toString().contains("baron")
                            || entity.getName().toString().contains("pinky")
                            || entity.getName().toString().contains("whiplash")
                            || entity.getName().toString().contains("dreadknight")
                            || entity.getName().toString().contains("archvile")
                            || entity.getName().toString().contains("motherdemon")
                            || entity.getName().toString().contains("summoner")//撒拉弗
                    ) {
                        if(!entity.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost")){
                            ca.setReturnValue(true);
                        }
                    }
                }
            }
        }

        //================================================================================================================
        if (entity instanceof ItemEntity itemEntity) {
            if(AliveAndWellMain.day<=32){
                if (entity.getName().toString().contains("bosses_of_mass_destruction.")) {
                    if (entity.getName().toString().contains("soul_star")) {
                        ca.setReturnValue(true);
                    }
                }
            }

//            if (entity.getName().toString().contains("gobber2_gooey")) {//戈伯食物不掉落。
//                ca.setReturnValue(true);
//            }
//
//            if (entity.getName().toString().contains("simplyswords.")) {//简单刀剑
//                if (entity.getName().toString().contains("watcher_claymore") || entity.getName().toString().contains("watching_warglaive")) {
//                    ca.setReturnValue(true);
//                }
//            }

            //地下世界不掉落烈焰棒和下界合金。
            if (entity.world.getRegistryKey() != World.NETHER) {
                if (entity.getName().toString().contains("netherite_scrap")
                        || entity.getName().toString().contains("blaze_rod")
                ) {
                    if (entity.getName().toString().contains("minecraft.")) {
                        if (itemEntity.getStack().hasNbt()) {
                            if (Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                                ca.setReturnValue(false);
                            } else {
                                ca.setReturnValue(true);
                            }
                        } else {
                            ca.setReturnValue(true);
                        }
                    }
                }
            }

            if (entity.getName().toString().contains("doom.")) {
                if (entity.getName().toString().contains("argent_block")) {
                    if (itemEntity.getStack().hasNbt()) {
                        if (Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                            ca.setReturnValue(false);
                        } else {
                            ca.setReturnValue(true);
                        }
                    } else {
                        ca.setReturnValue(true);
                    }
                }
            }
            //主世界不掉落亚金能源。
            if (entity.world.getRegistryKey() != DimsRegistry.UNDER_WORLD_KEY && entity.world.getRegistryKey() != World.NETHER) {
                if (entity.getName().toString().contains("doom.")) {
                    if (entity.getName().toString().contains("argent_energy")) {
                        if (itemEntity.getStack().hasNbt()) {
                            if (Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                                ca.setReturnValue(false);
                            } else {
                                ca.setReturnValue(true);
                            }
                        } else {
                            ca.setReturnValue(true);
                        }
                    }
                }
            }
            if (entity.world.getRegistryKey() != World.NETHER) {
                if (entity.getName().toString().contains("elytra") || entity.getName().toString().contains("netherite_scrap")
                        || entity.getName().toString().contains("blaze_rod")
                ) {
                    if (entity.getName().toString().contains("minecraft.")) {
                        if (itemEntity.getStack().hasNbt()) {
                            if (Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                                ca.setReturnValue(false);
                            } else {
                                ca.setReturnValue(true);
                            }
                        } else {
                            ca.setReturnValue(true);
                        }
                    }
                }
            }

            if (entity.getName().toString().contains("boss_ingot")) {
                if (entity.getName().toString().contains("mobz.")) {
                    if (itemEntity.getStack().hasNbt()) {
                        if (Objects.requireNonNull(itemEntity.getStack().getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                            ca.setReturnValue(false);
                        } else {
                            ca.setReturnValue(true);
                        }
                    } else {
                        ca.setReturnValue(true);
                    }
                }
            }
        }
    }

    @Unique
    public void setNbt(ItemStack itemStack) {
        NbtCompound nbt;
        if(!itemStack.hasNbt()){
            nbt = new NbtCompound();
        }else {
            nbt = itemStack.getNbt();
        }
        itemStack.setSubNbt("aliveandwell",nbt);
    }
}
