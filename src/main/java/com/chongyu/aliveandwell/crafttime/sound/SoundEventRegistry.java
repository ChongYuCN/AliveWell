package com.chongyu.aliveandwell.crafttime.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundEventRegistry {
	public static final Identifier finishSoundID = new Identifier("aliveandwell:finish");
    public static SoundEvent finishSound = SoundEvent.of(finishSoundID);
    
}
