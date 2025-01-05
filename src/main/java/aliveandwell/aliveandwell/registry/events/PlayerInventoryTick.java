package aliveandwell.aliveandwell.registry.events;

import aliveandwell.aliveandwell.equipmentlevels.handle.callback.PlayerEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PlayerInventoryTick {

    private static final NbtCompound nbt = new NbtCompound();
    public static void onPlayerInventoryItemStackTick() {
        PlayerEvents.PLAYER_INVENTORY_INSERT.register(PlayerInventoryTick::banItem);
    }

    private static void banItem(PlayerEntity player,ItemStack stack) {
        if(!stack.isEmpty()){
            String name = Registry.ITEM.getId(stack.getItem()).toString();

            if(stack.getName().toString().contains("未获取到任何附魔，请关闭gui后再次附魔")){
                stack.removeCustomName();
            }

            //禁用附魔
            if( stack.getItem() != Items.ENCHANTED_BOOK){
                if(stack.hasEnchantments()){
                    NbtList nbtList = stack.getEnchantments();
                    for(int k =0;k<nbtList.size();k++){
                        NbtCompound nbtCompound = nbtList.getCompound(k);
                        Identifier identifier2 = EnchantmentHelper.getIdFromNbt(nbtCompound);
                        assert identifier2 != null;
                        if(identifier2.toString().contains("mcdw:radiance")
                                || identifier2.toString().contains("mcdw:radiance_shot")
                                || identifier2.toString().contains("mcdw:prospector")
                                || identifier2.toString().contains("mcda:heal_allies")
                                || identifier2.toString().contains("mcda:lucky_explorer")
                                || identifier2.toString().contains("mcdw:leeching")
                                || identifier2.toString().contains("mcda:reckless")
                                || identifier2.toString().contains("mcdw:anima_conduit")
                                || identifier2.toString().contains("mcda:death_barter")
                                || identifier2.toString().contains("mcdw:rushdown")
                                || identifier2.toString().contains("mcdw:tempo_theft")
                                || identifier2.toString().contains("mcdar:beast_boss")
                                || identifier2.toString().contains("mcdar:beast_burst")
                                || identifier2.toString().contains("mcdar:beast_surge")
                                || identifier2.toString().contains("mcda:swiftfooted")
                                || identifier2.toString().contains("mcdw:refreshment")
                                || identifier2.toString().contains("mcdw:void_shot")
                                || identifier2.toString().contains("mcdw:void_strike")
                                || identifier2.toString().contains("mcda:cowardice")
//                            || identifier2.toString().contains("protection")//test===================
                        ){
                            stack.getEnchantments().remove(k);
                        }
                    }
                }
            }

            //mcdw
            if(name.equals("mcdw:hammer_suns_grace")){
                stack.split(1);
            }
            if(name.equals("mcdw:bow_sabrewing")){
                stack.split(1);
            }
            if(name.equals("mcdw:bow_love_spell_bo")){
                stack.split(1);
            }
            //doom
            if (name.equals("doom:daisy")) {
                stack.split(1);
            }
            if (name.equals("doom:soulcube")) {
                stack.split(1);
            }
            if (name.equals("doom:megasphere")) {
                stack.split(1);
            }
            if (name.equals("doom:powersphere")) {
                stack.split(1);
            }
            if (name.equals("doom:inmortalsphere")) {
                stack.split(1);
            }
            if (name.equals("doom:invisiblesphere")) {
                stack.split(1);
            }

            if (name.equals("twilightforest:ore_magnet")) {
                stack.split(1);
            }

            //mcdar
            if (name.equals("mcdar:blast_fungus")) {
                stack.split(1);
            }
            if (name.equals("mcdar:harvester")) {
                stack.split(1);
            }
            if (name.equals("mcdar:lighting_rod")) {
                stack.split(1);
            }
            if (name.equals("mcdar:updraft_tome")) {
                stack.split(1);
            }
            if (name.equals("mcdar:corrupted_seeds")) {
                stack.split(1);
            }
            if (name.equals("mcdar:gong_of_weakening")) {
                stack.split(1);
            }
            if (name.equals("mcdar:satchel_of_elements")) {
                stack.split(1);
            }
            if (name.equals("mcdar:shock_powder")) {
                stack.split(1);
            }

            if (name.equals("mcdar:buzzy_nest")) {
                stack.split(1);
            }
            if (name.equals("mcdar:enchanted_grass")) {
                stack.split(1);
            }
            if (name.equals("mcdar:tasty_bone")) {
                stack.split(1);
            }
            if (name.equals("mcdar:wonderful_wheat")) {
                stack.split(1);
            }
            if (name.equals("mcdar:boots_of_swiftness")) {
                stack.split(1);
            }
            if (name.equals("mcdar:death_cap_mushroom")) {
                stack.split(1);
            }
            if (name.equals("mcdar:light_feather")) {
                stack.split(1);
            }
            if (name.equals("mcdar:enchanters_tome")) {
                stack.split(1);
            }
            if (name.equals("mcdar:iron_hide_amulet")) {
                stack.split(1);
            }
            if (name.equals("mcdar:powershaker")) {
                stack.split(1);
            }
            if (name.equals("mcdar:soul_healer")) {
                stack.split(1);
            }
            if (name.equals("mcdar:totem_of_regeneration")) {
                stack.split(1);
            }
            if (name.equals("mcdar:totem_of_shielding")) {
                stack.split(1);
            }
            if (name.equals("mcdar:totem_of_soul_protection")) {
                stack.split(1);
            }
            if (name.equals("mcdar:wind_horn")) {
                stack.split(1);
            }

            if (name.equals("byg:soapstone")) {
                stack.split(1);
            }

            if (name.equals("minecraft:bundle") ) {
                stack.split(1);
            }

            //mobz
            if (name.equals("mobz:axe") ) {
                stack.split(1);
            }
            if (name.equals("mobz:v_sword") ) {
                stack.split(1);
            }
            if (name.equals("mobz:hardenedmetal_ingot") ) {
                stack.split(1);
            }
            //简单刀剑
//            if (name.equals("simplyswords:watcher_claymore") ) {//Item.of('simplyswords:watcher_claymore', '{Damage:0,player:{}}')
//                stack.split(1);
//            }
//            if (name.equals("simplyswords:watching_warglaive") ) {//Item.of('simplyswords:watching_warglaive', '{Damage:0,player:{}}')
//                stack.split(1);
//            }
//
//            //
//            if (name.equals("gateofbabylon:stone_yoyo") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:golden_yoyo") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:diamond_yoyo") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:netherite_yoyo") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:wooden_yoyo") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:iron_yoyo") ) {
//                stack.split(1);
//            }
//
//            if (name.equals("gateofbabylon:iron_boomerang") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:stone_boomerang") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:wooden_boomerang") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:golden_boomerang") ) {
//                stack.split(1);
//            }
//
//            if (name.equals("gateofbabylon:wooden_broadsword") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:stone_broadsword") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:iron_broadsword") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:golden_broadsword") ) {
//                stack.split(1);
//            }
//
//            if (name.equals("gateofbabylon:wooden_waraxe") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:stone_waraxe") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:wooden_katana") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:stone_katana") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:wooden_rapier") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:stone_rapier") ) {
//                stack.split(1);
//            }
//
//            if (name.equals("gateofbabylon:wooden_dagger") ) {
//                stack.split(1);
//            }
//            if (name.equals("gateofbabylon:stone_dagger") ) {
//                stack.split(1);
//            }

            //植物魔法
            if(name.equals("botania:travel_belt")){//旅居者腰带
                stack.split(1);
            }
            if(name.equals("botania:super_travel_belt")){//环游腰带
                stack.split(1);
            }
            if(name.equals("botania:speed_up_belt")){//平原腰带
                stack.split(1);
            }

            if(name.equals("botania:holy_cloak")){//神圣斗篷
                stack.split(1);
            }
            if(name.equals("botania:unholy_cloak")){//罪恶斗篷
                stack.split(1);
            }
            if(name.equals("soulsweapons:sting")){//针刺
                stack.split(1);
            }
            if(name.equals("soulsweapons:guts_sword")){//针刺
                stack.split(1);
            }
            if(name.equals("soulsweapons:kirkhammer")){//针刺
                stack.split(1);
            }
            if(name.equals("soulsweapons:holy_greatsword")){//针刺
                stack.split(1);
            }
            if(name.equals("soulsweapons:moonstone_axe")){//针刺
                stack.split(1);
            }
            if(name.equals("soulsweapons:moonstone_hoe")){//针刺
                stack.split(1);
            }

            if(name.equals("soulsweapons:moonstone_pickaxe")){//针刺
                stack.split(1);
            }

            if(name.equals("soulsweapons:moonstone_shovel")){//针刺
                stack.split(1);
            }

            if(name.equals("create:crafting_blueprint")){//合成蓝图
                stack.split(1);
            }

            if(name.equals("botania:crafting_halo")){//工厂环
                stack.split(1);
            }
            if(name.equals("botania:auto_crafting_halo")){//工
                stack.split(1);
            }
            if(name.equals("botania:alchemy_catalyst")){//工
                stack.split(1);
            }
            if(name.equals("botania:enchanter")){//工
                stack.split(1);
            }

            if(name.contains("invade:") && name.contains("_helmet")){//入侵装备
                stack.split(1);
            }
            if(name.contains("invade:") && name.contains("_chestplate")){//
                stack.split(1);
            }
            if(name.contains("invade:") && name.contains("_leggings")){//
                stack.split(1);
            }
            if(name.contains("invade:") && name.contains("_boots")){//
                stack.split(1);
            }
//            if(name.equals("invade:fixer") ){//
//                stack.split(1);
//            }
            if(name.equals("invade:enchantment_extract_table") ){//
                stack.split(1);
            }
            if(name.equals("mobz:shield") ){//
                stack.split(1);
            }

            if(name.equals("create:cart_assembler") ){//
                stack.split(1);
            }

//            if (name.contains("endrem.") && name.contains("_eye")){
//                if(!stack.hasNbt()){
//                    stack.split(1);
//                }else {
//                    assert stack.getNbt() != null;
//                    if(!stack.getNbt().contains("aliveandwell")){
//                        stack.split(1);
//                    }
//                }
//            }

            //原版
            if (name.equals("minecraft:wooden_sword")
                    || name.equals("minecraft:wooden_pickaxe")
                    || name.equals("minecraft:wooden_axe")
                    || name.equals("minecraft:wooden_hoe")
                    || name.equals("minecraft:stone_sword")
                    || name.equals("minecraft:stone_shovel")
                    || name.equals("minecraft:stone_pickaxe")
                    || name.equals("minecraft:stone_axe")
                    || name.equals("minecraft:stone_hoe") ) {
                stack.split(1);
            }
            if (name.equals("minecraft:netherite_scrap") || name.equals("minecraft:blaze_rod")
                    || name.equals("minecraft:elytra")
                    || name.equals("doom:argent_block") || name.equals("doom:argent_energy")
                    || name.equals("mobz:boss_ingot") ){
                if(!stack.hasNbt()){
                    stack.setSubNbt("aliveandwell",nbt);
                }else {
                    assert stack.getNbt() != null;
                    if(!stack.getNbt().contains("aliveandwell")){
                        stack.setSubNbt("aliveandwell",nbt);
                    }
                }
            }

            if(!player.getEquippedStack(EquipmentSlot.HEAD).hasNbt()){
                player.getEquippedStack(EquipmentSlot.HEAD).setSubNbt("equip_player",nbt);
            }else {
                assert player.getEquippedStack(EquipmentSlot.HEAD).getNbt() != null;
                if(!player.getEquippedStack(EquipmentSlot.HEAD).getNbt().contains("equip_player")){
                    player.getEquippedStack(EquipmentSlot.HEAD).setSubNbt("equip_player",nbt);
                }
            }
            if(!player.getEquippedStack(EquipmentSlot.CHEST).hasNbt()){
                player.getEquippedStack(EquipmentSlot.CHEST).setSubNbt("equip_player",nbt);
            }else {
                assert player.getEquippedStack(EquipmentSlot.CHEST).getNbt() != null;
                if(!player.getEquippedStack(EquipmentSlot.CHEST).getNbt().contains("equip_player")){
                    player.getEquippedStack(EquipmentSlot.CHEST).setSubNbt("equip_player",nbt);
                }
            }
            if(!player.getEquippedStack(EquipmentSlot.LEGS).hasNbt()){
                player.getEquippedStack(EquipmentSlot.LEGS).setSubNbt("equip_player",nbt);
            }else {
                assert player.getEquippedStack(EquipmentSlot.LEGS).getNbt() != null;
                if(!player.getEquippedStack(EquipmentSlot.LEGS).getNbt().contains("equip_player")){
                    player.getEquippedStack(EquipmentSlot.LEGS).setSubNbt("equip_player",nbt);
                }
            }
            if(!player.getEquippedStack(EquipmentSlot.FEET).hasNbt()){
                player.getEquippedStack(EquipmentSlot.FEET).setSubNbt("equip_player",nbt);
            }else {
                assert player.getEquippedStack(EquipmentSlot.FEET).getNbt() != null;
                if(!player.getEquippedStack(EquipmentSlot.FEET).getNbt().contains("equip_player")){
                    player.getEquippedStack(EquipmentSlot.FEET).setSubNbt("equip_player",nbt);
                }
            }
            if( player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof ToolItem
                    ||  player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof CrossbowItem
                    ||  player.getEquippedStack(EquipmentSlot.MAINHAND).getItem() instanceof TridentItem){
                if(!player.getEquippedStack(EquipmentSlot.MAINHAND).hasNbt()){
                    player.getEquippedStack(EquipmentSlot.MAINHAND).setSubNbt("equip_player",nbt);
                }else {
                    assert  player.getEquippedStack(EquipmentSlot.MAINHAND).getNbt() != null;
                    if(!player.getEquippedStack(EquipmentSlot.MAINHAND).getNbt().contains("equip_player")){
                        player.getEquippedStack(EquipmentSlot.MAINHAND).setSubNbt("equip_player",nbt);
                    }
                }

            }
            if(stack.getItem() instanceof ToolItem || stack.getItem() instanceof ArmorItem || stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem
                    || stack.getItem() instanceof TridentItem){
                if(!stack.hasNbt()){
                    stack.setSubNbt("equip_player",nbt);
                }else {
                    assert stack.getNbt() != null;
                    if(!stack.getNbt().contains("equip_player")){
                        stack.setSubNbt("equip_player",nbt);
                    }
                }
            }
        }
    }
}
