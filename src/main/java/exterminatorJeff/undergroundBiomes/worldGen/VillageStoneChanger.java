package exterminatorJeff.undergroundBiomes.worldGen;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockMeta;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

import cpw.mods.fml.common.eventhandler.Event.Result;

/**
 *
 * @author Zeno410
 */

public class VillageStoneChanger {
	
	private UBStoneCodes preferredStone;
	boolean replacing;
	
	public void setStoneCode(UBStoneCodes newCode) {
		preferredStone = newCode;
	}
	
	public void onVillageSelectBlock(GetVillageBlockID event) {
		if (preferredStone == null) {
			return;
		}
		if (event.original == Blocks.log) {
			event.replacement = event.original;
		}
		if (event.original == Blocks.cobblestone) {
			event.replacement = preferredStone.onDrop.block;
			replacing = true;
			event.setResult(Result.DENY);
		}
		if (event.original == Blocks.planks) {
			event.replacement = event.original;
		}
		if (event.original == Blocks.oak_stairs) {
			event.replacement = event.original;
		}
		if (event.original == Blocks.stone_stairs) {
			if (UndergroundBiomes.stairsOn()) {
				event.replacement = Blocks.oak_stairs;
				event.setResult(Result.DENY);
			}
		}
		if (event.original == Blocks.gravel) {
			if (UndergroundBiomes.replaceVillageGravel()) {
				event.replacement = preferredStone.brickVersionEquivalent().block;
				replacing = true;
				event.setResult(Result.DENY);
			}
		}
		if (event.original == Blocks.stone_slab) {
			event.replacement = preferredStone.slabVersionEquivalent().block;
			replacing = true;
			event.setResult(Result.DENY);
		}
	}
	
	public void onVillageSelectMeta(GetVillageBlockMeta event) {
		if (preferredStone == null) {
			return;
		}
		if (replacing == true) {
			event.replacement = preferredStone.onDrop.metadata;
			event.setResult(Result.DENY);
			replacing = false;
		}
	}
}
