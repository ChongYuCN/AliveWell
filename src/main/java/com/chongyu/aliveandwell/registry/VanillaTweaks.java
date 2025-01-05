package com.chongyu.aliveandwell.registry;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.world.SpawnHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class VanillaTweaks {

    //修改方块硬度
    private static void ChangeStrength(Block block, float i ) {
        try {
            Field PROPERTIES = AbstractBlock.class.getDeclaredFields()[10];
            PROPERTIES.setAccessible(true);
            AbstractBlock.Settings properties = (AbstractBlock.Settings) PROPERTIES.get(block);
            properties.strength(i, i);


            Field STATE_HARDNESS = AbstractBlock.AbstractBlockState.class.getDeclaredFields()[8];
            STATE_HARDNESS.setAccessible(true);
            for (BlockState state : block.getStateManager().getStates()) {
                STATE_HARDNESS.set(state, i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ChangeStackSize(Item item, int size) {
        //maxcount = 9
        //maxdamage = 10
        Field MAX_COUNT = Item.class.getDeclaredFields()[9];
        MAX_COUNT.setAccessible(true);
        try {
            MAX_COUNT.set(item, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void ChangeStackSizes() {
        Registries.ITEM.iterator().forEachRemaining((item) -> {
            if(item instanceof BlockItem blockItem){
                if(blockItem.getBlock() instanceof TorchBlock || blockItem.getBlock() instanceof PlantBlock || blockItem instanceof AliasedBlockItem){
                    if(item.getMaxCount() >= 32){
                        ChangeStackSize(item, 32);
                    }
                }else if(blockItem.getBlock() instanceof PlantBlock){
                    if(item.getMaxCount() >= 32){
                        ChangeStackSize(item, 32);
                    }
                }else {
                    if(item.isFood()){
                        if(item.getMaxCount() >= 16){
                            ChangeStackSize(item, 16);
                        }
                    }else if(item.getMaxCount() >= 8){
                        ChangeStackSize(item, 8);
                    }
                }
            }else {
                if(item instanceof ArrowItem){
                    if(item.getMaxCount() >= 32){
                        ChangeStackSize(item, 32);
                    }
                }else {
                    if(!Registries.ITEM.getId(item).toString().contains("bullet") && !Registries.ITEM.getId(item).toString().contains("nugget")){
                        if(item.getMaxCount() >= 16){
                            ChangeStackSize(item, 16);
                        }
                    }
                }
            }
        });
    }

    //修改物品耐久度
    private static void ChangeMaxDamage(Item item, int damage) {
        //maxcount = 9
        //maxdamage = 10
        Field MAX_DAMAGE = Item.class.getDeclaredFields()[10];
        MAX_DAMAGE.setAccessible(true);
        try {
            MAX_DAMAGE.set(item, damage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ChangeBlockStrength() {
        ChangeStrength(Blocks.CRYING_OBSIDIAN, 2.0f);
        ChangeStrength(Blocks.FURNACE, 1.0f);
        ChangeStrength(Blocks.CRAFTING_TABLE, 0.8f);
        ChangeStrength(Blocks.CRAFTING_TABLE, 0.25f);
        ChangeStrength(Blocks.HAY_BLOCK, 0.1f);
        ChangeStrength(Blocks.NETHERRACK, 4.0f);
        ChangeStrength(Blocks.CHEST, 0.3f);
    }

    public static void ChangeItemDurability() {
        ChangeMaxDamage(Items.ANVIL, 396800);
        ChangeMaxDamage(Items.FLINT_AND_STEEL, 16);

        ChangeMaxDamage(Items.WOODEN_SHOVEL, 10);
//        ChangeMaxDamage(Items.WOODEN_SWORD, 10);//60
//        ChangeMaxDamage(Items.WOODEN_AXE, 10);
//        ChangeMaxDamage(Items.WOODEN_HOE, 10);
//        ChangeMaxDamage(Items.WOODEN_PICKAXE, 6);

//        ChangeMaxDamage(Items.STONE_SHOVEL, 16);
//        ChangeMaxDamage(Items.STONE_SWORD, 32);//131
//        ChangeMaxDamage(Items.STONE_AXE, 16);
//        ChangeMaxDamage(Items.STONE_HOE, 16);
//        ChangeMaxDamage(Items.STONE_PICKAXE, 16);

//        ChangeMaxDamage(Items.IRON_SWORD, 128);  //250
//        ChangeMaxDamage(Items.IRON_HOE, 128);
//        ChangeMaxDamage(Items.IRON_SHOVEL, 128);
//        ChangeMaxDamage(Items.IRON_PICKAXE, 128);
//        ChangeMaxDamage(Items.IRON_AXE, 128);

//        ChangeMaxDamage(Items.DIAMOND_SWORD, 640);//1561
//        ChangeMaxDamage(Items.DIAMOND_HOE, 256);
//        ChangeMaxDamage(Items.DIAMOND_SHOVEL, 256);
//        ChangeMaxDamage(Items.DIAMOND_PICKAXE, 256);
//        ChangeMaxDamage(Items.DIAMOND_AXE, 256);

        ChangeMaxDamage(Items.GOLDEN_SWORD, 20);//32
        ChangeMaxDamage(Items.GOLDEN_SHOVEL, 20);
        ChangeMaxDamage(Items.GOLDEN_PICKAXE, 20);
        ChangeMaxDamage(Items.GOLDEN_AXE, 20);
        ChangeMaxDamage(Items.GOLDEN_HOE, 20);

        ChangeMaxDamage(Items.SHEARS, 476);
        ChangeMaxDamage(Items.FISHING_ROD, 32);

        ChangeMaxDamage(Items.DIAMOND_AXE, 5);
        ChangeMaxDamage(Items.DIAMOND_BOOTS, 5);
        ChangeMaxDamage(Items.DIAMOND_SWORD, 5);
        ChangeMaxDamage(Items.DIAMOND_HOE, 5);
        ChangeMaxDamage(Items.DIAMOND_CHESTPLATE, 5);
        ChangeMaxDamage(Items.DIAMOND_HELMET, 5);
        ChangeMaxDamage(Items.DIAMOND_LEGGINGS, 5);
        ChangeMaxDamage(Items.DIAMOND_PICKAXE, 5);
        ChangeMaxDamage(Items.DIAMOND_SHOVEL, 5);
    }

    public static void ApplyChanges() {
        ChangeBlockStrength();//方块硬度
        ChangeItemDurability();//物品耐久
        ChangeStackSizes();
        System.out.println("DONE!");
    }
}
