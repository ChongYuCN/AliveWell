package aliveandwell.aliveandwell.registry;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.block.*;
import aliveandwell.aliveandwell.block.randompos.BaseAdamantiumRandomBlock;
import aliveandwell.aliveandwell.block.randompos.BaseMithrilRandomBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.util.function.ToIntFunction;

public class BlockInit {
    public static final Block ORE_MITHRIL = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE));
    public static final Block ORE_ADAMANTIUM = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE));
    public static final Block ORE_ADAMANTIUM_DEEPSLATE = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE));
    public static final Block ORE_ADAMANTIUM_NETHER = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE));
    public static final Block ORE_MITHRIL_DEEPSLATE = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE));
    public static final Block ORE_EX = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE));
    public static final Block ORE_EX_DEEPSLATE = new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE).luminance(createLightLevelFromBlockState2(10)));

    public static final Block WUJIN_ORE = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).luminance(createLightLevelFromBlockState2(10)));
    public static final Block WUJIN_ORE_DEEPSLATE = new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE).luminance(createLightLevelFromBlockState2(9)));

    public static final Block NITER_ORE = new OreBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE));
    public static final Block NITER_ORE_DEEPSLATE = new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE));

    //portals
    public static final CustomPortalBlock RANDOM_A_PORTAL = new RandomAPortalBlock(FabricBlockSettings.copy(Blocks.NETHER_PORTAL).strength(-1.0F));
    public static final CustomPortalBlock RANDOM_B_PORTAL = new RandomBPortalBlock(FabricBlockSettings.copy(Blocks.NETHER_PORTAL).strength(-1.0F));
    public static final CustomPortalBlock SPAWNPOINT_PORTAL = new SpawnpointPortalBlock(FabricBlockSettings.copy(Blocks.NETHER_PORTAL).strength(-1.0F));
    public static final CustomPortalBlock UNDERWORLD_PORTAL = new UnderworldPortalBlock(FabricBlockSettings.copy(Blocks.NETHER_PORTAL).strength(-1.0F));
    public static final CustomPortalBlock NETHERWORLD_PORTAL = new NetherworldPortalBlock(FabricBlockSettings.copy(Blocks.NETHER_PORTAL).strength(-1.0F));
    public static final Block ADAMANTIUM_BLOCK = new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK));
    public static final Block MITHRIL_BLOCK = new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK));
    public static final Block FRAME_MITHRIL = new Block(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block FRAME_ADAMANTIUM= new Block(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block FRAME_SPAWNPOINT = new Block(FabricBlockSettings.copy(Blocks.STONE).strength(3.0F, 3.0F));

    public static final Block random_mithril_jia = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_yi = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_bing = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_ding = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_wu = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_ji = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_geng = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_xin = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_ren = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_mithril_gui = new BaseMithrilRandomBlock(FabricBlockSettings.copy(Blocks.STONE));

    public static final Block random_adamantium_jia = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_yi = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_bing = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_ding = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_wu = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_ji = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_geng = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_xin = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_ren = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));
    public static final Block random_adamantium_gui = new BaseAdamantiumRandomBlock(FabricBlockSettings.copy(Blocks.STONE));

