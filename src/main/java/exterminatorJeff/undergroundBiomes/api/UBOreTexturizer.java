/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exterminatorJeff.undergroundBiomes.api;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * This is an interface for the class that can create Underground Biomes versions of arbitary ores
 * It creates three new blocks for each texturized ore.
 * @author Zeno410
 */
public interface UBOreTexturizer {
    // usage: Block is the ore block.
    // Overlay name is the fully qualified name, e.g. modname:overlayName
    // that static vars are fully qualified names for all the textures in the UB pack, just pass as is
    // the event isn't needed per se, but if this is called anytime else, the blocks will not "stick"
    public void setupUBOre(Block oreBlock, String overlayName, FMLPreInitializationEvent event);
    @Deprecated
    public void setupUBOre(Block oreBlock, int metadata, String overlayName, FMLPreInitializationEvent event);
    public void setupUBOre(Block oreBlock, int metadata, String overlayName, String blockName, FMLPreInitializationEvent event);

    public void requestUBOreSetup(Block oreBlock, String overlayName) throws BlocksAreAlreadySet;
    @Deprecated
    public void requestUBOreSetup(Block oreBlock, int metadata, String overlayName) throws BlocksAreAlreadySet;
    public void requestUBOreSetup(Block oreBlock, int metadata, String overlayName, String blockName) throws BlocksAreAlreadySet;
    public void redoOres(int xInBlockCoordinates, int zInBlockCoordinates, World serverSideWorld) ;

    public static String no_overlay = "undergroundbiomes:no_overlay";
    
    public static String adamantium_overlay      = "undergroundbiomes:adamantium_overlay";
    public static String aluminium_overlay       = "undergroundbiomes:aluminium_overlay";
    public static String amazonite_overlay       = "undergroundbiomes:amazonite_overlay";
    public static String amber_overlay           = "undergroundbiomes:amber_overlay";
    public static String amber2_overlay          = "undergroundbiomes:amber2_overlay";
    public static String amber_smooth_overlay    = "undergroundbiomes:amber_overlay-smooth";
    public static String amethyst_overlay        = "undergroundbiomes:amethyst_overlay";
    public static String bluetopaz_overlay       = "undergroundbiomes:bluetopaz_overlay";
    public static String bunginite_overlay       = "undergroundbiomes:bunginite_overlay";
    public static String carbon_overlay          = "undergroundbiomes:carbon_overlay";
    public static String carnelian_overlay       = "undergroundbiomes:carnelian_overlay";
    public static String cinnabar_overlay        = "undergroundbiomes:cinnabar_overlay";
    public static String chimerite_overlay       = "undergroundbiomes:chimerite_overlay";
    public static String coal_overlay            = "undergroundbiomes:coal_overlay";
    public static String copper_overlay          = "undergroundbiomes:copper_overlay";
    public static String diamond_overlay         = "undergroundbiomes:diamond_overlay";
    public static String emerald_overlay         = "undergroundbiomes:emerald_overlay";
    public static String eridium_overlay         = "undergroundbiomes:eridium_overlay";
    public static String fossils_overlay         = "undergroundbiomes:fossils_overlay";
    public static String gem_green_overlay       = "undergroundbiomes:gem_green_overlay";
    public static String gem_onyxblack_overlay   = "undergroundbiomes:gem_onyxblack_overlay";
    public static String gem_onyxred_overlay     = "undergroundbiomes:gem_onyxred_overlay";
    public static String gem_orange_overlay      = "undergroundbiomes:gem_orange_overlay";
    public static String gem_red_overlay         = "undergroundbiomes:gem_red_overlay";
    public static String gem_ruby_overlay        = "undergroundbiomes:gem_ruby_overlay";
    public static String gem_sapphire_overlay    = "undergroundbiomes:gem_sapphire_overlay";
    public static String gem_yellow_overlay      = "undergroundbiomes:gem_yellow_overlay";
    public static String gold_overlay            = "undergroundbiomes:gold_overlay";
    public static String iron_overlay            = "undergroundbiomes:iron_overlay";
    public static String jade_overlay            = "undergroundbiomes:jade_overlay";
    public static String lapis_overlay           = "undergroundbiomes:lapis_overlay";
    public static String lead_overlay            = "undergroundbiomes:lead_overlay";
    public static String limonite_overlay        = "undergroundbiomes:limonite_overlay";
    public static String magnesite_overlay       = "undergroundbiomes:magnesite_overlay";
    public static String malachite_overlay       = "undergroundbiomes:malachite_overlay";
    public static String mimichite_overlay       = "undergroundbiomes:mimichite_overlay";
    public static String minicio_overlay         = "undergroundbiomes:minicio_overlay";
    public static String moonstone_overlay       = "undergroundbiomes:moonstone_overlay";
    public static String naquadah_overlay        = "undergroundbiomes:naquadah_overlay";
    public static String nickel_overlay          = "undergroundbiomes:nickel_overlay";
    public static String olivine_peridot_overlay = "undergroundbiomes:olivine-peridot_overlay";
    public static String osmium_overlay          = "undergroundbiomes:osmium_overlay";
    public static String platinum_overlay        = "undergroundbiomes:platinum_overlay";
    public static String redstone_overlay        = "undergroundbiomes:redstone_overlay";
    public static String resonating_overlay      = "undergroundbiomes:resonating_overlay";
    public static String rosite_overlay          = "undergroundbiomes:rosite_overlay";
    public static String ruby_overlay            = "undergroundbiomes:ruby_overlay";
    public static String runium_overlay          = "undergroundbiomes:runium_overlay";
    public static String salt_overlay            = "undergroundbiomes:salt_overlay";
    public static String sapphire_overlay        = "undergroundbiomes:sapphire_overlay";
    public static String silver_overlay          = "undergroundbiomes:silver_overlay";
    public static String smokeyquartz_overlay    = "undergroundbiomes:smokeyquartz_overlay";
    public static String sulfur_overlay          = "undergroundbiomes:sulfur_overlay";
    public static String sunstone_overlay        = "undergroundbiomes:sunstone_overlay";
    public static String talc_overlay            = "undergroundbiomes:talc_overlay";
    public static String tanzanite_overlay       = "undergroundbiomes:tanzanite_overlay";
    public static String tin_overlay             = "undergroundbiomes:tin_overlay";
    public static String titanium_overlay        = "undergroundbiomes:titanium_overlay";
    public static String topaz_overlay           = "undergroundbiomes:topaz_overlay";
    public static String tourmaline_overlay      = "undergroundbiomes:tourmaline_overlay";
    public static String uranium_overlay         = "undergroundbiomes:uranium_overlay";
    public static String vinteum_overlay         = "undergroundbiomes:vinteum_overlay";
    public static String zinc_overlay            = "undergroundbiomes:zinc_overlay";
    

    public class BlocksAreAlreadySet extends RuntimeException {
        // this is thrown if UB has already run its pre-initialization step and can no longer register blocks
        public final Block oreBlock;
        public final String overlayName;
        
        public BlocksAreAlreadySet(Block oreBlock, String overlayName) {
            this.oreBlock = oreBlock;
            this.overlayName = overlayName;
        }

        @Override
        public String toString() {
            String blockDescription = "undefined block";
            String overlayDescription = "undefined overlay";
            if (oreBlock != null) blockDescription = oreBlock.getUnlocalizedName();
            if (overlayName != null) overlayDescription = overlayName;
            return "Attempt to create Underground Biomes ore for "+blockDescription+" with "+overlayDescription +
                    " after blocks have already been defined";
        }

    }
}
