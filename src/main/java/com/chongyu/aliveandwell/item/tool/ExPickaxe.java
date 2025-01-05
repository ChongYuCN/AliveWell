package com.chongyu.aliveandwell.item.tool;

import com.chongyu.aliveandwell.item.AliveToolMaterial;
import com.chongyu.aliveandwell.registry.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static net.minecraft.block.Block.dropStack;

public class ExPickaxe extends PickaxeItem {

    public ExPickaxe( Settings settings) {
        super(AliveToolMaterial.EN_GENSTONE, 1, -2.8f, settings);
    }

//    @Override
//    public ActionResult useOnBlock(ItemUsageContext context) {
//        World world = context.getWorld();
//        BlockPos blockPos = context.getBlockPos();
//        Block block = world.getBlockState(blockPos).getBlock();
//        if(block == BlockInit.ORE_MITHRIL || block == BlockInit.ORE_MITHRIL_DEEPSLATE || block == BlockInit.ORE_ADAMANTIUM || block == BlockInit.ORE_ADAMANTIUM_NETHER){
//            world.removeBlock(blockPos,true);
//            dropStack(world, blockPos, new ItemStack(block.asItem(),1));
//            context.getPlayer().addExhaustion(0.05f);
//
//            context.getStack().damage(1,context.getPlayer(), (player1) -> {
//                context.getPlayer().sendToolBreakStatus(context.getHand());
//            });
//            world.playSound(context.getPlayer(),blockPos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
//            return ActionResult.PASS;
//        }
//        return super.useOnBlock(context);
//    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        int i = this.getMaterial().getMiningLevel()/2;
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            Block block = state.getBlock();
            if(this.isSuitableFor(this.getDefaultStack(),state)){
                if(block instanceof ExperienceDroppingBlock){
                    Map<Enchantment, Integer> map = EnchantmentHelper.get(stack);
                    //是否有精准采集。
                    boolean hasSilkTouch = false ;
                    for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
                        Enchantment enchantment = entry.getKey();
                        if(enchantment == Enchantments.SILK_TOUCH){
                            hasSilkTouch = true;
//                            miner.sendMessage(Text.translatable(enchantment.getTranslationKey()));
                        }
                    }
                    if(!hasSilkTouch){
                        if(miner instanceof PlayerEntity){
                            PlayerEntity player = (PlayerEntity) miner;
                            ExperienceOrbEntity.spawn((ServerWorld) world,new Vec3d(pos.getX(),pos.getY(),pos.getZ()),5+(int) (player.experienceLevel/5));
                            if(block == BlockInit.ORE_EX){
                                world.spawnEntity(new ItemEntity((ServerWorld)world,miner.getX(),miner.getY(),miner.getZ(), new ItemStack(Items.EXPERIENCE_BOTTLE,new Random().nextInt(5))));
                            }
                            //levelZ
//                            LevelExperienceOrbEntity.spawn((ServerWorld)world, new Vec3d(pos.getX(),pos.getY(),pos.getZ()), 1+(int) (player.experienceLevel/10) >= 5 ? 5 : 1);
                        }
                    }
                }
            }
            stack.damage(1+i*2, miner, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.aliveandwell.ex_pickaxe.tooltip_0").formatted(Formatting.LIGHT_PURPLE));
//        tooltip.add(Text.translatable("item.aliveandwell.ex_pickaxe.tooltip_1").formatted(Formatting.LIGHT_PURPLE));
    }
}
