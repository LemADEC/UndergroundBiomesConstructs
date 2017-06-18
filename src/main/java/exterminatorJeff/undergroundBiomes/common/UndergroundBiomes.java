package exterminatorJeff.undergroundBiomes.common;
import exterminatorJeff.undergroundBiomes.api.NamedVanillaItem;
import exterminatorJeff.undergroundBiomes.api.NamedVanillaBlock;
import exterminatorJeff.undergroundBiomes.api.NamedSlabPair;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBOreTexturizer;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomesSettings;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;






import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.oredict.*;
import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLModIdMappingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import exterminatorJeff.undergroundBiomes.common.block.*;
import exterminatorJeff.undergroundBiomes.common.item.*;
import exterminatorJeff.undergroundBiomes.common.command.*;
import exterminatorJeff.undergroundBiomes.constructs.item.ItemUBStairs;
import exterminatorJeff.undergroundBiomes.constructs.util.UBCodeLocations;
import exterminatorJeff.undergroundBiomes.constructs.UndergroundBiomesConstructs;
import Zeno410Utils.Acceptor;
import Zeno410Utils.ConfigManager;
import Zeno410Utils.PlayerDetector;
import exterminatorJeff.undergroundBiomes.constructs.util.WatchList;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.registry.GameData;
import exterminatorJeff.undergroundBiomes.network.PacketPipeline;
import exterminatorJeff.undergroundBiomes.worldGen.BiomeUndergroundDecorator;
import exterminatorJeff.undergroundBiomes.worldGen.OreUBifier;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.terraingen.OreGenEvent;

@Mod(modid = "UndergroundBiomes", name = "Underground Biomes", version = "0.8.13")

public class UndergroundBiomes{
    
    public UndergroundBiomes() {
        instance = this;
    }
    public static org.apache.logging.log4j.Logger logger;
    private static UndergroundBiomes instance;
    public static UndergroundBiomes instance() {return instance;}
    public static World world;
    
    public Configuration config;

    private DimensionManager dimensionManager;

    private WatchList defaultIDSetter;
    private boolean runningConfigIDs = false;
    
    public static String textures = "/exterminatorJeff/undergroundBiomes/textures.png";
    
    public static CreativeTabModBlocks tabModBlocks;
    public static CreativeTabModBlocks tabModItems;
    
    public static long worldSeed;
    private boolean gotWorldSeed;
    public boolean gotWorldSeed() {return gotWorldSeed;}
    
    public static BlockMetadataBase igneousStone;
    public static BlockMetadataBase igneousCobblestone;
    public static BlockMetadataBase igneousStoneBrick;
    public static BlockMetadataBase metamorphicStone;
    public static BlockMetadataBase metamorphicCobblestone;
    public static BlockMetadataBase metamorphicStoneBrick;
    public static BlockMetadataBase sedimentaryStone;
    public static Item ligniteCoal;
    public static Item fossilPiece;

    public static StoneSlabPair igneousBrickSlab;
    public static StoneSlabPair metamorphicBrickSlab;

    public static StoneSlabPair igneousStoneSlab;
    public static StoneSlabPair metamorphicStoneSlab;

    public static StoneSlabPair igneousCobblestoneSlab;
    public static StoneSlabPair metamorphicCobblestoneSlab;

    public static StoneSlabPair sedimentaryStoneSlab;

    public static ArrayList<String> fortuneAffected;
    public static ArrayList<ItemStack> nuggets;

    private static String[] nuggetStrings = {
      "nuggetIron", "nuggetCopper", "nuggetTin", "nuggetSilver", "nuggetLead", "nuggetAluminium",
      "nuggetNaturalAluminium", "nuggetNickel", "nuggetPlatinum", "nuggetElectrum", "nuggetZinc",
    };


    private int igneousStoneID() {return instance().settings.igneousStoneID.value();}
    private int igneousCobblestoneID() {return instance().settings.igneousCobblestoneID.value();}
    private int igneousStoneBrickID() {return instance().settings.igneousStoneBrickID.value();}
    private int metamorphicStoneID() {return instance().settings.metamorphicStoneID.value();}
    private int metamorphicCobblestoneID() {return instance().settings.metamorphicCobblestoneID.value();}
    private int metamorphicStoneBrickID() {return instance().settings.metamorphicStoneBrickID.value();}
    private int sedimentaryStoneID() {return instance().settings.sedimentaryStoneID.value();}
    private int ligniteCoalID() {return instance().settings.ligniteCoalID.value();}
    private int fossilPieceID() {return instance().settings.fossilPieceID.value();}
    private int igneousBrickSlabHalfID() {return instance().settings.igneousBrickSlabHalfID.value();}
    private int igneousBrickSlabFullID() {return instance().settings.igneousBrickSlabFullID.value();}
    private int metamorphicBrickSlabHalfID() {return instance().settings.metamorphicBrickSlabHalfID.value();}
    private int metamorphicBrickSlabFullID() {return instance().settings.metamorphicBrickSlabFullID.value();}
    private int igneousStoneSlabHalfID() {return instance().settings.igneousStoneSlabHalfID.value();}
    private int igneousStoneSlabFullID() {return instance().settings.igneousStoneSlabFullID.value();}
    private int metamorphicStoneSlabHalfID() {return instance().settings.metamorphicStoneSlabHalfID.value();}
    private int metamorphicStoneSlabFullID() {return instance().settings.metamorphicStoneSlabFullID.value();}
    private int igneousCobblestoneSlabHalfID() {return instance().settings.igneousCobblestoneSlabHalfID.value();}
    private int igneousCobblestoneSlabFullID() {return instance().settings.igneousCobblestoneSlabFullID.value();}
    private int metamorphicCobblestoneSlabHalfID() {return instance().settings.metamorphicCobblestoneSlabHalfID.value();}
    private int metamorphicCobblestoneSlabFullID() {return instance().settings.metamorphicCobblestoneSlabFullID.value();}
    private int sedimentaryStoneSlabHalfID() {return instance().settings.sedimentaryStoneSlabHalfID.value();}
    private int sedimentaryStoneSlabFullID() {return instance().settings.sedimentaryStoneSlabFullID.value();}

    public final UBCodeLocations ubCodeLocations = new UBCodeLocations();

