package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.common.WorldGenManager;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumn;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumnProvider;
import exterminatorJeff.undergroundBiomes.api.BlockCodes;

import net.minecraft.world.chunk.IChunkProvider;

/**
 *
 * @author Zeno41o
 */
public class UBBlockProvider implements UBStrataColumnProvider {
	
	private BiomeUndergroundCacheBlock chunkBiomeArray; // one chunk cached for speed
	private final WorldGenManager worldGen;
	
	public UBBlockProvider(WorldGenManager _worldGen) {
		worldGen = _worldGen;
		chunkBiomeArray = worldGen.chunkBiomeCache(0, 0);//preset so presence is an invariant;
	}
	
	private UBStrataColumn strataColumn(final StrataLayer[] strata, final UBStoneCodes fillerBlockCodes, final int variation) {
		return new UBStrataColumn() {
			
			@Override
			public UBStoneCodes stone(int y) {
				for (int i = 0; i < strata.length; i++) {
					if (strata[i].valueIsInLayer(y + variation) == true) {
						return strata[i].codes;
					}
				}
				return fillerBlockCodes;
			}
			
			@Override
			public BlockCodes cobblestone(int height) {
				return stone(height).onDrop;
			}
			
			@Override
			public BlockCodes cobblestone() {
				return stone().onDrop;
			}
			
			@Override
			public BlockCodes stone() {
				return fillerBlockCodes;
			}
		};
	}
	
	@Override
	public UBStrataColumn strataColumn(int x, int z) {
		// make sure we have the right chunk
		if (!chunkBiomeArray.contains(x, z)) {
			chunkBiomeArray = worldGen.chunkBiomeCache(x, z);
		}
		BiomeGenUndergroundBase biome = chunkBiomeArray.getBiomeGenAt(x, z);
		int variation = (int) (biome.strataNoise.noise(x / 55.533, z / 55.533, 3, 1, 0.5) * 10 - 5);
		return strataColumn(biome.strata, biome.fillerBlockCodes, variation);
	}
	
	@Override
	public boolean inChunkGenerationAllowed() {
		return this.worldGen.inChunkGenerationAllowed();
	}
	
	@Override
	public IChunkProvider UBChunkProvider(IChunkProvider wrapped) {
		return worldGen.UBChunkProvider(wrapped);
	}
}
