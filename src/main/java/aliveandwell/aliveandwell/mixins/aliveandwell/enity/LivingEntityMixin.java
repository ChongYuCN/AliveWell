package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.accessor.IEntityNbt;
import aliveandwell.aliveandwell.dimensions.DimsRegistry;
import aliveandwell.aliveandwell.registry.ItemInit;
import aliveandwell.aliveandwell.util.PlayerEquipUtil;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected abstract void initDataTracker();

    @Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);

    @Shadow  protected int playerHitTimer;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(at=@At("HEAD"), method="getJumpVelocity", cancellable = true)
    private void getJumpVelocity(CallbackInfoReturnable<Float> ca) {
        if((LivingEntity)(Object)this instanceof PlayerEntity){
            ca.setReturnValue(0.42F * this.getJumpVelocityMultiplier()+0.045f);//0.42F
        }
    }

//    @Inject(method = "getMaxHealth", at = @At(value = "RETURN"), cancellable = true)
//    public void setMaxHealth(CallbackInfoReturnable<Float> cir) {
//        LivingEntity livingEntity = (LivingEntity) (Object) this;
//        if (livingEntity instanceof PlayerEntity player) {
//            int highestTier = (int)Math.floor((double) Math.min(player.experienceLevel, 200) / 5) * 2;
//            cir.setReturnValue(cir.getReturnValue() + highestTier);
//        }
//    }

    @Inject(method = "getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D", at = @At(value = "TAIL"), cancellable = true)
    public void overridePlayerAttack(EntityAttribute attribute, CallbackInfoReturnable<Double> cir) {
        if (attribute.equals(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;
            if (livingEntity instanceof PlayerEntity player) {
                int highestTier = (int)Math.floor((double) Math.min(player.experienceLevel, 200) / 5) * 2;
                double bonusDamage = (double) highestTier /10;
                cir.setReturnValue(cir.getReturnValue() + bonusDamage);
            }
        }
//        if (attribute.equals(EntityAttributes.GENERIC_MAX_HEALTH)) {
//            LivingEntity livingEntity = (LivingEntity) (Object) this;
//            if (livingEntity instanceof PlayerEntity player) {
//                int highestTier = (int)Math.floor((double) Math.min(player.experienceLevel, 200) / 5) * 2;
//                cir.setReturnValue(cir.getReturnValue() + highestTier);
//            }
//        }
    }

    @Inject(at=@At("HEAD"), method="computeFallDamage", cancellable = true)
    public void computeFallDamage(float fallDistance, float damageMultiplier,CallbackInfoReturnable<Integer> ca) {
        StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffects.JUMP_BOOST);
        float f = statusEffectInstance == null ? 0.0f : (float)(statusEffectInstance.getAmplifier() + 1.0f);
        if(fallDistance > 6 ){
            ca.setReturnValue((int) (MathHelper.ceil((fallDistance - 3.0f - f) * damageMultiplier) * 5));
        }else {
            ca.setReturnValue((int) (MathHelper.ceil((fallDistance - 3.0f - f) * damageMultiplier) * 1.2));
        }
    }

    @Shadow
    @Nullable
    public StatusEffectInstance getStatusEffect(StatusEffect effect) {
        return null;
    }

    @Inject(at=@At("TAIL"), method="modifyAppliedDamage", cancellable = true)
    private void modifyAppliedDamage(DamageSource source, float amount,CallbackInfoReturnable<Float> ca) {
        //被攻击的是玩家，即玩家所受伤害
        if((LivingEntity)(Object)this instanceof PlayerEntity player){

            float overwoldB = (float) AliveAndWellMain.day  * 0.006f;
            float otherB = (float) AliveAndWellMain.day  *  0.0002f;

            float a =1.0f;
            float overwold =overwoldB * a;
            float other =otherB * a;


            if(overwold <= 0) overwold = 0;
            if(other <= 0) other = 0;

            if(overwold >= 3) overwold = 3;
            if(other >= 3) other = 3;

            Entity entity = source.getAttacker();
            if(entity !=null){
                if(player.getWorld().getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
                    amount = amount * (4.0f)*(1.0f+otherB);//5
                } else if(player.getWorld().getRegistryKey() == World.NETHER){
                    amount = amount * (4.2f);//5
                }else if(player.getWorld().getRegistryKey() == World.OVERWORLD){
                    amount = amount * (1.0f+overwold);//5
                }else if(player.getWorld().getRegistryKey().getValue().toString().contains("twilightforest") || player.getWorld().getRegistryKey().getValue().toString().contains("paradise_lost")){
                    amount =  amount*(3.8f);
                    if(entity instanceof SilverfishEntity){
                        amount =  amount*(1.5f);
                    }
                }else if(player.getWorld().getRegistryKey().getValue().toString().contains("ad_astra")){
                    amount =  amount*(2.2f);
                }else if(player.getWorld().getRegistryKey().getValue().toString().contains("minecells")){
                    amount =  amount*(12.0f);
                }else if(player.getWorld().getRegistryKey().getValue().toString().contains("deeperdarker") && !entity.getName().toString().contains("minecraft.warden")){
                    amount =  amount*(4.0f);
                }else{
                    amount =  amount*(2.2f+other);
                }

                //如果是指定boss，则伤害为5倍。
                String name1 = Registry.ENTITY_TYPE.getId(entity.getType()).toString();
                if(name1.contains("bosses_of_mass_destruction:gauntlet")
                        || name1.contains("bosses_of_mass_destruction:lich")
//                        || name1.contains("minecraft:warden")
                        || (name1.contains("minecraft:wither") && !name1.contains("skeleton"))
//                            || name1.contains("minecraft:zombie")//test=============================================
                ){
                    amount =  amount*5;
                }

                if(name1.contains("adventurez:stone_golem")){
                    amount =  amount*5;//8
                }

                if(name1.contains("doom:icon_of_sin")){
                    amount =  amount*5;//8
                }

                if(name1.contains("doom:arch_maykr")){
                    amount =  amount*5;//8
                }
                if(name1.contains("doom:gladiator")){
                    amount =  amount*5;//8
                }

                if(name1.contains("doom:motherdemon")){
                    amount =  amount*5;//8
                }

                if(name1.contains("bosses_of_mass_destruction:void_blossom")){
                    amount =  amount*5;//8
                }

//                if(name1.contains("minecells:conjunctivius")){
//                    amount =  amount*6;//8
//                }

                if(name1.contains("bosses_of_mass_destruction:obsidilith")){
                    amount =  amount*5;
                }

                if(name1.contains("soulsweapons:chaos_monarch")
                        || name1.contains("soulsweapons:draugr_boss")
                        || name1.contains("soulsweapons:returning_knight")
                        || name1.contains("soulsweapons:accursed_lord_boss")
                        || name1.contains("soulsweapons:moonknight")){
                    amount =  amount*5;//8
                }

                //test
//                player.sendMessage(Text.translatable("正在攻击玩家的怪物名字："+entity.getName().toString()));//test=====================================
//                player.sendMessage(Text.translatable("对玩家造成的伤害："+amount));//test=====================================
            }

            if(PlayerEquipUtil.getWearingDoomArmorCount(player) == 1){
                amount =amount-amount*0.2f;//40%
            }else if(PlayerEquipUtil.getWearingDoomArmorCount(player) == 2){
                amount =amount-amount*0.6f;//50%
            }else if(PlayerEquipUtil.getWearingDoomArmorCount(player) == 3){
                amount =amount-amount*0.7f;//60%
            }else if(PlayerEquipUtil.getWearingDoomArmorCount(player) == 4){
                amount =amount-amount*0.8f;//70%
//                player.sendMessage(Text.translatable("亚金套"+ amount));//test======================================
            }
//            player.sendMessage(Text.translatable("一"+ amount));
            if(PlayerEquipUtil.getWearingAamanArmorCount(player) == 1){
                amount =amount-amount*0.2f;//15%
//                player.sendMessage(Text.translatable("1===="+ amount));
            }else if(PlayerEquipUtil.getWearingAamanArmorCount(player) == 2){
                amount =amount-amount*0.3f;//20%
//                player.sendMessage(Text.translatable("2===="+ amount));
            }else if(PlayerEquipUtil.getWearingAamanArmorCount(player) == 3){
                amount =amount-amount*0.4f;//25%
//                player.sendMessage(Text.translatable("3===="+ amount));
            }else if(PlayerEquipUtil.getWearingAamanArmorCount(player) == 4){
                amount =amount-amount*0.5f;//30%
//                player.sendMessage(Text.translatable("爱德曼套"+ amount));//test========================
            }
            ca.setReturnValue(amount);
        }

        //被打的不是玩家，而是指定boss时，伤害减半。
        if(this != null){
            String name = Registry.ENTITY_TYPE.getId(this.getType()).toString();
            if(source.getAttacker() instanceof PlayerEntity player ){

                int count = Objects.requireNonNull(player.getServer()).getPlayerManager().getCurrentPlayerCount();
//            String name = ((LivingEntity)(Object)this).getName().toString();

                if(count > 2){
                    if(name.contains("doom:arch_maykr")
                            || name.contains("minecells:conjunctivius")
//                            || name.contains("bosses_of_mass_destruction:gauntlet")
                            || name.contains("doom:gladiator")
                            || name.contains("doom:motherdemon")
                            || name.contains("adventurez:stone_golem")
                            || name.contains("bosses_of_mass_destruction:lich")
//                        || name.contains("minecraft:warden")
                            || name.contains("minecraft:wither") && !name.contains("skeleton")
                            || name.contains("lich")
                            || name.contains("naga")
                            || name.contains("snow_queen")
                            || name.contains("mored_giant")
                            || name.contains("yeti")
                            || name.contains("minoshroom")
//                    || name.contains("minecraft:zombie")//test===============================================
                    ){
                        amount =amount/5;
                    }

                    if(name.contains("drongon") && name.contains("minecraft:")){
                        amount =amount/4;
                    }

                    if(name.contains("doom:icon_of_sin")){
                        amount =  amount/5;//8
                    }

                    if(name.contains("soulsweapons:chaos_monarch")
                            || name.contains("soulsweapons:draugr_boss")
                            || name.contains("soulsweapons:accursed_lord_boss")
                            || name.contains("soulsweapons:moonknight")){
                        amount =amount/5;
                    }
                    if(name.contains("soulsweapons:returning_knight")){
                        if(!(player.getMainHandStack().getItem() == ItemInit.ADAMANTIUM_SWORD || player.getOffHandStack().getItem() == ItemInit.ADAMANTIUM_SWORD)){
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,2*20,3));
                        }
//                    player.sendMessage(Text.translatable("被打怪物名字"+ "======================"));
                        amount =amount/5;
                    }

                    if(name.contains("invade:raider_knight")
                            || name.contains("illagerinvasion:invokert")){
                        amount =amount/5;
                    }

                    if(name.contains("bosses_of_mass_destruction:obsidilith")){
                        amount =amount/5;
                    }
                    if(name.contains("bosses_of_mass_destruction:void_blossom")){
                        amount =amount/5;
                    }
                }else {
                    if(name.contains("doom:arch_maykr")
                            || name.contains("minecells:conjunctivius")
//                            || name.contains("bosses_of_mass_destruction:gauntlet")
                            || name.contains("doom:gladiator")
                            || name.contains("doom:motherdemon")
                            || name.contains("adventurez:stone_golem")
                            || name.contains("bosses_of_mass_destruction:lich")
//                        || name.contains("minecraft:warden")
                            || name.contains("minecraft:wither") && !name.contains("skeleton")
                            || name.contains("lich")
                            || name.contains("naga")
                            || name.contains("snow_queen")
                            || name.contains("mored_giant")
                            || name.contains("yeti")
                            || name.contains("minoshroom")
//                    || name.contains("minecraft:zombie")//test===============================================
                    ){
                        amount =amount/4;
                    }

                    if(name.contains("drongon") && name.contains("minecraft:")){
                        amount =amount/3;
                    }

                    if(name.contains("doom:icon_of_sin")){
                        amount =  amount/4;//8
                    }

                    if(name.contains("soulsweapons:chaos_monarch")
                            || name.contains("soulsweapons:draugr_boss")
                            || name.contains("soulsweapons:accursed_lord_boss")
                            || name.contains("soulsweapons:moonknight")){
                        amount =amount/4;
                    }
                    if(name.contains("soulsweapons:returning_knight")){
                        if(!(player.getMainHandStack().getItem() == ItemInit.ANCIENT_SWORD || player.getOffHandStack().getItem() == ItemInit.ANCIENT_SWORD)){
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,2*20,3));
                        }
//                    player.sendMessage(Text.translatable("被打怪物名字"+ "======================"));
                        amount =amount/4;
                    }

                    if(name.contains("invade:raider_knight")
                            || name.contains("illagerinvasion:invokert")){
                        amount =amount/4;
                    }

                    if(name.contains("bosses_of_mass_destruction:obsidilith")){
                        amount =amount/4;
                    }
                    if(name.contains("bosses_of_mass_destruction:void_blossom")){
                        amount =amount/4;
                    }
                }

                if(name.contains("minecraft:zombie")){
                    amount = amount*1.5f;
                }

                //test
