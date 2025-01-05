package com.chongyu.aliveandwell.registry;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.flintcoppertool.armor.CopperArmorBase;
import com.chongyu.aliveandwell.flintcoppertool.item.CopperToolBase;
import com.chongyu.aliveandwell.flintcoppertool.item.FlintToolBase;
import com.chongyu.aliveandwell.flintcoppertool.item.HoeBase;
import com.chongyu.aliveandwell.item.*;
import com.chongyu.aliveandwell.item.exitem.*;
import com.chongyu.aliveandwell.item.food.EnchantedGoldenCarrotItem;
import com.chongyu.aliveandwell.item.food.JuHuaGao;
import com.chongyu.aliveandwell.item.food.SalaItem;
import com.chongyu.aliveandwell.item.tool.ExPickaxe;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ItemInit {
//    public static final Item UNDER_TELEPORTER = new UnderWorldTeleporter(new Item.Settings().maxCount(1));
    public static final Item WATER_BOWL = new WaterBowl(new Item.Settings().maxCount(16).recipeRemainder(Items.BOWL).fireproof());
    public static final Item ITEM_EN_GENSTONE = new Item(new Item.Settings().maxCount(16).fireproof());
    public static final Item INGOT_WUJIN = new Item(new Item.Settings().maxCount(16).fireproof());
    public static final Item INGOT_MITHRIL = new Item(new Item.Settings().maxCount(16).fireproof());
    public static final Item ROW_MITHRIL = new Item(new Item.Settings().maxCount(16).fireproof());
    public static final Item ROW_ADAMANTIUM = new Item(new Item.Settings().maxCount(16).fireproof());
    public static final Item INGOT_ADAMANTIUM = new Item(new Item.Settings().maxCount(16).fireproof());
    public static final Item FLINT_AND_STEEL = new Item(new Item.Settings().maxDamage(20).fireproof());
    //核心
    public static final Item ELYTRA_CORE = new Item(new Item.Settings().fireproof().fireproof());
    public static final Item MITHRIL_CORE = new Item(new Item.Settings().fireproof().fireproof());
    public static final Item ADAMANTIUM_CORE = new Item(new Item.Settings().fireproof().fireproof());
    public static final Item ARGENT_CORE = new Item(new Item.Settings().fireproof().fireproof());
    public static final Item SKELETON_CORE = new Item(new Item.Settings().fireproof().fireproof());
    public static final Item FS = new Item(new Item.Settings().maxCount(16).fireproof());

    //经验币
    public static final Item EX_COPPER = new ExItemCopper();
    public static final Item EX_GOLD = new ExItemGold();
    public static final Item EX_DIAMOND = new ExItemDiamond();
    public static final Item EX_MITHRIL = new ExItemMithril();
    public static final Item EX_ADAMAN = new ExItemAdaman();

    public static final Item REBORN_STONE = new RebornStone(new Item.Settings().maxCount(16).fireproof());
    public static final Item JUHUAGAO = new JuHuaGao(new Item.Settings().maxCount(16).food( (new FoodComponent.Builder()).hunger(1).saturationModifier(0.3F).build()));
    public static final Item ENCHANTED_GOLDEN_CARROT = new EnchantedGoldenCarrotItem(new Item.Settings().maxCount(16).fireproof().food( (new FoodComponent.Builder()).hunger(2).saturationModifier(1.2f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,4*20*60,3), 1.0f).build()));
    //面团
    public static final Item MIANTUAN = new JuHuaGao(new Item.Settings().maxCount(16).food( (new FoodComponent.Builder()).hunger(1).saturationModifier(0.3F).build()));
    public static final Item QUQI_MIANTUAN = new JuHuaGao(new Item.Settings().maxCount(16).food( (new FoodComponent.Builder()).hunger(1).saturationModifier(0.3F).build()));
    public static final Item SALA = new SalaItem(new Item.Settings().maxCount(16).food( (new FoodComponent.Builder()).hunger(2).saturationModifier(0.5F).build()));
    public static final Item EX_PICKAXE = new ExPickaxe(new Item.Settings().fireproof());
    public static final Item WUJIN_SWORD = new SwordItem(AliveToolMaterial.WUJIN,4,-2.0f,new Item.Settings().fireproof());
    public static final Item MITHRIL_SWORD = new SwordItem(AliveToolMaterial.MITHRIL,8,-2.0f,new Item.Settings().fireproof());
    public static final Item ADAMANTIUM_SWORD = new SwordItem(AliveToolMaterial.ADAMANTIUM,17,-2.0f,new Item.Settings().fireproof());
    public static final Item ANCIENT_SWORD = new SwordItem(AliveToolMaterial.ANCIENT,34,-2.0f,new Item.Settings().fireproof());

    //armors==============================
    //钨金
    public static final Item WUJIN_HELMET = new ArmorItem(AliveArmorMaterial.WUJIN, ArmorItem.Type.HELMET, new Item.Settings().fireproof());
    public static final Item WUJIN_CHESTPLATE = new ArmorItem(AliveArmorMaterial.WUJIN, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof());
    public static final Item WUJIN_LEGGINGS = new ArmorItem(AliveArmorMaterial.WUJIN, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof());
    public static final Item WUJIN_BOOTS = new ArmorItem(AliveArmorMaterial.WUJIN, ArmorItem.Type.BOOTS, new Item.Settings().fireproof());

    public static final Item WUJIN_PICKAXE=new PickaxeItem(AliveToolMaterial.WUJIN, 1,-2.8F,new Item.Settings().fireproof());
    public static final Item WUJIN_AXE= new AxeItem(AliveToolMaterial.WUJIN,5.0F,-3.0F, new Item.Settings().fireproof());
    public static final Item WUJIN_SHOVEL= new ShovelItem(AliveToolMaterial.WUJIN,1.5F,-3.0F, new Item.Settings().fireproof());
    public static final Item WUJIN_HOE=new HoeBase(AliveToolMaterial.WUJIN, new Item.Settings().fireproof());
    public static final Item WUJIN_COAL=new Item(new Item.Settings().maxCount(16).fireproof());
    public static final Item RAW_WUJIN= new Item(new Item.Settings().maxCount(16).fireproof());


    //秘银
    public static final Item MITHRIL_HELMET = new ArmorItem(AliveArmorMaterial.MITHRIL, ArmorItem.Type.HELMET, new Item.Settings().fireproof());
    public static final Item MITHRIL_CHESTPLATE = new ArmorItem(AliveArmorMaterial.MITHRIL, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof());
    public static final Item MITHRIL_LEGGINGS = new ArmorItem(AliveArmorMaterial.MITHRIL, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof());
    public static final Item MITHRIL_BOOTS = new ArmorItem(AliveArmorMaterial.MITHRIL, ArmorItem.Type.BOOTS, new Item.Settings().fireproof());
    public static final Item ADAMANTIUM_HELMET = new ArmorItem(AliveArmorMaterial.ADAMANTIUM, ArmorItem.Type.HELMET, new Item.Settings().fireproof());
    public static final Item ADAMANTIUM_CHESTPLATE = new ArmorItem(AliveArmorMaterial.ADAMANTIUM, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof());
    public static final Item ADAMANTIUM_LEGGINGS = new ArmorItem(AliveArmorMaterial.ADAMANTIUM, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof());
    public static final Item ADAMANTIUM_BOOTS = new ArmorItem(AliveArmorMaterial.ADAMANTIUM, ArmorItem.Type.BOOTS, new Item.Settings().fireproof());

    public static final ArmorMaterial COPPER_ARMOR = new CopperArmorBase();
    public static final Item copper_helmet = new ArmorItem(COPPER_ARMOR, ArmorItem.Type.HELMET, new Item.Settings());
    public static final Item copper_chestplate = new ArmorItem(COPPER_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings());
    public static final Item copper_leggings = new ArmorItem(COPPER_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings());
    public static final Item copper_boots = new ArmorItem(COPPER_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings());

    //tables
    public static final Item DEEP_RAW_COPPER = new Item(new Item.Settings().maxCount(16));
    public static final Item DEEP_RAW_IRON = new Item(new Item.Settings().maxCount(16));
    public static final Item DEEP_RAW_GOLD = new Item(new Item.Settings().maxCount(16));
    public static final Item copper_shears = new ShearsItem(new Item.Settings().maxDamage(64));

    public static final Item FLINT_SHARD = new Item(new Item.Settings());
    public static final Item FLINT_PICKAXE= new PickaxeItem(new FlintToolBase(),0,-1.8F, new Item.Settings());
    public static final Item FLINT_AXE=new AxeItem(new FlintToolBase(), 1,-2.8F,new Item.Settings());
    public static final Item FLINT_SWORD=new ShovelItem(new FlintToolBase(),2.5F,-3.0F, new Item.Settings().maxDamage(128));
    public static final Item FLINT_INGOT=new Item(new Item.Settings());
    public static final Item STRINGS=new Item(new Item.Settings());
    //粒
    public static final Item nugget_mithril = new Item(new Item.Settings().maxCount(16));
    public static final Item nugget_adamantium = new Item(new Item.Settings().maxCount(16));
    public static final Item nugget_wujin=new Item(new Item.Settings());
    public static final Item nugget_diamond = new Item(new Item.Settings().maxCount(16));
    public static final Item nugget_emerald = new Item(new Item.Settings().maxCount(16));


    public static final Item lich_spawn=new Item(new Item.Settings());
    public static final Item void_blossom_spawn=new Item(new Item.Settings());
    public static final Item draugr_boss_spawn=new Item(new Item.Settings());
    public static final Item COPPER_NUGGET=new Item(new Item.Settings());
    public static final Item COPPER_SWORD=new SwordItem(new CopperToolBase(), 3,-2.4F, new Item.Settings());
    public static final Item COPPER_SHOVEL= new ShovelItem(new CopperToolBase(),1.5F,-3.0F, new Item.Settings());
    public static final Item COPPER_PICKAXE=new PickaxeItem(new CopperToolBase(), 1,-2.8F,new Item.Settings());
    public static final Item COPPER_AXE= new AxeItem(new CopperToolBase(),7.0F,-3.2F, new Item.Settings());
    public static final Item COPPER_HOE=new HoeBase(AliveToolMaterial.COPPER, new Item.Settings());

    //古城指南针
    public static final Item COMPASS_ANCIENT_CITY =new CompassAncientCity(new FabricItemSettings().rarity(Rarity.RARE));

    public static final Item DROSS_JERKY = new Item(new Item.Settings().maxCount(16).food( (new FoodComponent.Builder()).hunger(1).saturationModifier(0.3F).build()));
    public static final Item BONE_STICK=new Item(new Item.Settings().maxCount(16).fireproof());

    public static void init() {
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "flint_and_steel"), FLINT_AND_STEEL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "lich_spawn"), lich_spawn);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "void_blossom_spawn"), void_blossom_spawn);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "draugr_boss_spawn"), draugr_boss_spawn);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "flint_shard"), FLINT_SHARD);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "flint_pickaxe"), FLINT_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "flint_axe"), FLINT_AXE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "flint_ingot"), FLINT_INGOT);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "flint_sword"), FLINT_SWORD);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "strings"), STRINGS);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_nugget"), COPPER_NUGGET);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_sword"), COPPER_SWORD);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_shovel"), COPPER_SHOVEL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_pickaxe"), COPPER_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_axe"), COPPER_AXE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_hoe"), COPPER_HOE);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_shears"), copper_shears);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ex_copper"), EX_COPPER);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ex_gold"), EX_GOLD);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ex_diamond"), EX_DIAMOND);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ex_mithril"), EX_MITHRIL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ex_adaman"), EX_ADAMAN);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "enchanted_golden_carrot"), ENCHANTED_GOLDEN_CARROT);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "mithril_core"), MITHRIL_CORE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_core"), ADAMANTIUM_CORE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "argent_core"), ARGENT_CORE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "skeleton_core"), SKELETON_CORE);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "water_bowl"), WATER_BOWL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "miantuan"), MIANTUAN);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "quqi_miantuan"), QUQI_MIANTUAN);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "nugget_wujin"), nugget_wujin);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "nugget_mithril"), nugget_mithril);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "nugget_adamantium"), nugget_adamantium);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "nugget_diamond"), nugget_diamond);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "nugget_emerald"), nugget_emerald);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "item_en_gemstone"), ITEM_EN_GENSTONE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ingot_wujin"), INGOT_WUJIN);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ingot_mithril"), INGOT_MITHRIL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "row_mithril"), ROW_MITHRIL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "row_adamantium"), ROW_ADAMANTIUM);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ingot_adamantium"), INGOT_ADAMANTIUM);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "fortified_stone"), FS);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "reborn_stone"), REBORN_STONE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "juhuagao"), JUHUAGAO);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "sala"), SALA);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ex_pickaxe"), EX_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "elytra_core"), ELYTRA_CORE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_sword"), WUJIN_SWORD);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "mithril_sword"), MITHRIL_SWORD);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_sword"), ADAMANTIUM_SWORD);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ancient_sword"), ANCIENT_SWORD);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_helmet"), copper_helmet);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_chestplate"), copper_chestplate);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_leggings"), copper_leggings);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "copper_boots"), copper_boots);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_helmet"), WUJIN_HELMET);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_chestplate"), WUJIN_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_leggings"), WUJIN_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_boots"), WUJIN_BOOTS);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_pickaxe"), WUJIN_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_axe"), WUJIN_AXE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_shovel"), WUJIN_SHOVEL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_hoe"), WUJIN_HOE);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_coal"), WUJIN_COAL);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "raw_wujin"), RAW_WUJIN);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "mithril_helmet"), MITHRIL_HELMET);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "mithril_chestplate"), MITHRIL_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "mithril_leggings"), MITHRIL_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "mithril_boots"), MITHRIL_BOOTS);

        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_helmet"), ADAMANTIUM_HELMET);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_chestplate"), ADAMANTIUM_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_leggings"), ADAMANTIUM_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_boots"), ADAMANTIUM_BOOTS);

        //tables
        Registry.register(Registries.ITEM, new Identifier("aliveandwell", "deep_raw_copper"), DEEP_RAW_COPPER);
        Registry.register(Registries.ITEM, new Identifier("aliveandwell", "deep_raw_iron"), DEEP_RAW_IRON);
        Registry.register(Registries.ITEM, new Identifier("aliveandwell", "deep_raw_gold"), DEEP_RAW_GOLD);

        Registry.register(Registries.ITEM, new Identifier("aliveandwell", "compass_ancient_city"), COMPASS_ANCIENT_CITY);

        Registry.register(Registries.ITEM, new Identifier("aliveandwell", "dross_jerky"), DROSS_JERKY);
        Registry.register(Registries.ITEM, new Identifier("aliveandwell", "bone_stick"), BONE_STICK);
    }
}
