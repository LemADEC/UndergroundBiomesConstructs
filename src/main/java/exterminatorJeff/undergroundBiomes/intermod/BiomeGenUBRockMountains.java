
package exterminatorJeff.undergroundBiomes.intermod;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import highlands.biome.BiomeDecoratorHighlands;
import highlands.biome.BiomeGenBaseHighlands;
import highlands.biome.BiomeGenRockMountains;
import highlands.worldgen.WorldGenHighlandsShrub;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenUBRockMountains extends BiomeGenRockMountains
{
	private static final Height biomeHeight = new Height(1.8F, 0.5F);

	public BiomeGenUBRockMountains(int par1)
    {
        super(par1);

        int trees = -999;
	    int grass = 2;
	    int flowers = 0;
	    ((BiomeGenBase)this).theBiomeDecorator = new BiomeDecoratorHighlands(this, trees, grass, flowers);

        //this.spawnableCreatureList.clear();

        ((BiomeGenBase)this).fillerBlock = Blocks.stone;
        ((BiomeGenBase)this).setHeight(biomeHeight);
        ((BiomeGenBase)this).temperature = 0.6F;
        ((BiomeGenBase)this).rainfall = 0.5F;

        //this.setEnableSnow();
        ((BiomeGenBase)this).setBiomeName("Rock Mountains");
    }

    @Override
    public WorldGenAbstractTree func_150567_a(Random par1Random)
    {
        return (WorldGenAbstractTree)(new WorldGenHighlandsShrub(1, 1));
    }

    @Override
    public void func_76728_a(World world, Random random, int x, int z) {
        decorate(world, random, x, z);
    }
    @Override
	public void decorate(World world, Random random, int x, int z) {
		BiomeGenBaseHighlands biome = this;
    	if(random.nextInt(2) == 0)
    		new WorldGenUBMountain(15, 15, false, 1).generate(world, random, x+random.nextInt(16), 128, z+random.nextInt(16));

    	((BiomeGenBase)this).theBiomeDecorator.decorateChunk(world, random, biome, x, z);

    	((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 20, ((BiomeGenBase)this).theBiomeDecorator.ironGen, 64, 128);
    	((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 40, ((BiomeGenBase)this).theBiomeDecorator.ironGen, 0, 128);
    	((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 8, ((BiomeGenBase)this).theBiomeDecorator.redstoneGen, 16, 32);
    	((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 1, ((BiomeGenBase)this).theBiomeDecorator.lapisGen, 32, 64);
    	((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 2, ((BiomeGenBase)this).theBiomeDecorator.goldGen, 32, 64);
    	((BiomeDecoratorHighlands)((BiomeGenBase)this).theBiomeDecorator).genOreHighlands(world, random, x, z, 1, ((BiomeGenBase)this).theBiomeDecorator.diamondGen, 16, 32);
    }
}
