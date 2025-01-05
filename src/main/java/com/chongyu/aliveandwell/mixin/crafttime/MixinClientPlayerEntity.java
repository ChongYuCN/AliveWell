package com.chongyu.aliveandwell.mixin.crafttime;

import com.chongyu.aliveandwell.crafttime.ITimeCraftPlayer;
import com.chongyu.aliveandwell.crafttime.sound.SoundEventRegistry;
import com.chongyu.aliveandwell.crafttime.util.CraftingSpeedHelper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity extends AbstractClientPlayerEntity implements ITimeCraftPlayer {
	@Shadow @Final protected MinecraftClient client;
	@Unique
	public boolean is_crafting = false;
	@Unique
	public float craft_time = 0;
	@Unique
	public float craft_period = 0;

	public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Override
	public void setCrafting(boolean is_crafting) {
		this.is_crafting = is_crafting;
	}

	@Override
	public boolean isCrafting() {
		return this.is_crafting;
	}

	@Override
	public void setCraftTime(float craft_time) {
		this.craft_time = craft_time;
	}

	@Override
	public float getCraftTime() {
		return this.craft_time;
	}

	@Override
	public void setCraftPeriod(float craft_period) {
		this.craft_period = craft_period;
	}

	@Override
	public float getCraftPeriod() {
		return this.craft_period;
	}
	
	@Override
	public void stopCraft() {
		this.is_crafting = false;
		this.craft_time = 0F;
	}

	@Override
	public void startCraftWithNewPeriod(float craft_period) {
		this.craft_time = 0;
		this.craft_period = craft_period;
		this.is_crafting = true;


//		if (craft_period >= 10F) {
//			MinecraftClient.getInstance().getSoundManager().play(new CraftingTickableSound(Random.create(),this, this.getBlockPos()));
//		}
	}
	
	@Override
	public boolean tick(ItemStack resultStack) {

		if (this.isCrafting()) {
//			ItemStack cursorStack = this.getInventory().player.currentScreenHandler.getCursorStack();
//			if (cursorStack.getItem() != Items.AIR) {
//				if (!cursorStack.isItemEqual(resultStack)
//						|| cursorStack.getCount() + resultStack.getCount() > cursorStack.getMaxCount()) {
//					return false;
//				}
//			}
			if(resultStack.isEmpty()){
				return false;
			}

			if (this.getCraftTime() < this.getCraftPeriod()) {
				this.craft_time += CraftingSpeedHelper.getCraftingSpeed(this);
			}
			else if (this.getCraftTime() >= this.getCraftPeriod()) {
				this.playSound(SoundEventRegistry.finishSound, SoundCategory.PLAYERS, 0.1F, 1f);
				this.startCraftWithNewPeriod(craft_period);
				return true;
			}
		}
		return false;
	}

}
