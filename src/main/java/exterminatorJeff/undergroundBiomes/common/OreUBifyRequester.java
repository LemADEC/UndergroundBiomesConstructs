package exterminatorJeff.undergroundBiomes.common;

import Zeno410Utils.Zeno410Logger;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBOreTexturizer;

import java.util.HashSet;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 *
 * @author Zeno410
 */
public class OreUBifyRequester implements UBOreTexturizer {
	
	private static Logger logger = new Zeno410Logger("OrUBifyRequester").logger();
	private HashSet<UBifyRequest> waitingRequests = new HashSet<UBifyRequest>();
	private boolean alreadyRun = false;
	
	OreUBifyRequester() {
		UBAPIHook.ubAPIHook.ubOreTexturizer = this;
	}
	
	@Override
	@Deprecated
	public void setupUBOre(Block oreBlock, int metadata, String overlayName, FMLPreInitializationEvent notUsed) {
		logger.info("setup attempt");
		assert (oreBlock != null);
		assert (metadata >= 0);
		assert (metadata < 16);
		assert (overlayName != null);
		UndergroundBiomes.instance().oreUBifier().setupUBOre(oreBlock, overlayName, metadata);
	}
	
	@Override
	@Deprecated
	public void requestUBOreSetup(Block oreBlock, int metadata, String overlayName) throws BlocksAreAlreadySet {
		logger.info("setup request for " + oreBlock.getLocalizedName());
		assert (oreBlock != null);
		assert (metadata >= 0);
		assert (metadata < 16);
		assert (overlayName != null);
		logger.info("request OK");
		waitingRequests.add(new UBifyRequestWithMetadata(oreBlock, metadata, overlayName));
	}
	
	@Override
	public void setupUBOre(Block oreBlock, int metadata, String overlayName, String blockName, FMLPreInitializationEvent notUsed) {
		setupUBOre(oreBlock, metadata, overlayName);
	}
	
	private void setupUBOre(Block oreBlock, int metadata, String overlayName) {
		UndergroundBiomes.instance().oreUBifier().setupUBOre(oreBlock, overlayName, metadata);
	}
	
	@Override
	public void requestUBOreSetup(Block oreBlock, int metadata, String overlayName, String blockName) throws BlocksAreAlreadySet {
		logger.info("setup request for " + oreBlock.getLocalizedName());
		assert (oreBlock != null);
		assert (metadata >= 0);
		assert (metadata < 16);
		assert (overlayName != null);
		logger.info("request OK");
		waitingRequests.add(new UBifyRequestWithMetadata(oreBlock, metadata, overlayName));
	}
	
	private class UBifyRequest {
		final Block ore;
		final String overlayName;
		
		UBifyRequest(Block ore, String overlayName) {
			this.ore = ore;
			this.overlayName = overlayName;
		}
		
		void fulfill(FMLPreInitializationEvent event) {
			setupUBOre(ore, overlayName, event);
		}
	}
	
	private class UBifyRequestWithMetadata extends UBifyRequest {
		final int metadata;
		
		UBifyRequestWithMetadata(Block ore, int metadata, String overlayName) {
			super(ore, overlayName);
			this.metadata = metadata;
		}
		
		@Override
		void fulfill(FMLPreInitializationEvent event) {
			setupUBOre(ore, metadata, overlayName);
		}
	}
	
	@Override
	public void setupUBOre(Block oreBlock, String overlayName, FMLPreInitializationEvent event) {
		UndergroundBiomes.instance().oreUBifier().setupUBOre(oreBlock, overlayName, event);
	}
	
	@Override
	public void requestUBOreSetup(Block oreBlock, String overlayName) throws BlocksAreAlreadySet {
		if (alreadyRun) {
			BlocksAreAlreadySet error = new BlocksAreAlreadySet(oreBlock, overlayName);
			if (UndergroundBiomes.crashOnProblems())
				throw error;
			UndergroundBiomes.logger.error(error.toString());
		} else {
			waitingRequests.add(new UBifyRequest(oreBlock, overlayName));
		}
	}
	
	void fulfillRequests(FMLPreInitializationEvent event) {
		// this should not be run by anyting other than the Underground Biomes Constructs object
		alreadyRun = true;
		for (UBifyRequest request : waitingRequests) {
			request.fulfill(event);
		}
		waitingRequests.clear();
	}
	
	@Override
	public void redoOres(int x, int z, World world) {
		UndergroundBiomes.instance().redoOres(x, z, world);
	}
}