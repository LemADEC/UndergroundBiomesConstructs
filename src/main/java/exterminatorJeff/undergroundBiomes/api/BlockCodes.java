package exterminatorJeff.undergroundBiomes.api;

/**
 *
 * @author Zeno410
 */

import Zeno410Utils.*;
import net.minecraft.block.Block;

public class BlockCodes extends BlockState {
	public final NamedBlock name;
	public final BlockCodes onDrop;
	private final int metadataHashCode;
	
	public BlockCodes(Block block, int metadata) {
		super(block, metadata);
		name = null;
		onDrop = this;
		metadataHashCode = new Integer(metadata).hashCode();
	}
	
	public BlockCodes(NamedBlock namer, int metadata) {
		super(namer.block(), metadata);
		name = namer;
		if (block == null) {
			throw new RuntimeException("couldn't find block for " + namer.internal());
		}
		onDrop = this;
		metadataHashCode = new Integer(metadata).hashCode();
	}
	
	public BlockCodes(NamedBlock namer, int metadata, BlockCodes onDrop) {
		super(namer.block(), metadata);
		name = namer;
		this.onDrop = onDrop;
		metadataHashCode = new Integer(metadata).hashCode();
	}
	
	@Override
	public int hashCode() {
		return block.hashCode() + metadataHashCode;
	}
	
	@Override
	public boolean equals(Object compared) {
		if (compared instanceof BlockCodes) {
			BlockCodes comparedCodes = (BlockCodes) compared;
			if ((block == comparedCodes.block) && (metadata == comparedCodes.metadata))
				return true;
		}
		return false;
	}
}
