package aliveandwell.aliveandwell.mixins.aliveandwell.villager;

import aliveandwell.aliveandwell.util.TradeOfferSelf;
import aliveandwell.aliveandwell.config.CommonConfig;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer{

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="interactMob",cancellable = true)
    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> ca) {
        //地狱堡垒
        if(player instanceof ServerPlayerEntity player1){
            if(CommonConfig.c){
                Advancement advancement15 = player1.server.getAdvancementLoader().get(Identifier.tryParse("minecraft:nether/obtain_ancient_debris"));
                if(advancement15 != null){
                    AdvancementProgress advancementProgress = player1.getAdvancementTracker().getProgress(advancement15);
                    if(!advancementProgress.isDone()){
                        this.sayNo();
                        player.sendMessage(Text.translatable("aliveandwell.villager.info1").formatted(Formatting.YELLOW));
                        ca.setReturnValue(ActionResult.success(this.world.isClient));
                    }
                }
            }else {
                this.sayNo();
                player.sendMessage(Text.translatable("aliveandwell.villager.info2").formatted(Formatting.YELLOW));
                ca.setReturnValue(ActionResult.success(this.world.isClient));
            }
        }
    }

    @Shadow
    private void sayNo() {
        this.setHeadRollingTimeLeft(40);
        if (!this.world.isClient()) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
        }

    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void fillRecipes() {
        VillagerData villagerData = this.getVillagerData();
        Int2ObjectMap int2ObjectMap = (Int2ObjectMap) TradeOfferSelf.PROFESSION_TO_LEVELED_TRADE.get(villagerData.getProfession());
        if (int2ObjectMap != null && !int2ObjectMap.isEmpty()) {
            TradeOffers.Factory[] factorys = (TradeOffers.Factory[])int2ObjectMap.get(villagerData.getLevel());
            if (factorys != null) {
                TradeOfferList tradeOfferList = this.getOffers();
                this.fillRecipesFromPool(tradeOfferList, factorys, 2);
            }
        }
    }

//    @Overwrite
//    private void prepareOffersFor(PlayerEntity player) {
//        int i = this.getReputation(player);
//        if (i != 0) {
//            Iterator var3 = this.getOffers().iterator();
//
//            while(var3.hasNext()) {
//                TradeOffer tradeOffer = (TradeOffer)var3.next();
//                tradeOffer.increaseSpecialPrice(-MathHelper.floor((float)i * tradeOffer.getPriceMultiplier()));
//            }
//        }
//
//        if (player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)) {
//            StatusEffectInstance statusEffectInstance = player.getStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
//            int j = statusEffectInstance.getAmplifier();
//            Iterator var5 = this.getOffers().iterator();
//
//            while(var5.hasNext()) {
//                TradeOffer tradeOffer2 = (TradeOffer)var5.next();
//                double d = 0.3 + 0.0625 * (double)j;
//                int k = (int)Math.floor(d * (double)tradeOffer2.getOriginalFirstBuyItem().getCount());
//                tradeOffer2.increaseSpecialPrice(-Math.max(k, 1));
//            }
//        }
//
//    }
//
//    @Shadow
//    public int getReputation(PlayerEntity player) {
//        return ((VillagerEntityAccessor)(Object)this).getGossip().getReputationFor(player.getUuid(), (gossipType) -> {
//            return true;
//        });
//    }
}