//    public static final Block HARD_GLASS = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.CLEAR).luminance(createLightLevelFromBlockState2(10)));
//    public static final Block HARD_GLASS_WHITE = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.WHITE).luminance(createLightLevelFromBlockState2(10)));//白色
//    public static final Block HARD_GLASS_ORANGE = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.ORANGE).luminance(createLightLevelFromBlockState2(10)));//橘色
//    public static final Block HARD_GLASS_MAGENTA = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.MAGENTA).luminance(createLightLevelFromBlockState2(10)));//品红
//    public static final Block HARD_GLASS_LIGHT_BLUE = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.LIGHT_BLUE).luminance(createLightLevelFromBlockState2(10)));//亮蓝色
//    public static final Block HARD_GLASS_YELLOW = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.YELLOW).luminance(createLightLevelFromBlockState2(10)));//黄色
//    public static final Block HARD_GLASS_LIME = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.LIME).luminance(createLightLevelFromBlockState2(10)));//绿黄色
//    public static final Block HARD_GLASS_PINK = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.PINK).luminance(createLightLevelFromBlockState2(10)));//粉红色
//    public static final Block HARD_GLASS_GRAY = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.GRAY).luminance(createLightLevelFromBlockState2(10)));//灰色
//    public static final Block HARD_GLASS_LIGHT_GRAY = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.LIGHT_GRAY).luminance(createLightLevelFromBlockState2(10)));//亮灰色
//    public static final Block HARD_GLASS_CYAN = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.CYAN).luminance(createLightLevelFromBlockState2(10)));//青色
//    public static final Block HARD_GLASS_PURPLE = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.PURPLE).luminance(createLightLevelFromBlockState2(10)));//紫色
//    public static final Block HARD_GLASS_BLUE = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.BLUE).luminance(createLightLevelFromBlockState2(10)));//蓝色
//    public static final Block HARD_GLASS_BROWN = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.BROWN).luminance(createLightLevelFromBlockState2(10)));//棕色
//    public static final Block HARD_GLASS_GREEN = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.GREEN).luminance(createLightLevelFromBlockState2(10)));//绿色
//    public static final Block HARD_GLASS_RED = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.RED).luminance(createLightLevelFromBlockState2(10)));//红色
//    public static final Block HARD_GLASS_BLACK = new GlassBlock(AbstractBlock.Settings.of(Material.GLASS).strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(BlockInit::never).solidBlock(BlockInit::never).suffocates(BlockInit::never).blockVision(BlockInit::never).mapColor(MapColor.BLACK).luminance(createLightLevelFromBlockState2(10)));//黑色
    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "ore_mithril"), ORE_MITHRIL);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "ore_mithril_deepslate"), ORE_MITHRIL_DEEPSLATE);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "ore_adamantium"), ORE_ADAMANTIUM);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "ore_adamantium_deepslate"), ORE_ADAMANTIUM_DEEPSLATE);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "ore_adamantium_nether"), ORE_ADAMANTIUM_NETHER);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "ore_ex"), ORE_EX);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "ore_ex_deepslate"), ORE_EX_DEEPSLATE);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_block"), ADAMANTIUM_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "mithril_block"), MITHRIL_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "wujin_ore"), WUJIN_ORE);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "wujin_ore_deepslate"), WUJIN_ORE_DEEPSLATE);

        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "niter_ore"), NITER_ORE);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "niter_ore_deepslate"), NITER_ORE_DEEPSLATE);

        //portals
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_a_portal"), RANDOM_A_PORTAL);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_b_portal"), RANDOM_B_PORTAL);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "spawnpoint_portal"), SPAWNPOINT_PORTAL);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "underworld_portal"), UNDERWORLD_PORTAL);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "netherworld_portal"), NETHERWORLD_PORTAL);

        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "frame_mithril"), FRAME_MITHRIL);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "frame_adamantium"), FRAME_ADAMANTIUM);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "frame_spawnpoint"), FRAME_SPAWNPOINT);

        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_jia"), random_mithril_jia);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_yi"), random_mithril_yi);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_bing"), random_mithril_bing);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_ding"), random_mithril_ding);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_wu"), random_mithril_wu);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_ji"), random_mithril_ji);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_geng"), random_mithril_geng);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_xin"), random_mithril_xin);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_ren"), random_mithril_ren);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_gui"), random_mithril_gui);

        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_jia"), random_adamantium_jia);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_yi"), random_adamantium_yi);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_bing"), random_adamantium_bing);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_ding"), random_adamantium_ding);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_wu"), random_adamantium_wu);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_ji"), random_adamantium_ji);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_geng"), random_adamantium_geng);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_xin"), random_adamantium_xin);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_ren"), random_adamantium_ren);
        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_gui"), random_adamantium_gui);

