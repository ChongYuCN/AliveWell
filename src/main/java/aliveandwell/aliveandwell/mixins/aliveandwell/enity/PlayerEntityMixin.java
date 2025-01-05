package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import aliveandwell.aliveandwell.registry.ItemInit;
import aliveandwell.aliveandwell.util.*;
import com.mojang.datafixers.util.Either;
import net.levelz.data.LevelLists;
import net.levelz.stats.PlayerStatsManager;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    private int lavaTime = 0;
    @Unique
    private int damageTime = 0;
    @Unique
    private int damageTime1 = 0;
    @Unique
    private int hungerTime = 0;

    @Unique
    private float cold_lerp = 0;
    @Unique
    boolean hot = false;
    @Unique
    boolean cold = false;
    @Shadow
    protected HungerManager hungerManager;

    @Shadow
    public int experienceLevel;
    @Shadow protected int enchantmentTableSeed;

//    @Unique
//    private final NbtCompound nbt = new NbtCompound();

    @Shadow @Final private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public void baseTick() {
//        ReachDistance.setReachDistance((PlayerEntity) (Object)this);

        if(PlayerEquipUtil.getWearingquantumArmorCount((PlayerEntity) (Object)this) == 4 && !this.hasStatusEffect(StatusEffects.NIGHT_VISION)){
            if(this.getStatusEffect(StatusEffects.NIGHT_VISION).getDuration() <11){
                ((PlayerEntity) (Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 30*20));
            }
        }

//        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(Math.min(80, (int)Math.floor(experienceLevel / 5) * 2 + 6));
//        this.setHealth(Math.min(120, (int)Math.floor(experienceLevel / 5) * 2 + 6));
        super.baseTick();

        int i = this.experienceLevel;
        this.totalExperience = (int) (5*i*i+5*i+this.experienceProgress*10*(this.experienceLevel+1));

        if (world.hasRain(getBlockPos())) {
            this.addExhaustion(0.004f);
        }
        if (this.isTouchingWater() || this.isSubmergedInWater) {
            this.addExhaustion(0.006f);
        }

        if (world.getDimension().ultrawarm()) {
            hot = true;
            cold = false;
        } else
        {
            float temperature = world.getBiome(getBlockPos()).value().getTemperature();
            hot = temperature > 1.5f;
            cold = temperature < 0.5f;
        }

        if (cold) {
            if (cold_lerp < 1) {
                cold_lerp += 0.01f;
            }
        } else {
            if (cold_lerp > 0) {
                cold_lerp -= 0.01f;
            }
        }

        float hunger_loss = 0.001f;

        if (cold) {
            hunger_loss = 0.004f;
        }
        if (hot) {
            hunger_loss = 0.004f;
        }
        this.addExhaustion(hunger_loss);
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void tickMovement(CallbackInfo info) {
        if (this.getHealth() < this.getMaxHealth()) {
            this.heal(0.0005F);
        }

        if (this.isSprinting()) {
            this.addExhaustion(0.003f);
        }

    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) throws IllegalAccessException {
        if(this.isInLava()) {
            lavaTime++;
        }

        boolean digging = this.isDigBlock();
        int i = this.experienceLevel;
        this.totalExperience = (int) (5*i*i+5*i+this.experienceProgress*10*(this.experienceLevel+1));

        double slow_speed = 0.8d;
        double MOVEMENT_SPEED = 0.10000000149011612D;
        if(this.hungerManager.getFoodLevel()< 1){
            Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(MOVEMENT_SPEED * slow_speed);
        }

        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue((hungerManager.getFoodLevel() + hungerManager.getSaturationLevel()) > 0 ? 1 : 0);

//        int max_health = Math.min(120, (int)Math.floor(experienceLevel / 5) * 2 + 6);
//        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(max_health);

        //中毒每10秒减1个血
        if(this.hasStatusEffect(StatusEffects.POISON) && this.getHealth() <= 1){
            damageTime1++;
            if(this.damageTime1 >=100){
                this.damage(DamageSource.MAGIC,1.0f);
                damageTime1 = 0;
            }
        }

        //每3秒减1个血
        if(this.hasStatusEffect(StatusEffects.HUNGER)){
            hungerTime++;
            if(this.hungerTime >=120 ){
                this.addExhaustion(2.0f);
                hungerTime = 0;
            }
        }

        if(this.isInLava()){
            lavaTime++;
            if(lavaTime>=20){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE,20,0));
                this.getEquippedStack(EquipmentSlot.HEAD).damage(8,this.random,null);
                this.getEquippedStack(EquipmentSlot.CHEST).damage(8,this.random,null);
                this.getEquippedStack(EquipmentSlot.LEGS).damage(8,this.random,null);
                this.getEquippedStack(EquipmentSlot.LEGS).damage(8,this.random,null);
                lavaTime = 0;
            }
        }

        //20秒回复1血
        if(this.isSleeping()){
            this.heal(0.01f);
            if(digging){
                this.wakeUp();
                this.sendMessage(Text.translatable("aliveandwell.playerentity.info1").formatted(Formatting.RED));
            }
        }
    }

    @Overwrite
    public int getXpToDrop() {
        if (this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) || this.isSpectator()) {
            return 0;
        }
        int i = this.experienceLevel;
        return (int)((5*i*i+5*i+this.experienceProgress*10*(this.experienceLevel+1))*1/2);
    }

    @Inject(at = @At("RETURN"), method = "getBlockBreakingSpeed", cancellable = true)
    private void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> ca) {
        float speed = this.getInventory().getBlockBreakingSpeed(block);

        ItemStack itemStack = this.getMainHandStack();
        if((block.getBlock() instanceof PillarBlock) && (block.getMaterial() == Material.WOOD) && Registry.ITEM.getId(itemStack.getItem()).toString().equals("mcdw:staff_battlestaff")){
            speed = -1 ;
            ca.setReturnValue(speed);
        }
        if((block.getBlock() instanceof PillarBlock) && (block.getMaterial() == Material.WOOD) &&  !(itemStack.getItem() instanceof AxeItem) && !(Registry.ITEM.getId(itemStack.getItem()).toString().equals("doom:argent_paxel") || Registry.ITEM.getId(itemStack.getItem()).toString().equals("doom:argent_axe"))){
            speed = -1 ;
            ca.setReturnValue(speed);
        }

        if(itemStack.getItem() instanceof MiningToolItem){
            MiningToolItem miningToolItem = (MiningToolItem) (itemStack.getItem());
            int i = miningToolItem.getMaterial().getMiningLevel();
            if (i < 3 && block.isIn(BlockTags.NEEDS_DIAMOND_TOOL) && !(block.getBlock() == Blocks.OBSIDIAN || block.getBlock() == Blocks.CRYING_OBSIDIAN)) {
                speed = -1;
            } else if (i < 2 && block.isIn(BlockTags.NEEDS_IRON_TOOL) && !(block.getBlock() instanceof EnchantingTableBlock
                    || block.getBlock() instanceof AnvilBlock || block.getBlock() instanceof FurnaceBlock
            )) {
                speed = -1;
            } else if(i < 1 && block.isIn(BlockTags.NEEDS_STONE_TOOL)//燧石镐、熔炉、铁砧、附魔台不受影响
                    && !(block.getBlock() instanceof EnchantingTableBlock || block.getBlock() instanceof AnvilBlock || block.getBlock() instanceof FurnaceBlock
            )){
                speed = -1 ;
            }
            ca.setReturnValue(speed);
        }

        if( itemStack.getItem() == ItemInit.FLINT_PICKAXE && block.isIn(BlockTags.COPPER_ORES)){
            speed = -1 ;
            ca.setReturnValue(speed);
        }

        if(this.hungerManager.getFoodLevel()==0){
            if(block.getBlock() != Blocks.GRASS){
                speed = speed/8;
            }
        }

        if (speed > 1.0F) {
            int i = EnchantmentHelper.getEfficiency(this);
            speed += (float)(i * i + 1);
            if (i > 0 && !itemStack.isEmpty()) {
                speed = speed/8 + ((float)(i * i + 1))/15;
            }else {
                speed = speed/8;
            }
        }else {
            speed = speed/8.0F;
        }

        double j = (this.experienceLevel+0.000001)/100;
        if (j>=1.5){
            speed =speed*1.5F;
        }else {
            speed  =(float) (speed*(1+j));
        }

        if(Registry.ITEM.getId(itemStack.getItem()).toString().equals("doom:argent_pickaxe")
                || Registry.ITEM.getId(itemStack.getItem()).toString().equals("doom:argent_paxel")){
            if (speed > 1.0F) {
                int i = EnchantmentHelper.getEfficiency(this);
                speed += (float)(i * i + 1);
                if (i > 0 && !itemStack.isEmpty()) {
                    speed = speed/8 + ((float)(i * i + 1))/15;
                }else {
                    speed = speed/8;
                }
            }else {
                speed = speed/8.0F;
            }
            double m = (this.experienceLevel+0.000001)/100;
            if (m>=1.5){
                speed =speed*1.5F;
            }else {
                speed  =(float) (speed*(1+m));
            }
        }

        if(Registry.ITEM.getId(itemStack.getItem()).toString().equals("modern_industrialization:steam_mining_drill")){
            if(this.experienceLevel < 45){
                speed = speed/2;
            }
        }

        if(this.getMainHandStack().getItem() instanceof PickaxeItem){
            if(Registry.BLOCK.getId(block.getBlock()).toString().contains("coal")){
                speed = speed*2;
            }
        }

        if(this.getMainHandStack().getItem() instanceof PickaxeItem pickaxeItem){
            if(block.getBlock() == Blocks.OBSIDIAN || block.getBlock() == Blocks.CRYING_OBSIDIAN
//                    || block.getBlock() == BlockInit.HARD_GLASS || block.getBlock() == BlockInit.HARD_GLASS_WHITE || block.getBlock() == BlockInit.HARD_GLASS_ORANGE
//                    || block.getBlock() == BlockInit.HARD_GLASS_MAGENTA || block.getBlock() == BlockInit.HARD_GLASS_LIGHT_BLUE || block.getBlock() == BlockInit.HARD_GLASS_YELLOW
//                    || block.getBlock() == BlockInit.HARD_GLASS_LIME || block.getBlock() == BlockInit.HARD_GLASS_PINK || block.getBlock() == BlockInit.HARD_GLASS_GRAY
//                    || block.getBlock() == BlockInit.HARD_GLASS_LIGHT_GRAY || block.getBlock() == BlockInit.HARD_GLASS_CYAN || block.getBlock() == BlockInit.HARD_GLASS_PURPLE
//                    || block.getBlock() == BlockInit.HARD_GLASS_BLUE || block.getBlock() == BlockInit.HARD_GLASS_BROWN || block.getBlock() == BlockInit.HARD_GLASS_GREEN
//                    || block.getBlock() == BlockInit.HARD_GLASS_RED || block.getBlock() == BlockInit.HARD_GLASS_BLACK
            ){
                speed = speed*60;
            }

            if(block.getBlock() == Blocks.NETHERRACK){
                if(pickaxeItem.getMaterial().getMiningLevel() < 4){
                    speed = -1 ;
                }else {
                    speed = speed/4 ;
                }
            }
        }
        if(!(this.getMainHandStack().getItem() instanceof ToolItem)){
            if( block.getBlock() instanceof EnchantingTableBlock || block.getBlock() instanceof AnvilBlock || block.getBlock() instanceof FurnaceBlock || block.getBlock() instanceof SmithingTableBlock){
                speed = speed*15;
            }
        }

        //levelZ
        if (PlayerStatsManager.listContainsItemOrBlock(((PlayerEntity) (Object)this), Registry.BLOCK.getRawId(block.getBlock()), 1)){
            speed = -1;
            ca.setReturnValue(speed);
        }

        if(this.isTouchingWater() || this.isSubmergedInWater()){
            speed = speed/6;
        }

        if(block.getMaterial() == Material.WOOD ){
            if(itemStack.getItem() instanceof MiningToolItem toolItem){
                if(toolItem instanceof AxeItem){
                    speed = speed*2;
                }
            }
        }
        if(block.getMaterial() == Material.SOIL || block.getMaterial() == Material.AGGREGATE){
            if(itemStack.getItem() instanceof MiningToolItem toolItem){
                if(toolItem instanceof ShovelItem){
                    speed = speed*1.2f;
                }
            }
        }

        if(block.getBlock() instanceof ChestBlock){
            speed = speed*1.2f;
        }
        if(block.getMaterial() == Material.WOOD ){
            if(itemStack.getItem() instanceof MiningToolItem toolItem){
                if(toolItem instanceof AxeItem){
                    speed = speed*2;
                }
            }
        }
//        if(this.world.getRegistryKey() != DimsRegistry.UNDER_WORLD_KEY){
//            if(block.getBlock() == Blocks.STONE
//                    || block.getBlock() == Blocks.GRANITE
//                    || block.getBlock() == Blocks.DIORITE
//                    || block.getBlock() == Blocks.ANDESITE
//                    || block.getBlock() == Blocks.DEEPSLATE
//                    || block.getBlock() == Blocks.CALCITE
//                    || block.getBlock() == Blocks.TUFF
//                    || block.getBlock() == Blocks.DRIPSTONE_BLOCK
//            ){
//                speed = speed/4;
//            }
//        }

        ca.setReturnValue(speed);
    }

    @Shadow
    public PlayerInventory getInventory() {
        return null;
    }
    @Shadow public abstract void addExhaustion(float exhaustion);
    @Shadow public abstract void wakeUp();
    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);
    @Shadow public abstract Text getName();
    @Shadow public float experienceProgress;
    @Shadow public int totalExperience;
    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);
    @Shadow public abstract void sendMessage(Text message, boolean overlay);
    @Shadow public abstract boolean isInvulnerableTo(DamageSource damageSource);
    @Shadow public abstract void tick();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract void remove(RemovalReason reason);

    @Shadow public abstract void addExperience(int experience);

    @Shadow protected boolean isSubmergedInWater;

    @Shadow public abstract Either<PlayerEntity.SleepFailureReason, Unit> trySleep(BlockPos pos);

    @Inject(at=@At("HEAD"), method="jump", cancellable = true)
    public void jump(CallbackInfo info) {
        super.jump();

        if (this.isSprinting()) {
            this.addExhaustion(0.006F);
        } else {
            this.addExhaustion(0.004F);
        }
        info.cancel();
    }

    protected void dropXp( ) {
        if (!(this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) && this.world instanceof ServerWorld && !this.isExperienceDroppingDisabled() && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.shouldDropXp() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
            if(!this.world.isClient){
                int dropXP = this.getXpToDrop();
//                int i = dropXP/ EatOreAddExperience.ITEM_EN_GENSTONE_XP;//经验石数量

                try {
                    aliveAndWell$spawn((ServerWorld)this.world, this.getPos(), dropXP,(PlayerEntity) (Object)this);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Overwrite
    @Nullable
    public ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
        if (stack.isEmpty()) {
            return null;
        }
        if (this.world.isClient) {
            this.swingHand(Hand.MAIN_HAND);
        }
        double d = this.getEyeY() - (double)0.3f;
        ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), d, this.getZ(), stack);
        itemEntity.setPickupDelay(40);
        if (retainOwnership) {
            itemEntity.setThrower(this.getUuid());
        }
        if (throwRandomly) {
            float f = this.random.nextFloat() * 0.2f;//0.5
            float g = this.random.nextFloat() * ((float)(Math.PI * 2));//2
            itemEntity.setVelocity(-MathHelper.sin(g) * f, 0.2f, MathHelper.cos(g) * f);
        } else {
            float f = 0.3f;//0.3
            float g = MathHelper.sin(this.getPitch() * ((float)Math.PI / 180));
            float h = MathHelper.cos(this.getPitch() * ((float)Math.PI / 180));
            float i = MathHelper.sin(this.getYaw() * ((float)Math.PI / 180));
            float j = MathHelper.cos(this.getYaw() * ((float)Math.PI / 180));
            float k = this.random.nextFloat() * ((float)Math.PI * 2);
            float l = 0.02f * this.random.nextFloat();
            itemEntity.setVelocity((double)(-i * h * 0.3f) + Math.cos(k) * (double)l, -g * 0.3f + 0.1f + (this.random.nextFloat() - this.random.nextFloat()) * 0.1f, (double)(j * h * 0.3f) + Math.sin(k) * (double)l);
        }
        return itemEntity;
    }

    @Unique
    private void aliveAndWell$spawn(ServerWorld world, Vec3d pos, int amount, @Nullable PlayerEntity ownerPlayer) throws NoSuchMethodException {
        while (amount > 0) {
            int i = ExperienceOrbEntity.roundToOrbSize(amount);
            amount -= i;

            if (!ExperienceOrbEntity.wasMergedIntoExistingOrb(world, pos, i)) {
                ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), i);
                if (ownerPlayer != null) {
                    ((ExperienceOrbUtil)(Object)experienceOrbEntity).setOwnerUuid(ownerPlayer.getUuid());
                }
                world.spawnEntity(experienceOrbEntity);
            }
        }
    }

    @Overwrite
    public int getNextLevelExperience() {
        return 10*(this.experienceLevel+1);
    }

    @Overwrite
    public int getEnchantmentTableSeed() {
        return this.random.nextInt();
    }

    @Overwrite
    public void applyEnchantmentCosts(ItemStack itemStack, int enchantLevel) {
        if(enchantLevel>=44){
            addExperience(-10000);
        }else if(itemStack.getItem() == Items.GOLDEN_APPLE || itemStack.getItem() == Items.GOLDEN_CARROT) {
            addExperience(-500);
        }else {
            addExperience(- Math.max((int) (5*enchantLevel*enchantLevel+5*enchantLevel), 500));
        }

        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experienceProgress = 0.0F;
            this.totalExperience = 0;
        }
        this.enchantmentTableSeed = this.random.nextInt();
    }

    //僵尸正在挖方块
    @Unique
    public boolean isDigBlock() throws IllegalAccessException {
        LivingEntity entity = this.world.getClosestEntity(HostileEntity.class, TargetPredicate.DEFAULT,this,this.getX(),this.getY(),this.getZ(),new Box(this.getX()-4,this.getY()-4,this.getZ()-4,this.getX()+4,this.getY()+4,this.getZ()+4));
        if(entity instanceof ZombieEntity zombieEntity){
            if(GetIsDestroyingBlock.getIsDestroyingBlock(zombieEntity)){
                return true;
            }
        }
        return false;
    }

    @Inject(at = @At("RETURN"), method = "getEquippedStack", cancellable = true)
    public void getEquippedStack(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if (slot.getType() == EquipmentSlot.Type.ARMOR && inventory.armor.get(slot.getEntitySlotId()).getItem() instanceof ArmorItem) {
            String key = Registry.ITEM.getId(inventory.armor.get(slot.getEntitySlotId()).getItem()).toString();
            int damage = this.inventory.armor.get(slot.getEntitySlotId()).getMaxDamage() - this.inventory.armor.get(slot.getEntitySlotId()).getDamage();

            //levelz的armor
            ArrayList<Object> levelList = LevelLists.customItemList;
            boolean b1 = true;
            boolean b2 = true;
            if (!levelList.isEmpty() && levelList.contains(Registry.ITEM.getId(inventory.armor.get(slot.getEntitySlotId()).getItem()).toString())) {
                if (!PlayerStatsManager.playerLevelisHighEnough((PlayerEntity)(Object)this, levelList, Registry.ITEM.getId(inventory.armor.get(slot.getEntitySlotId()).getItem()).toString(), true)) {
                    b1=false;
                }
            } else {
                levelList = LevelLists.armorList;
                if (!PlayerStatsManager.playerLevelisHighEnough((PlayerEntity)(Object)this, levelList, ((ArmorItem)inventory.armor.get(slot.getEntitySlotId()).getItem()).getMaterial().getName().toLowerCase(), true)) {
                    b1=false;
                }
            }

            cir.setReturnValue(damage <=1 && !key.contains("modern_industrialization:quantum_") || !b1 || !b2 ? ItemStack.EMPTY : this.inventory.armor.get(slot.getEntitySlotId()));
        }
    }
}