    private List<Integer> includeDimensionIDs;
    private List<Integer> excludeDimensionIDs;

    public final static String includeDimensions() {return instance().settings.includeDimensions.value();}
    public final static String excludeDimensions() {return instance().settings.excludeDimensions.value();}

    public final static int vanillaStoneCrafting() {return instance().settings.vanillaStoneCrafting.value();}

    public final static float hardnessModifier() {
        return instance().settings.hardnessModifier.value().floatValue();
    }

    public final static float resistanceModifier() {
        return instance().settings.resistanceModifier.value().floatValue()/3.0F;
    }
    
    public final static int generateHeight() {return instance().settings.generateHeight.value();}

    public UndergroundBiomesConstructs constructs;
    private UndergroundBiomesSettings settings = new UndergroundBiomesSettings();
    public UndergroundBiomesSettings settings() {return settings;}
    private ConfigManager<UndergroundBiomesSettings> configManager;


    public static boolean addOreDictRecipes() {return instance().settings.addOreDictRecipes.value();}
    public static boolean vanillaStoneBiomes() {return instance().settings.vanillaStoneBiomes.value();}
    public static boolean buttonsOn() {return instance().settings.buttonsOn.value();}
    public static boolean stairsOn() {return instance().settings.stairsOn.value();}
    public static boolean wallsOn() {return instance().settings.wallsOn.value();}
    public static boolean harmoniousStrata() {return instance().settings.harmoniousStrata.value();}
    public static boolean replaceCobblestone() {return instance().settings.replaceCobblestone.value();}
    public static boolean replaceVillageGravel() {return instance().settings.replaceVillageGravel.value();}
    public static boolean crashOnProblems() {return instance().settings.crashOnProblems.value();}

    public static void throwIfTesting(RuntimeException toThrow) {
        if (crashOnProblems()) throw toThrow;
    }

    public static void throwIfTesting(String toThrow) {
        if (crashOnProblems()) throw new RuntimeException(toThrow);
    }

    public static void throwIfTesting(RuntimeException toThrow,String logMessage) {
        logger.info(logMessage);
        if (crashOnProblems()) throw toThrow;
    }

    public static boolean forceConfigIds() {return instance().settings.forceConfigIds.value();}
    public static boolean ubOres() {return instance().settings.ubOres.value();}

    public static int biomeSize() {return instance().settings.biomeSize.value();}

    @SidedProxy(clientSide = "exterminatorJeff.undergroundBiomes.client.ClientProxy",
                serverSide = "exterminatorJeff.undergroundBiomes.common.CommonProxy")
    public static CommonProxy proxy;

    public static String blockCategory = "block";
    public static String itemCategory = "item";

    private OreUBifier oreUBifier;
    OreUBifier oreUBifier() {return oreUBifier;}
    public int ubOreRenderID() {return oreUBifier.getRenderID();}
    private OreUBifyRequester oreRequester = new OreUBifyRequester();

    private VanillaStoneRecipeManager vanillaStoneRecipeManager = new VanillaStoneRecipeManager(oreCobblestoneName());
    
    private PacketPipeline pipeline;
    private PlayerDetector playerDetector;
    private UndergroundBiomesNetworking networking;

	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
        if (UBIDs.version != 3) throw new RuntimeException("Another mod has included an obsolete version of the Underground Biomes API. Underground Biomes Constructs cannot run.");
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        settings.readFrom(config);
        configManager = new ConfigManager<UndergroundBiomesSettings>(
                config,settings,event.getSuggestedConfigurationFile());
        
        if (includeDimensions().equals("*"))
        {
            excludeDimensionIDs = new ArrayList<Integer>();
            for (String v : excludeDimensions().split(","))
            {
                excludeDimensionIDs.add(Integer.parseInt(v));
            }
        }
        else
        {
            includeDimensionIDs = new ArrayList<Integer>();
            for (String v : includeDimensions().split(","))
            {
                includeDimensionIDs.add(Integer.parseInt(v));
            }
        }

        oreUBifier = new OreUBifier(settings.ubOres);
        dimensionManager = new DimensionManager(settings,oreUBifier);
        
        config.save();

        fortuneAffected = new ArrayList<String>();

        nuggets = new ArrayList<ItemStack>();

        nuggets.add(new ItemStack(NamedVanillaItem.goldNugget.registeredItem(), 1, 0));
        assert(nuggets.get(0) != null);

        tabModBlocks = new CreativeTabModBlocks("undergroundBiomesBlocks");
        tabModItems = new CreativeTabModBlocks("undergroundBiomesItems");

        proxy.registerRenderThings(oreUBifier);

        igneousStone = new BlockIgneousStone();
        UBIDs.igneousStoneName.gameRegister(igneousStone,ItemMetadataBlock.class);
        
        igneousCobblestone = new BlockIgneousCobblestone();
        UBIDs.igneousCobblestoneName.gameRegister(igneousCobblestone,ItemMetadataBlock.class);
        
        igneousStoneBrick = new BlockIgneousStoneBrick();
        UBIDs.igneousStoneBrickName.gameRegister(igneousStoneBrick,ItemMetadataBlock.class);
        
        metamorphicStone = new BlockMetamorphicStone();
        UBIDs.metamorphicStoneName.gameRegister(metamorphicStone,ItemMetadataBlock.class);
        
        metamorphicCobblestone = new BlockMetamorphicCobblestone();
        UBIDs.metamorphicCobblestoneName.gameRegister(metamorphicCobblestone,ItemMetadataBlock.class);
        
        metamorphicStoneBrick = new BlockMetamorphicStoneBrick();
        UBIDs.metamorphicStoneBrickName.gameRegister(metamorphicStoneBrick,ItemMetadataBlock.class);
        
        sedimentaryStone = new BlockSedimentaryStone();
        UBIDs.sedimentaryStoneName.gameRegister(sedimentaryStone,ItemMetadataBlock.class);

        igneousBrickSlab = stoneSlabPair(igneousStoneBrick,UBIDs.igneousBrickSlabName, "slabStoneBricks");

        metamorphicBrickSlab = stoneSlabPair(metamorphicStoneBrick,UBIDs.metamorphicBrickSlabName, "slabStoneBricks");
        
        igneousStoneSlab = stoneSlabPair(igneousStone,UBIDs.igneousStoneSlabName, "slabStone");

