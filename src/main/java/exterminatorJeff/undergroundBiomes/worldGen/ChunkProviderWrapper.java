package exterminatorJeff.undergroundBiomes.worldGen;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;

public class ChunkProviderWrapper implements IChunkProvider {
    private IChunkProvider wrappee;
    private final CurrentWorldMemento.Manager currentWorldManager = new CurrentWorldMemento.Manager();
    public ChunkProviderWrapper(IChunkProvider toWrap) {
        wrappee = toWrap;
    }
    /**
     * Checks to see if a chunk exists at x, y
     */
    @Override
	public boolean chunkExists(int i, int j) {return wrappee.chunkExists(i,j);}

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
	public Chunk provideChunk(int i, int j) {
        Chunk result = wrappee.provideChunk(i,j);
        return result;
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    @Override
	public Chunk loadChunk(int i, int j) {
        return wrappee.loadChunk(i,j);
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
	public void populate(IChunkProvider ichunkprovider, int i, int j) {

        try {
            wrappee.populate(ichunkprovider,i,j);
        } catch (RuntimeException e) {
            CurrentWorldMemento memento = this.currentWorldManager.memento();
            try {
                wrappee.populate(ichunkprovider, i, j);
            } catch (Exception e2) {
                throw e;
            }
            memento.restore();
        }
    }
            

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    @Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        return this.wrappee.saveChunks(flag, iprogressupdate);
    }

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    @Override
	public boolean unloadQueuedChunks(){
        return wrappee.unloadQueuedChunks();
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    @Override
	public boolean canSave() {
        return wrappee.canSave();
    }

    /**
     * Converts the instance data to a readable string.
     */
    @Override
	public String makeString() {
        return wrappee.makeString();
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
	public List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i, int j, int k) {
        return wrappee.getPossibleCreatures(enumcreaturetype, i, j, k);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    @Override
	public ChunkPosition func_147416_a(World world, String s, int i, int j, int k) {
        return wrappee.func_147416_a(world, s, i, j, k);
    }

    @Override
	public int getLoadedChunkCount() {
        return this.wrappee.getLoadedChunkCount();
    }

    @Override
	public void recreateStructures(int i, int j){
        this.wrappee.recreateStructures(i, j);
    }

    @Override
	public void saveExtraData() {
        this.wrappee.saveExtraData();
    }
}