//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass"), HARD_GLASS);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_white"), HARD_GLASS_WHITE);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_orange"), HARD_GLASS_ORANGE);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_magenta"), HARD_GLASS_MAGENTA);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_light_blue"), HARD_GLASS_LIGHT_BLUE);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_yellow"), HARD_GLASS_YELLOW);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_lime"), HARD_GLASS_LIME);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_pink"), HARD_GLASS_PINK);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_gray"), HARD_GLASS_GRAY);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_light_gray"), HARD_GLASS_LIGHT_GRAY);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_cyan"), HARD_GLASS_CYAN);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_purple"), HARD_GLASS_PURPLE);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_blue"), HARD_GLASS_BLUE);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_brown"), HARD_GLASS_BROWN);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_green"), HARD_GLASS_GREEN);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_red"), HARD_GLASS_RED);
//        Registry.register(Registry.BLOCK, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_black"), HARD_GLASS_BLACK);

    }

    public static void registerBlockItems() {
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ore_mithril"), new BlockItem(ORE_MITHRIL, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ore_adamantium"), new BlockItem(ORE_ADAMANTIUM, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ore_adamantium_deepslate"), new BlockItem(ORE_ADAMANTIUM_DEEPSLATE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ore_adamantium_nether"), new BlockItem(ORE_ADAMANTIUM_NETHER, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ore_mithril_deepslate"), new BlockItem(ORE_MITHRIL_DEEPSLATE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ore_ex"), new BlockItem(ORE_EX, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "ore_ex_deepslate"), new BlockItem(ORE_EX_DEEPSLATE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "adamantium_block"), new BlockItem(ADAMANTIUM_BLOCK, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "mithril_block"), new BlockItem(MITHRIL_BLOCK, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_ore"), new BlockItem(WUJIN_ORE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "wujin_ore_deepslate"), new BlockItem(WUJIN_ORE_DEEPSLATE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "niter_ore"), new BlockItem(NITER_ORE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "niter_ore_deepslate"), new BlockItem(NITER_ORE_DEEPSLATE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

        //portals
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_a_portal"), new BlockItem(RANDOM_A_PORTAL, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_b_portal"), new BlockItem(RANDOM_B_PORTAL, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "spawnpoint_portal"), new BlockItem(SPAWNPOINT_PORTAL, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "underworld_portal"), new BlockItem(UNDERWORLD_PORTAL, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "netherworld_portal"), new BlockItem(NETHERWORLD_PORTAL, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "frame_mithril"), new BlockItem(FRAME_MITHRIL, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "frame_adamantium"), new BlockItem(FRAME_ADAMANTIUM, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "frame_spawnpoint"), new BlockItem(FRAME_SPAWNPOINT, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_jia"), new BlockItem(random_mithril_jia, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_yi"), new BlockItem(random_mithril_yi, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_bing"), new BlockItem(random_mithril_bing, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_ding"), new BlockItem(random_mithril_ding, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_wu"), new BlockItem(random_mithril_wu, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_ji"), new BlockItem(random_mithril_ji, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_geng"), new BlockItem(random_mithril_geng, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_xin"), new BlockItem(random_mithril_xin, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_ren"), new BlockItem(random_mithril_ren, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_mithril_gui"), new BlockItem(random_mithril_gui, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_jia"), new BlockItem(random_adamantium_jia, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_yi"), new BlockItem(random_adamantium_yi, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_bing"), new BlockItem(random_adamantium_bing, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_ding"), new BlockItem(random_adamantium_ding, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_wu"), new BlockItem(random_adamantium_wu, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_ji"), new BlockItem(random_adamantium_ji, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_geng"), new BlockItem(random_adamantium_geng, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_xin"), new BlockItem(random_adamantium_xin, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_ren"), new BlockItem(random_adamantium_ren, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "random_adamantium_gui"), new BlockItem(random_adamantium_gui, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass"), new BlockItem(HARD_GLASS, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_white"), new BlockItem(HARD_GLASS_WHITE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_orange"), new BlockItem(HARD_GLASS_ORANGE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_magenta"), new BlockItem(HARD_GLASS_MAGENTA, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_light_blue"), new BlockItem(HARD_GLASS_LIGHT_BLUE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_yellow"), new BlockItem(HARD_GLASS_YELLOW, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_lime"), new BlockItem(HARD_GLASS_LIME, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_pink"), new BlockItem(HARD_GLASS_PINK, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_gray"), new BlockItem(HARD_GLASS_GRAY, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_light_gray"), new BlockItem(HARD_GLASS_LIGHT_GRAY, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_cyan"), new BlockItem(HARD_GLASS_CYAN, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_purple"), new BlockItem(HARD_GLASS_PURPLE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_blue"), new BlockItem(HARD_GLASS_BLUE, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_brown"), new BlockItem(HARD_GLASS_BROWN, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_green"), new BlockItem(HARD_GLASS_GREEN, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_red"), new BlockItem(HARD_GLASS_RED, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));
//        Registry.register(Registry.ITEM, new Identifier(AliveAndWellMain.MOD_ID, "hard_glass_black"), new BlockItem(HARD_GLASS_BLACK, new Item.Settings().fireproof().group(ItemGroup.BUILDING_BLOCKS)));

    }

    private static ToIntFunction<BlockState> createLightLevelFromBlockState2(int litLevel) {
        return (blockState) -> litLevel;
    }
}