        metamorphicStoneSlab = stoneSlabPair(metamorphicStone,UBIDs.metamorphicStoneSlabName, "slabStone");

        igneousCobblestoneSlab = stoneSlabPair(igneousCobblestone,UBIDs.igneousCobblestoneSlabName, "slabCobblestone");

        metamorphicCobblestoneSlab = stoneSlabPair(metamorphicCobblestone,UBIDs.metamorphicCobblestoneSlabName, "slabCobblestone");
        
        sedimentaryStoneSlab = stoneSlabPair(sedimentaryStone,UBIDs.sedimentaryStoneSlabName, "slabStone");
        //items

        ligniteCoal = new ItemLigniteCoal(ligniteCoalID());
        fossilPiece = new ItemFossilPiece(fossilPieceID());

        fortuneAffected.add(ligniteCoal.getUnlocalizedName());

        tabModItems.item = ligniteCoal;

        proxy.setUpBlockNames();

        // register village change events

        constructs = new UndergroundBiomesConstructs();
        constructs.preInit(config);

        config.save();
        // set up ores;

        // test delayed requests
        
        //oreUBifier.setupUBOre(Blocks.iron_ore,UBOreTexturizer.iron_overlay, event);
        UBAPIHook.ubAPIHook.ubOreTexturizer.requestUBOreSetup(Blocks.iron_ore, UBOreTexturizer.iron_overlay);

        //test on-the-spot requests

        //oreUBifier.setupUBOre(Blocks.redstone_ore,UBOreTexturizer.redstone_overlay, event);
        UBAPIHook.ubAPIHook.ubOreTexturizer.setupUBOre(Blocks.redstone_ore,UBOreTexturizer.redstone_overlay,event);

        oreUBifier.setupUBOre(Blocks.coal_ore   , UBOreTexturizer.coal_overlay   , event);
        oreUBifier.setupUBOre(Blocks.diamond_ore, UBOreTexturizer.diamond_overlay, event);
        oreUBifier.setupUBOre(Blocks.lapis_ore  , UBOreTexturizer.lapis_overlay  , event);
        oreUBifier.setupUBOre(Blocks.emerald_ore, UBOreTexturizer.emerald_overlay, event);
        oreUBifier.setupUBOre(Blocks.gold_ore   , UBOreTexturizer.gold_overlay   , event);
        oreUBifier.setupUBHidden(Blocks.monster_egg, event);

        oreRequester.fulfillRequests(event);

        defaultIDSetter = this.defaultIDs();
        FMLCommonHandler.instance().bus().register(this);
        //FMLCommonHandler.instance().bus().register(new EventWatcher());

