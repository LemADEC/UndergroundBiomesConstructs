package exterminatorJeff.undergroundBiomes.worldGen;

import exterminatorJeff.undergroundBiomes.common.PerlinNoiseGenerator;
import exterminatorJeff.undergroundBiomes.api.NamedBlock;

public class BiomeGenUndergroundBase {
	
	public String biomeName;
	
	public final int biomeID;
	
	public boolean hasStrata = false;
	
	public StrataLayer[] strata;
	
	public final PerlinNoiseGenerator strataNoise;
	
	public final UBStoneCodes fillerBlockCodes;
	
	protected BiomeGenUndergroundBase(int ID, NamedBlock filler, int metadataValue, BiomeGenUndergroundBase[] biomeList) {
		this.biomeID = ID;
		this.fillerBlockCodes = new UBStoneCodes(filler, metadataValue);
		strataNoise = new PerlinNoiseGenerator(1);
		biomeList[ID] = this;
	}
	
	protected BiomeGenUndergroundBase AddStrataLayers(StrataLayer[] parStrata) {
		hasStrata = true;
		this.strata = parStrata;
		return this;
	}
	
	public UBStoneCodes getStrataBlockAtLayer(int y) {
		for (int i = 0; i < strata.length; i++) {
			if (strata[i].valueIsInLayer(y) == true) {
				return strata[i].codes;
			}
		}
		return fillerBlockCodes;
	}
	
	protected BiomeGenUndergroundBase setName(String name) {
		this.biomeName = name;
		return this;
	}
}
