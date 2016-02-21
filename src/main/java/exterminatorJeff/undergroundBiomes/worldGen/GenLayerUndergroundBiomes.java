package exterminatorJeff.undergroundBiomes.worldGen;

import net.minecraft.world.gen.layer.*;

import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class GenLayerUndergroundBiomes extends GenLayer{
    /** this sets all the biomes that are allowed to appear in the overworld */
    private BiomeGenUndergroundBase[] allowedBiomes;

    public GenLayerUndergroundBiomes(long par1, GenLayer par3GenLayer, UndergroundBiomeSet biomeSet) {
        super(par1);
        if (!UndergroundBiomes.vanillaStoneBiomes()) {
            this.allowedBiomes = new BiomeGenUndergroundBase[] {biomeSet.igneous1, biomeSet.igneous2,
                    biomeSet.igneous3, biomeSet.igneous4,
                    biomeSet.igneous5, biomeSet.igneous6, biomeSet.igneous7,
                    biomeSet.igneous8, biomeSet.igneous9, biomeSet.igneous10,
                    biomeSet.igneous11, biomeSet.igneous12, biomeSet.igneous13,
                    biomeSet.igneous14, biomeSet.igneous15, biomeSet.igneous16,
                    biomeSet.metamorphic1, biomeSet.metamorphic2, biomeSet.metamorphic3,
                    biomeSet.metamorphic4, biomeSet.metamorphic5, biomeSet.metamorphic6,
                    biomeSet.metamorphic7, biomeSet.metamorphic8, biomeSet.metamorphic9,
                    biomeSet.metamorphic10, biomeSet.metamorphic11, biomeSet.metamorphic12,
                    biomeSet.metamorphic13, biomeSet.metamorphic14, biomeSet.metamorphic15,
                    biomeSet.metamorphic16};
        } else {
            this.allowedBiomes = new BiomeGenUndergroundBase[] {biomeSet.igneous1, biomeSet.igneous2,
                    biomeSet.igneous3, biomeSet.igneous4,
                    biomeSet.igneous5, biomeSet.igneous6, biomeSet.igneous7,
                    biomeSet.igneous8, biomeSet.igneous9, biomeSet.igneous10,
                    biomeSet.igneous11, biomeSet.igneous12, biomeSet.igneous13,
                    biomeSet.igneous14, biomeSet.igneous15, biomeSet.igneous16,
                    biomeSet.metamorphic1, biomeSet.metamorphic2, biomeSet.metamorphic3,
                    biomeSet.metamorphic4, biomeSet.metamorphic5, biomeSet.metamorphic6,
                    biomeSet.metamorphic7, biomeSet.metamorphic8, biomeSet.metamorphic9,
                    biomeSet.metamorphic10, biomeSet.metamorphic11, biomeSet.metamorphic12,
                    biomeSet.metamorphic13, biomeSet.metamorphic14, biomeSet.metamorphic15,
                    biomeSet.metamorphic16,
                    biomeSet.vanillaStone1, biomeSet.vanillaStone2,
                    biomeSet.vanillaStone3, biomeSet.vanillaStone4
                    };
        }
        this.parent = par3GenLayer;

    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    @Override
	public int[] getInts(int par1, int par2, int par3, int par4)
    {
        //int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        int[] var6 = IntCache.getIntCache(par3 * par4);

        for (int var7 = 0; var7 < par4; ++var7) {
            for (int var8 = 0; var8 < par3; ++var8){
                this.initChunkSeed(var8 + par1, var7 + par2);
                var6[var8 + var7 * par3] =this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;
            }
        }

        return var6;
    }
}