        pipeline = new PacketPipeline();
    }

    private void registerCrossModOres() {
		oreUBifier.setupUBOre("appliedenergistics2:tile.OreQuartz", 0, "appliedenergistics2:OreQuartz");
		oreUBifier.setupUBOre("appliedenergistics2:tile.OreQuartzCharged", 0, "appliedenergistics2:OreQuartzCharged");
		oreUBifier.setupUBOre("arsmagica2:vinteumOre", 0, UBOreTexturizer.vinteum_overlay);
		oreUBifier.setupUBOre("arsmagica2:vinteumOre", 1, UBOreTexturizer.chimerite_overlay);
		oreUBifier.setupUBOre("arsmagica2:vinteumOre", 2, UBOreTexturizer.bluetopaz_overlay);
		oreUBifier.setupUBOre("arsmagica2:vinteumOre", 3, UBOreTexturizer.moonstone_overlay);
		oreUBifier.setupUBOre("arsmagica2:vinteumOre", 4, UBOreTexturizer.sunstone_overlay);
		oreUBifier.setupUBOre("BiomesOPlenty:gemOre", 2, UBOreTexturizer.ruby_overlay);
		oreUBifier.setupUBOre("BiomesOPlenty:gemOre", 4, UBOreTexturizer.olivine_peridot_overlay);
		oreUBifier.setupUBOre("BiomesOPlenty:gemOre", 6, UBOreTexturizer.topaz_overlay);
		oreUBifier.setupUBOre("BiomesOPlenty:gemOre", 8, UBOreTexturizer.tanzanite_overlay);
		oreUBifier.setupUBOre("BiomesOPlenty:gemOre", 10, UBOreTexturizer.malachite_overlay);
		oreUBifier.setupUBOre("BiomesOPlenty:gemOre", 12, UBOreTexturizer.sapphire_overlay);
		oreUBifier.setupUBOre("BiomesOPlenty:gemOre", 14, UBOreTexturizer.amber_overlay);
		oreUBifier.setupUBOre("bonecraft:Fossil", 0, UBOreTexturizer.fossils_overlay);
        oreUBifier.setupUBOre("deepresonance:oreResonating", 0, UBOreTexturizer.resonating_overlay);
		oreUBifier.setupUBOre("evilcraft:darkOre", 0, "evilcraft:darkOre");
		oreUBifier.setupUBOre("harvestcraft:salt", 0, UBOreTexturizer.salt_overlay);
        oreUBifier.setupUBOre("IC2:blockOreUran", 0, UBOreTexturizer.uranium_overlay);
		oreUBifier.setupUBOre("imc:block_adamantium_ore", 0, UBOreTexturizer.adamantium_overlay);
		oreUBifier.setupUBOre("ImmersiveEngineering:ore", 0, UBOreTexturizer.copper_overlay);
		oreUBifier.setupUBOre("ImmersiveEngineering:ore", 1, UBOreTexturizer.aluminium_overlay);
		oreUBifier.setupUBOre("ImmersiveEngineering:ore", 2, UBOreTexturizer.lead_overlay);
		oreUBifier.setupUBOre("ImmersiveEngineering:ore", 3, UBOreTexturizer.silver_overlay);
		oreUBifier.setupUBOre("ImmersiveEngineering:ore", 4, UBOreTexturizer.nickel_overlay);
		oreUBifier.setupUBOre("magicalcrops:MinicioOre", 0, UBOreTexturizer.minicio_overlay);
        oreUBifier.setupUBOre("Mekanism:OreBlock", 0, UBOreTexturizer.osmium_overlay);
        // oreUBifier.setupUBOre("Mekanism:OreBlock", 1, UBOreTexturizer.copper_overlay);
        // oreUBifier.setupUBOre("Mekanism:OreBlock", 2, UBOreTexturizer.tin_overlay);
		oreUBifier.setupUBOre("Mimicry:Sparr_Mimichite Ore", 0, UBOreTexturizer.mimichite_overlay);
		oreUBifier.setupUBOre("OptimumGS_Mod:bunginiteOre", 0, UBOreTexturizer.bunginite_overlay);
		oreUBifier.setupUBOre("OptimumGS_Mod:carbonOre", 0, UBOreTexturizer.carbon_overlay);
		oreUBifier.setupUBOre("OptimumGS_Mod:eridiumOre", 0, UBOreTexturizer.eridium_overlay);
		oreUBifier.setupUBOre("OptimumGS_Mod:silverOre", 0, UBOreTexturizer.silver_overlay);
		oreUBifier.setupUBOre("nevermine:oreAmethyst", 0, UBOreTexturizer.amethyst_overlay);
		oreUBifier.setupUBOre("nevermine:oreJade", 0, UBOreTexturizer.jade_overlay);
		oreUBifier.setupUBOre("nevermine:oreLimonite", 0, UBOreTexturizer.limonite_overlay);
		oreUBifier.setupUBOre("nevermine:oreRosite", 0, UBOreTexturizer.rosite_overlay);
		oreUBifier.setupUBOre("nevermine:oreRunium", 0, UBOreTexturizer.runium_overlay);
		oreUBifier.setupUBOre("nevermine:oreSapphire", 0, UBOreTexturizer.sapphire_overlay);
		oreUBifier.setupUBOre("randomadditions:BlockRAOre", 0, UBOreTexturizer.ruby_overlay);
		oreUBifier.setupUBOre("randomadditions:BlockRAOre", 1, UBOreTexturizer.copper_overlay);
		oreUBifier.setupUBOre("randomadditions:BlockRAOre", 2, UBOreTexturizer.tin_overlay);
		oreUBifier.setupUBOre("randomadditions:BlockRAOre", 3, UBOreTexturizer.tourmaline_overlay);
		oreUBifier.setupUBOre("StargateTech2:block.naquadah", 0, UBOreTexturizer.naquadah_overlay);
		oreUBifier.setupUBOre("Techguns:oreTitanIron", 0, UBOreTexturizer.titanium_overlay);
		oreUBifier.setupUBOre("Techguns:oreTitanIron", 1, UBOreTexturizer.uranium_overlay);
		oreUBifier.setupUBOre("Thaumcraft:blockCustomOre", 0, UBOreTexturizer.cinnabar_overlay);	// cinnabar ore
		oreUBifier.setupUBOre("Thaumcraft:blockCustomOre", 7, UBOreTexturizer.amber2_overlay);	// amber bearing stone
		oreUBifier.setupUBOre("TYNKYN:carnelianOre", 0, UBOreTexturizer.carnelian_overlay);
		oreUBifier.setupUBOre("TYNKYN:surfaceOre", 0, UBOreTexturizer.no_overlay);
		oreUBifier.setupUBOre("TYNKYN:talcOre", 0, UBOreTexturizer.talc_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 0, UBOreTexturizer.tin_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 1, UBOreTexturizer.copper_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 2, UBOreTexturizer.silver_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 3, UBOreTexturizer.lead_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 4, UBOreTexturizer.zinc_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 5, UBOreTexturizer.nickel_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 6, UBOreTexturizer.aluminium_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 7, UBOreTexturizer.magnesite_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 8, UBOreTexturizer.uranium_overlay);
		oreUBifier.setupUBOre("voltzengine:veStoneOre", 9, UBOreTexturizer.platinum_overlay);
		oreUBifier.setupUBOre("voltzengine:veGemOre", 0, UBOreTexturizer.amazonite_overlay);
		oreUBifier.setupUBOre("voltzengine:veGemOre", 1, UBOreTexturizer.smokeyquartz_overlay);
		oreUBifier.setupUBOre("voltzengine:veGemOre", 2, UBOreTexturizer.gem_onyxblack_overlay);
		oreUBifier.setupUBOre("voltzengine:veGemOre", 3, UBOreTexturizer.gem_onyxred_overlay);
		oreUBifier.setupUBOre("voltzengine:veGemOre", 4, UBOreTexturizer.olivine_peridot_overlay);	// green_garnet
		oreUBifier.setupUBOre("voltzengine:veGemOre", 5, UBOreTexturizer.gem_orange_overlay);
		oreUBifier.setupUBOre("voltzengine:veGemOre", 6, UBOreTexturizer.ruby_overlay);
		oreUBifier.setupUBOre("voltzengine:veGemOre", 7, UBOreTexturizer.gem_yellow_overlay);
		oreUBifier.setupUBOre("wildcaves3:FossilBlock", 0, UBOreTexturizer.fossils_overlay);
	}

    public StoneSlabPair stoneSlabPair(BlockMetadataBase material, NamedSlabPair slabPairName, String oreName) {
        BlockStoneSlab half = new BlockStoneSlab(false, material, slabPairName);
        BlockStoneSlab full = new BlockStoneSlab(true, material, slabPairName);

        GameRegistry.registerBlock(half, ItemMetadataSlab.class, slabPairName.half.internal(), UBIDs.ubPrefix(), full);
        GameRegistry.registerBlock(full, ItemMetadataSlab.class, slabPairName.full.internal(), UBIDs.ubPrefix(), half);

        if (instance().settings.slabOreDictionaryRegistering.value()) {
	        for (int i = 0; i < 8; i++){
	            OreDictionary.registerOre(oreName, new ItemStack(half, 1, i));
	        }
        }

        return new StoneSlabPair(half, full);
    }

    @EventHandler
    public void load(FMLInitializationEvent event){
        
        registerCrossModOres();
        
        pipeline.initialise();
        playerDetector = new PlayerDetector();
        networking = new UndergroundBiomesNetworking(pipeline,settings);
        // when a player logs in, send the settings
        playerDetector.addLoginAction(new SettingsSender());

        addOreDicts();
        addRecipes();
        constructs.load(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) throws Exception {
        pipeline.postInitialise();

        if (addOreDictRecipes()){
            oreDictifyStone();
        }

        // Pull nuggets from ore dictionary
        ArrayList<ItemStack> stacks;
        for (String s : nuggetStrings){
            stacks = OreDictionary.getOres(s);
            if (stacks.size() > 0) {
                nuggets.add(stacks.get(0));
            }
        }

        if (Loader.isModLoaded("Thaumcraft")){
            try{
		//ThaumcraftApi.registerObjectTag(id, meta, (new ObjectTags()).add(EnumTag.VALUABLE, 58).add(EnumTag.LIGHT, 15));
            }
            catch (Exception exception){
                logger.error("[UndergroundBiomes] Error while integrating with Thaumcraft");
                exception.printStackTrace(System.err);
            }
        }

        constructs.postInit(event);
        //logger.info(Block.blockRegistry.getNameForObject(igneousStone));
        //logger.info(Block.blockRegistry.getNameForObject(metamorphicStone));
        //logger.info(Block.blockRegistry.getNameForObject(sedimentaryStone));

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
        MinecraftForge.ORE_GEN_BUS.register(this);
        //if (replaceCobblestone()) {
            MinecraftForge.EVENT_BUS.register(dimensionManager);
            MinecraftForge.TERRAIN_GEN_BUS.register(dimensionManager);
        //}
    }

    @EventHandler
    public void serverLoad(FMLServerAboutToStartEvent event) {
        //logger.info("server about to start");
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        //logger.info("server starting");
        event.registerServerCommand(new CommandOreDictifyStone());
    }

   @EventHandler
    public void serverLoaded(FMLServerStartedEvent event) {
        //logger.info("server starting");
        if (forceRemap) {
            this.forceConfigIDs();
            GameData.freezeData();
            logger.info("forcing on remapping");
            this.runningConfigIDs = true;
        }
    }

    @EventHandler
    public void serverUnload(FMLServerStoppingEvent event) {
        //logger.info("server stopping");
        for (Object key: Block.blockRegistry.getKeys()) {
            String name = (String) key;
            Block named = Block.getBlockFromName(name);
            Block.getIdFromBlock(named);
        }
        for (Object key: Item.itemRegistry.getKeys()) {
            String name = (String) key;
            Item named = (Item)(Item.itemRegistry.getObject(name));
            Item.getIdFromItem(named);
        }
        if (runningConfigIDs) {
            //defaultIDSetter.redoAsNeeded();
            runningConfigIDs = false;
        }
        BiomeUndergroundDecorator.noMoreRedos();
    }


    private boolean forceRemap;
    @EventHandler
    public void onMissingMapping(FMLMissingMappingsEvent event) {
       // logger.info("missing mappings");
       forceRemap = false;
        for (FMLMissingMappingsEvent.MissingMapping missing: event.get()) {
            logger.info(missing.name + " " + missing.type.toString());
            if (missing.name.equalsIgnoreCase("UndergroundBiomes:sedimentaryStoneItem")) forceRemap = true;
        }
    }

    @EventHandler
    public void adjustMappings(FMLModIdMappingEvent event) {

        boolean oldIDs = false;
        logger.info("remapping");
        ImmutableList<FMLModIdMappingEvent.ModRemapping> remappings =  event.remappedIds;

        Iterator<FMLModIdMappingEvent.ModRemapping> list = remappings.iterator();
        while (list.hasNext()) {
            FMLModIdMappingEvent.ModRemapping remapping = list.next();
            // logger.info(remapping.tag +  " from " + remapping.oldId + " to " + remapping.newId);

            // currently tags drop the fist letter
            if(remapping.tag.equals("inecraft:bed")){
                if (remapping.oldId<256) oldIDs = true;
            }
            // and presumably Forge will fix that
            if(remapping.tag.equals("Minecraft:bed")){
                if (remapping.oldId<256) oldIDs = true;
            }
            // currently tags drop the fist letter
            if(remapping.tag.equals("inecraft:wheat")){
                if (remapping.oldId<256) oldIDs = true;
            }
            // and presumably Forge will fix that
            if(remapping.tag.equals("Minecraft:wheat")){
                if (remapping.oldId<256) oldIDs = true;
            }
            if (remapping.tag.equals("ndergroundBiomes:stairs")) {
                if (remapping.newId == constructs.stoneStairID()) oldIDs = true;
            }
            if (remapping.tag.equals("UndergroundBiomes:stairs")) {
                if (remapping.newId == constructs.stoneStairID()) oldIDs = true;
            }
            if (remapping.tag.equals("UndergroundBiomes:igneousStone")) {
                if (remapping.newId > 2000) oldIDs = true;
            }
            if (remapping.tag.equals("ndergroundBiomes:igneousStone")) {
                if (remapping.newId> 2000) oldIDs = true;
            }
            if (remapping.tag.equals("UndergroundBiomes:metamorphicStone")) {
                if (remapping.newId> 2000) oldIDs = true;
            }
            if (remapping.tag.equals("ndergroundBiomes:metamorphicStone")) {
                if (remapping.newId> 2000) oldIDs = true;
            }
        }
        //logger.info("old IDs "+oldIDs);
        if (oldIDs) {
            this.forceConfigIDs();

            logger.info("forcing");
            this.runningConfigIDs = true;
        }
        if (forceRemap) {
            this.forceConfigIDs();

            logger.info("forcing");
            this.runningConfigIDs = true;
            forceRemap = false;
        }
    }

    public void addRecipes() {
        if (!addOreDictRecipes())
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.furnace.block(), 1), "XXX", "X X", "XXX", 'X', oreCobblestoneName()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.lever.block(), 1), "I", "X", 'X', oreCobblestoneName(), 'I', NamedVanillaItem.stick.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.piston.block(), 1), "WWW", "CIC", "CRC", 'W',NamedVanillaBlock.planks.block(), 'C', oreCobblestoneName(), 'I', NamedVanillaItem.ingotIron.cachedItem(), 'R', NamedVanillaItem.redstone.cachedItem()));
            if (!stairsOn()) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.stairsCobblestone.block(), 4), "X  ", "XX ", "XXX", 'X', oreCobblestoneName()));
            }
            if (!wallsOn()) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.cobblestone_wall.block(), 1), "XXX", "XXX", 'X', oreCobblestoneName()));
            }
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaItem.axeStone.cachedItem(), 1), "XX ", "XW ", " W ", 'X', oreCobblestoneName(), 'W', NamedVanillaItem.stick.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaItem.pickaxeStone.cachedItem(), 1), "XXX", " W ", " W ", 'X', oreCobblestoneName(), 'W', NamedVanillaItem.stick.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaItem.hoeStone.cachedItem(), 1), "XX ", " W ", " W ", 'X', oreCobblestoneName(), 'W', NamedVanillaItem.stick.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaItem.shovelStone.cachedItem(), 1), " X ", " W ", " W ", 'X', oreCobblestoneName(), 'W', NamedVanillaItem.stick.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaItem.swordStone.cachedItem(), 1), "X", "X", "W", 'X', oreCobblestoneName(), 'W', NamedVanillaItem.stick.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaItem.brewingStand.cachedItem(), 1), " B ", "XXX", 'X', oreCobblestoneName(), 'B', NamedVanillaItem.blazeRod.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.dispenser.block(), 1), "XXX", "XBX", "XRX", 'X', oreCobblestoneName(), 'B', NamedVanillaItem.bow.cachedItem(), 'R', NamedVanillaItem.redstone.cachedItem()));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.stone_pressure_plate.block(), 1), "XX", 'X', oreStoneName()));
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneSingleSlab, 6, 3), "XXX", 'X', oreCobblestoneName()));
            //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneSingleSlab, 6, 0), "XXX", 'X', "stoneSmooth"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaItem.redstoneRepeater.cachedItem(), 1), "TRT", "XXX", 'X', oreStoneName(), 'T', NamedVanillaBlock.torchRedstoneActive.block(), 'R', NamedVanillaItem.redstone.cachedItem()));
            if (!stairsOn()) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NamedVanillaBlock.stairsStoneBrick.block(), 4), "X  ", "XX ", "XXX", 'X', "stoneBricks"));
            }
            if (!buttonsOn()) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NamedVanillaBlock.stoneButton.block(), 1), oreStoneName()));

            }
        }
        
        GameRegistry.addRecipe(new ItemStack(NamedVanillaItem.coal.cachedItem(), 1), "XXX", "XXX", "XXX", 'X', ligniteCoal);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 15), new ItemStack(fossilPiece, 1, WILDCARD_VALUE));


        //vanilla cobblestone
        // set recipe
        //logger.info("setting stone recipe");
        this.vanillaStoneRecipeManager.accept(vanillaStoneCrafting());
        // arrange changes
        settings.vanillaStoneCrafting.informOnChange(vanillaStoneRecipeManager);
        
        for (int i = 0; i < 8; i++) {
            GameRegistry.addRecipe(new ItemStack(igneousBrickSlab.half, 6, i), "XXX", 'X', new ItemStack(igneousStoneBrick, 1, i));
            GameRegistry.addRecipe(new ItemStack(metamorphicBrickSlab.half, 6, i), "XXX", 'X', new ItemStack(metamorphicStoneBrick, 1, i));

            GameRegistry.addRecipe(new ItemStack(igneousStoneSlab.half, 6, i), "XXX", 'X', new ItemStack(igneousStone, 1, i));
            GameRegistry.addRecipe(new ItemStack(metamorphicStoneSlab.half, 6, i), "XXX", 'X', new ItemStack(metamorphicStone, 1, i));

            GameRegistry.addRecipe(new ItemStack(igneousCobblestoneSlab.half, 6, i), "XXX", 'X', new ItemStack(igneousCobblestone, 1, i));
            GameRegistry.addRecipe(new ItemStack(metamorphicCobblestoneSlab.half, 6, i), "XXX", 'X', new ItemStack(metamorphicCobblestone, 1, i));

            GameRegistry.addRecipe(new ItemStack(sedimentaryStoneSlab.half, 6, i), "XXX", 'X', new ItemStack(sedimentaryStone, 1, i));

            FurnaceRecipes.smelting().func_151394_a(new ItemStack(metamorphicCobblestone, 1, i), new ItemStack(metamorphicStone, 1, i), 0.1f);
            FurnaceRecipes.smelting().func_151394_a(new ItemStack(igneousCobblestone, 1, i), new ItemStack(igneousStone, 1, i), 0.1f);

            GameRegistry.addRecipe(new ItemStack(metamorphicStoneBrick, 4, i), "xx", "xx", 'x', new ItemStack(metamorphicStone, 1, i));
            GameRegistry.addRecipe(new ItemStack(igneousStoneBrick, 4, i), "xx", "xx", 'x', new ItemStack(igneousStone, 1, i));
        }
        
        GameRegistry.registerFuelHandler(new FuelManager());
    }
    
    public void addOreDicts() {
        OreDictionary.registerOre(oreStoneName(), new ItemStack(igneousStone, 1, WILDCARD_VALUE));
        OreDictionary.registerOre(oreStoneName(), new ItemStack(metamorphicStone, 1, WILDCARD_VALUE));
        OreDictionary.registerOre(oreStoneName(), new ItemStack(sedimentaryStone, 1, WILDCARD_VALUE));
        OreDictionary.registerOre(oreCobblestoneName(), new ItemStack(igneousCobblestone, 1, WILDCARD_VALUE));
        OreDictionary.registerOre(oreCobblestoneName(), new ItemStack(metamorphicCobblestone, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("stoneBricks", new ItemStack(igneousStoneBrick, 1, WILDCARD_VALUE));
        OreDictionary.registerOre("stoneBricks", new ItemStack(metamorphicStoneBrick, 1, WILDCARD_VALUE));
        //OreDictionary.registerOre(textures, ore);
        this.oreUBifier.registerOres();
    }
    
    public static int oreDictifyStone() throws Exception {
        int numReplaced = 0;
        Map<ItemStack, String> replacements = new HashMap<ItemStack, String>();
        replacements.put(new ItemStack(NamedVanillaBlock.stone.block(), 1, WILDCARD_VALUE), oreStoneName());
        replacements.put(new ItemStack(NamedVanillaBlock.cobblestone.block(), 1, WILDCARD_VALUE), oreCobblestoneName());
        replacements.put(new ItemStack(NamedVanillaBlock.stoneBrick.block(), 1, WILDCARD_VALUE), "stoneBricks");
        ItemStack[] replaceStacks = replacements.keySet().toArray(new ItemStack[replacements.keySet().size()]);

        // Ignore recipes for the following items
        ItemStack[] exclusions = new ItemStack[]
        {
            new ItemStack(NamedVanillaBlock.stairsStoneBrick.block()),
            new ItemStack(NamedVanillaBlock.stoneBrick.block()),
            new ItemStack(NamedVanillaBlock.stoneSingleSlab.block(), 1, 5),
        };
        List recipes = CraftingManager.getInstance().getRecipeList();
        Constructor shapedConstr = ShapedOreRecipe.class.getDeclaredConstructor(ShapedRecipes.class, Map.class);
        Constructor shapelessConstr = ShapelessOreRecipe.class.getDeclaredConstructor(ShapelessRecipes.class, Map.class);
        shapedConstr.setAccessible(true);
        shapelessConstr.setAccessible(true);

        // zap stone button recipe if needed
        // this is being altered somewhere else so we need to take it out
        if (buttonsOn()) {
            Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();

            while (iterator.hasNext()){
                IRecipe recipe = iterator.next();
                if (recipe == null)
                         continue;
                ItemStack output = recipe.getRecipeOutput();
                if (output != null && NamedVanillaBlock.stoneButton.matches(output.getItem()))
                         iterator.remove();
            }
        }

        // Add ore dictionary entries for replaced blocks
        for (ItemStack stack : replacements.keySet())
        {
            OreDictionary.registerOre(replacements.get(stack), stack);
        }

        // Search stone recipes for recipes to replace
        for (int i = 0; i < recipes.size(); i++)
        {
            Object obj = recipes.get(i);
            ItemStack output = ((IRecipe)obj).getRecipeOutput();
            if (output == null || containsMatch(false, exclusions, output))
            {
                continue;
            }
            // suppress alterations overriding construct recipes
            if (UndergroundBiomesConstructs.overridesRecipe((IRecipe)obj)) continue;

            if (obj instanceof ShapedRecipes)
            {
                ShapedRecipes recipe = (ShapedRecipes)obj;
                if (containsMatch(true, recipe.recipeItems, replaceStacks))
                {
                    recipes.set(i, shapedConstr.newInstance(recipe, replacements));
                    numReplaced++;
                    UndergroundBiomes.logger.info("Changed shaped recipe for " + output.getDisplayName());
                }
            }
            else if (obj instanceof ShapelessRecipes)
            {
                ShapelessRecipes recipe = (ShapelessRecipes)obj;
                if (containsMatch(true, (ItemStack[])recipe.recipeItems.toArray(new ItemStack[recipe.recipeItems.size()]), replaceStacks))
                {
                    recipes.set(i, shapelessConstr.newInstance(recipe, replacements));
                    numReplaced++;
                    UndergroundBiomes.logger.info("Changed shapeless recipe for " + output.getDisplayName());
                }
            }
            else if (obj instanceof ShapedOreRecipe)
            {
                ShapedOreRecipe recipe = (ShapedOreRecipe)obj;
                if (containsMatchReplaceInplace(true, recipe.getInput(), replaceStacks, replacements))
                {
                    numReplaced++;
                    UndergroundBiomes.logger.info("Changed shaped ore recipe for " + output.getDisplayName());
                }
            }
            else if (obj instanceof ShapelessOreRecipe)
            {
                ShapelessOreRecipe recipe = (ShapelessOreRecipe)obj;
                if (containsMatchReplaceInplace(true, recipe.getInput(), replaceStacks, replacements))
                {
                    numReplaced++;
                    UndergroundBiomes.logger.info("Changed shapeless ore recipe for " + output.getDisplayName());
                }
            }
        }
        //CraftingManager.getInstance().addShapelessRecipe(new ItemStack(NamedVanillaBlock.stoneButton.block()), new Object[] {NamedVanillaBlock.stone.block()});

        return numReplaced;
    }
    
    private static boolean containsMatch(boolean strict, ItemStack[] inputs, ItemStack... targets){
        try {
        for (ItemStack input : inputs) {
            for (ItemStack target : targets) {
                if (OreDictionary.itemMatches(target, input, strict)){
                    return true;
                }
            }
        }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    // Doing what Forge tells not to do
    private static boolean containsMatchReplaceInplace(boolean strict, Object inputArrayOrList, ItemStack[] targets, Map<ItemStack, String> replacements)
    {
        boolean replaced = false;
        if (inputArrayOrList instanceof ArrayList)
        {
            ArrayList inputList = (ArrayList)inputArrayOrList;
            for (int i = 0; i < inputList.size(); i++)
            {
                Object input = inputList.get(i);
                if (input instanceof ItemStack)
                {
                    for (ItemStack target : targets)
                    {
                        if (OreDictionary.itemMatches(target, (ItemStack)input, strict))
                        {
                            inputList.set(i, OreDictionary.getOres(replacements.get(target)));
                            replaced = true;
                        }
                    }
                }
            }
        } else {
            // Expect array
            Object[] inputArray = (Object[])inputArrayOrList;
            for (int i = 0; i < inputArray.length; i++)
            {
                Object input = inputArray[i];
                if (input instanceof ItemStack)
                {
                    for (ItemStack target : targets)
                    {
                        if (OreDictionary.itemMatches(target, (ItemStack)input, strict))
                        {
                            inputArray[i] = OreDictionary.getOres(replacements.get(target));
                            replaced = true;
                        }
                    }
                }
            }
        }
        return replaced;
    }
    
    public static long getWorldSeed(){
        return worldSeed;
    }
    
    public static World getWorld() {
        return world;
    }

    public boolean inChunkGenerationAllowed(int id) {
        return dimensionManager.inChunkGenerationAllowed(id);
    }

    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (world instanceof WorldServer) {
            WorldServer server = (WorldServer)world;
            File worldLocation = server.getChunkSaveLocation().getParentFile();
            //UndergroundBiomes.logger.info(world.toString() + " " +worldLocation.getAbsolutePath());
            configManager.setWorldFile(worldLocation);
            this.dimensionManager.setupGenerators(event);
        }
        if (!gotWorldSeed) {
            world = event.world;
            worldSeed = world.getSeed();
            gotWorldSeed = true;
        }
        tabModBlocks.item = ItemMetadataBlock.itemFrom(UBIDs.igneousStoneBrickName);
    }
    
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event){
        gotWorldSeed = false;
        this.ubCodeLocations.clear();
        this.dimensionManager.unload();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onBiomeDecorate(DecorateBiomeEvent.Post event)    {
        dimensionManager.onBiomeDecorate(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onOreGenerate(OreGenEvent.Post event)    {
        if (this.oreUBifier.replacementActive()) {
            dimensionManager.redoOres(event.worldX, event.worldZ, event.world);
        }
    }

    @SubscribeEvent
    public void registerOre(OreDictionary.OreRegisterEvent event){
        if (Arrays.asList(nuggets).contains(event.Name)){
            nuggets.add(event.Ore);
        }
    }

    public void redoOres(int x, int z, World worldObj) {
        dimensionManager.redoOres(x, z, worldObj);
    }

    public static String oreStoneName() {
        if (forgeVersion() < 934) return "stoneSmooth";
        return "stone";
    }

    public static String oreCobblestoneName() {
        if (forgeVersion() < 934) return "stoneCobble";
        return "cobblestone";
    }
    public static int forgeVersion() {
        return  ForgeVersion.getBuildVersion();
    }

    private WatchList configList() {
        WatchList forcing = new WatchList();
        forcing.addChangeWithItem(igneousStoneID(), igneousStone);
        forcing.addChangeWithItem(metamorphicStoneID(), metamorphicStone);
        forcing.addChangeWithItem(sedimentaryStoneID(), sedimentaryStone);
        forcing.addChangeWithItem(igneousCobblestoneID(), igneousCobblestone);
        forcing.addChangeWithItem(metamorphicCobblestoneID(), metamorphicCobblestone);
        forcing.addChangeWithItem(igneousStoneBrickID(), igneousStoneBrick);
        forcing.addChangeWithItem(metamorphicStoneBrickID(), metamorphicStoneBrick);

        forcing.addChangeWithItem(igneousBrickSlabHalfID(),igneousBrickSlab.half);
        forcing.addChangeWithItem(igneousBrickSlabFullID(),igneousBrickSlab.full);

        forcing.addChangeWithItem(metamorphicBrickSlabHalfID(),metamorphicBrickSlab.half);
        forcing.addChangeWithItem(metamorphicBrickSlabFullID(),metamorphicBrickSlab.full);

        forcing.addChangeWithItem(igneousStoneSlabHalfID(),igneousStoneSlab.half);
        forcing.addChangeWithItem(igneousStoneSlabFullID(),igneousStoneSlab.full);

        forcing.addChangeWithItem(metamorphicStoneSlabHalfID(),metamorphicStoneSlab.half);
        forcing.addChangeWithItem(metamorphicStoneSlabFullID(),metamorphicStoneSlab.full);

        forcing.addChangeWithItem(igneousCobblestoneSlabHalfID(),igneousCobblestoneSlab.half);
        forcing.addChangeWithItem(igneousCobblestoneSlabFullID(),igneousCobblestoneSlab.full);

        forcing.addChangeWithItem(metamorphicCobblestoneSlabHalfID(),metamorphicCobblestoneSlab.half);
        forcing.addChangeWithItem(metamorphicCobblestoneSlabFullID(),metamorphicCobblestoneSlab.full);

        forcing.addChangeWithItem(sedimentaryStoneSlabHalfID(),sedimentaryStoneSlab.half);
        forcing.addChangeWithItem(sedimentaryStoneSlabFullID(),sedimentaryStoneSlab.full);

        forcing.addChange(constructs.stoneButtonID(),constructs.stoneButton().construct);
        forcing.addChange(constructs.stoneStairID(), constructs.stoneStair().construct);
        forcing.addChange(constructs.stoneWallID(), constructs.stoneWall().construct);
        return forcing;
    }

    private WatchList defaultIDs() {
        WatchList forcing = new WatchList();
        forcing.addWithItem(igneousStone);
        forcing.addWithItem( metamorphicStone);
        forcing.addWithItem( sedimentaryStone);
        forcing.addWithItem( igneousCobblestone);
        forcing.addWithItem( metamorphicCobblestone);
        forcing.addWithItem( igneousStoneBrick);
        forcing.addWithItem( metamorphicStoneBrick);

        forcing.addWithItem(igneousBrickSlab.half);
        forcing.addWithItem(igneousBrickSlab.full);

        forcing.addWithItem(metamorphicBrickSlab.half);
        forcing.addWithItem(metamorphicBrickSlab.full);

        forcing.addWithItem(igneousStoneSlab.half);
        forcing.addWithItem(igneousStoneSlab.full);

        forcing.addWithItem(metamorphicStoneSlab.half);
        forcing.addWithItem(metamorphicStoneSlab.full);

        forcing.addWithItem(igneousCobblestoneSlab.half);
        forcing.addWithItem(igneousCobblestoneSlab.full);

        forcing.addWithItem(metamorphicCobblestoneSlab.half);
        forcing.addWithItem(metamorphicCobblestoneSlab.full);

        forcing.addWithItem(sedimentaryStoneSlab.half);
        forcing.addWithItem(sedimentaryStoneSlab.full);

        forcing.add(constructs.stoneButton().construct);
        forcing.add(constructs.stoneStair().construct);
        forcing.add(constructs.stoneWall().construct);
        return forcing;
    }

    private void forceConfigIDs() {
        WatchList forcing = configList();
        try {
            for (String warning: forcing.problems()) {
                logger.info(warning);
            }
            logger.info("forcing config IDs ");
            forcing.redoAsNeeded();
            for (String warning: forcing.problems()) {
                logger.info(warning);
            }
        } catch (Exception e) {
            logger.info("redoerror "+ e.toString());
        }

        WatchList check = new WatchList();
        check.add(constructs.stoneButton().construct);
        check.add(constructs.stoneStair().construct);
        check.add(constructs.stoneWall().construct);
        for (Item item: ItemUBStairs.instances) {
            check.add(item);
        }
        for (String warning: check.problems()){
                //logger.info(warning);
        }
    }

    public class EventWatcher {
        public void processEvent(FMLEvent event) {
            //logger.info(event.getEventType());
        }
    }

    private class SettingsSender extends Acceptor<EntityPlayerMP> {

        @Override
        public void accept(EntityPlayerMP accepted) {
            networking.settings.sendTo(settings, accepted);
        }
        
    }
}
