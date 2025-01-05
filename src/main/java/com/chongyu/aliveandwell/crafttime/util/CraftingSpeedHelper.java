package com.chongyu.aliveandwell.crafttime.util;

import net.minecraft.entity.player.PlayerEntity;

public class CraftingSpeedHelper {
	
	public static float getCraftingSpeed(PlayerEntity player) {
		float speed = 1F;
		speed += 0.02F * Math.min(200, player.experienceLevel);
		return speed;
	}
	
}
