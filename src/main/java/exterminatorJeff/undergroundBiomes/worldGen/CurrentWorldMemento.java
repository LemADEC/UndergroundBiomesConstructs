
package exterminatorJeff.undergroundBiomes.worldGen;

import java.util.HashSet;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 *
 * @author Zeno410
 */
public class CurrentWorldMemento {
    private int remembered;
    private int [] indices = new int [256];
    private World [] worlds = new World [256];
    private Manager manager;

    private CurrentWorldMemento(Manager manager) {
        this.manager = manager;
    }

    private void save() {
        BiomeGenBase [] biomes = BiomeGenBase.getBiomeGenArray();
        for (int i = 0 ;i < biomes.length; i++) {
            BiomeGenBase biome = biomes[i];
            if (biome != null) {
                World currentWorld = biome.theBiomeDecorator.currentWorld;
                if (currentWorld != null) {
                    worlds[remembered] = currentWorld;
                    indices[remembered++] = i;
                    biome.theBiomeDecorator.currentWorld = null;
                }
            }
        }
    }

    void restore() {
        BiomeGenBase [] biomes = BiomeGenBase.getBiomeGenArray();
        for (int i = 0;  i < remembered; i++) {
            BiomeGenBase biome = biomes[indices[i]];
            biome.theBiomeDecorator.currentWorld = worlds[i];
        }
        remembered = 0;
        manager.release(this);
    }

    static class Manager {
        private HashSet<CurrentWorldMemento> available = new HashSet<CurrentWorldMemento>();

        public Manager() {
        }

        private void release(CurrentWorldMemento freed) {
            available.add(freed);
        }

        CurrentWorldMemento memento() {
            CurrentWorldMemento result;
            if (available.size() >0) {
                result = available.iterator().next();
                available.remove(result);
            } else {result = new CurrentWorldMemento(this);}
            result.save();
            return result;
        }
    }

}