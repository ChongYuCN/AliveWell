package com.chongyu.aliveandwell.item.exitem;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ExItemDiamond extends Item {
    public static final int MAX_STORAGE = 50000;
    public  int giveXP=500;

    private final Random random = new Random();

    public ExItemDiamond() {
        super(new Settings().maxCount(1).maxDamage(MAX_STORAGE).fireproof());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        //存储玩家经验
        if(player.isSneaking() && getXPStored(stack) != MAX_STORAGE) {
            int playerXP = player.totalExperience;
            if(playerXP < giveXP) {
                player.sendMessage(Text.translatable("aliveandwell.xpcard.noxp").append(Text.of(String.valueOf(giveXP))).append(Text.translatable("aliveandwell.xpcard.noxpsave")).formatted(Formatting.RED));
                return TypedActionResult.success(stack);
            }

            //存储玩家经验，每次存100
            int ex = addXP(stack,giveXP);
            player.addExperience(-ex);
            if(!world.isClient)
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F, (random.nextFloat() - random.nextFloat()) * 0.35F + 0.9F);

            return TypedActionResult.success(stack);
        }
        // 返还经验
        else if(!player.isSneaking() && getXPStored(stack) != 0) {
            if(getXPStored(stack) >= giveXP) {
                player.addExperience(giveXP);
                setStoredXP(stack, getXPStored(stack)-giveXP);
            }
            else {
                player.sendMessage(Text.translatable("aliveandwell.xpcard.noxp").append(Text.of(String.valueOf(giveXP))).append(Text.translatable("aliveandwell.xpcard.noxpget")).formatted(Formatting.RED));
                return TypedActionResult.success(stack);
            }

            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0F, 1.0F);

            return TypedActionResult.success(stack);
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public void onCraft(ItemStack stack, World worldIn, PlayerEntity playerIn)
    {
        stack.setDamage(MAX_STORAGE);
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return getXPStored(stack) > 0;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean canRepair(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

    public int addXP(ItemStack stack, int amount) {
        int stored = getXPStored(stack);

        if(stored + amount > MAX_STORAGE) {
            setStoredXP(stack, MAX_STORAGE);
            return MAX_STORAGE - stored;
        }
        else {
            setStoredXP(stack, stored + amount);
            return amount;
        }
    }

    public void setStoredXP(ItemStack stack, int amount)
    {
        stack.setDamage(MAX_STORAGE - amount);
    }

    public int getXPStored(ItemStack stack)
    {
        return MAX_STORAGE - stack.getDamage();
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("aliveandwell.xpcard.tooltip1").formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("aliveandwell.xpcard.tooltip2").append(Text.of(String.valueOf(giveXP))).append(Text.translatable("aliveandwell.xpcard.tooltip3")).formatted(Formatting.GREEN));

        tooltip.add(Text.translatable("aliveandwell.xpcard.tooltip4", getXPStored(itemStack)).formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("aliveandwell.xpcard.tooltip5").append(Text.of(String.valueOf(giveXP))).append(Text.translatable("aliveandwell.xpcard.tooltip3")).formatted(Formatting.GREEN));
        tooltip.add(Text.translatable("aliveandwell.xpcard.tooltip6").append(Text.of(String.valueOf(getXPStored(itemStack)))).append(Text.translatable("/")).append(Text.of(String.valueOf(MAX_STORAGE))).formatted(Formatting.YELLOW));
    }
}