//            player.sendMessage(Text.translatable("当前世界名字"+player.world.getRegistryKey().getValue().toString()));
//            player.sendMessage(Text.translatable("被打怪物名字"+ name));
//            player.sendMessage(Text.translatable("玩家打怪伤害"+ amount));
                ca.setReturnValue(amount);
            }

            if(source.getAttacker() != null){
                String nameAttacker = Registry.ENTITY_TYPE.getId(source.getAttacker().getType()).toString();
                if(name.contains("doom:arch_maykr")
                        || name.contains("minecells:conjunctivius")
                        || name.contains("bosses_of_mass_destruction:gauntlet")
                        || name.contains("doom:gladiator")
                        || name.contains("doom:motherdemon")
                        || name.contains("adventurez:stone_golem")
                        || name.contains("bosses_of_mass_destruction:lich")
                        || name.contains("minecraft:wither") && !name.contains("skeleton")
                        || name.contains("lich")
                        || name.contains("naga")
                        || name.contains("snow_queen")
                        || name.contains("mored_giant")
                        || name.contains("yeti")
                        || name.contains("minoshroom")
                        || name.contains("doom:icon_of_sin")
                        || name.contains("soulsweapons:chaos_monarch")
                        || name.contains("soulsweapons:draugr_boss")
                        || name.contains("soulsweapons:accursed_lord_boss")
                        || name.contains("soulsweapons:moonknight")
                        || name.contains("soulsweapons:returning_knight")
                        || name.contains("invade:raider_knight")
                        || name.contains("illagerinvasion:invokert")
                        || name.contains("bosses_of_mass_destruction:obsidilith")
                        || name.contains("bosses_of_mass_destruction:void_blossom")
//                    || name.contains("minecraft:zombie")//test===============================================
                ){
                    if(source == DamageSource.FALL
                            || source == DamageSource.DROWN
                            || source == DamageSource.CACTUS
                            || source == DamageSource.STARVE
                            || source == DamageSource.IN_WALL
                            || source == DamageSource.IN_FIRE
                            || source == DamageSource.LAVA
                            || source == DamageSource.FREEZE
                            || source == DamageSource.ANVIL
                            || source == DamageSource.SWEET_BERRY_BUSH
                            || nameAttacker.contains("rlovelyr")
                            || nameAttacker.contains("golem")
                            || nameAttacker.contains("hwg:")
                            || nameAttacker.contains("pswg:")
                    ){
                        ca.setReturnValue(0.0f);
                    }
                }
            }
        }

    }

    @Inject(at=@At("HEAD"), method="damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this != null && source.getAttacker() instanceof PlayerEntity player){
            String name = Registry.ENTITY_TYPE.getId(this.getType()).toString();
            if (name.contains("rlovelyr:")) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(at=@At("TAIL"), method="onDeath")
    public void onDeath(DamageSource damageSource,CallbackInfo ca) {
        if (this.world instanceof ServerWorld) {
            Entity entity = damageSource.getAttacker();
            if(entity instanceof PlayerEntity player){
                if((LivingEntity) (Object)this instanceof HostileEntity){
                    if(this.onKilledOther((ServerWorld)this.world,(LivingEntity) (Object)player) ){
                        if(random.nextInt(80)+1  <= 3){
                            ItemStack stack = new ItemStack(ItemInit.REBORN_STONE);
                            setNbt(stack);
                            ((LivingEntity) (Object)this).dropStack(stack,1);
                        }
                    }
                }
                if((LivingEntity) (Object)this instanceof WitherSkeletonEntity){
                    if(this.onKilledOther((ServerWorld)this.world,(LivingEntity) (Object)player) ){
                        if(random.nextInt(40)+1  <= 3){
                            ((LivingEntity) (Object)this).dropStack(new ItemStack(ItemInit.SKELETON_CORE,1));
                        }
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
            nbt.putString("aliveandwell_reborn_stone",this.getUuidAsString());
        }else {
            nbt = itemStack.getNbt();
        }
        itemStack.setNbt(nbt);
    }
}
