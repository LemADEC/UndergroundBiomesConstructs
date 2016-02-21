package exterminatorJeff.undergroundBiomes.api;

import net.minecraft.block.Block;

/**
 *
 * @author Zeno410
 */
public class NamedSlabPair {
	public final NamedBlock half;
	public final NamedBlock full;
	
	public NamedSlabPair(NamedBlock material) {
		half = new NamedSlab(material.internal() + "HalfSlab");
		full = new NamedSlab(material.internal() + "FullSlab");
	}
	
	public static class NamedSlab extends NamedBlock {
		public NamedSlab(String name) {
			super(name);
		}
		
		@Override
		public Block block() {
			// this doesn't register its own items so it doesn't have the block
			Block result = Block.getBlockFromName(internal());
			if (result == null) {
				result = Block.getBlockFromName(external());
			}
			return result;
		}
	}
}
