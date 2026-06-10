package org.sim.fish;

import org.sim.food.Egg;
import org.sim.Entity;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Field of Vision Info
 * Helper class that stores structured information about everything a fish can see.
 * It separates detected objects into categorized maps sorted by distance.
 */
public class FOVInfo {
    /* This class performs the most expensive mathematical operations in the entire project
     and as such was optimized, resulting in slightly obtuse code. We keep multiple
     Maps for different entity types to lower the amount of iterations necessary to
     look through them. Furthermore, we have multiple booleans to keep track of state
     to decrease the amount of looking through maps further.
    */

    // We have to use LinkedHashMaps so that the order of elements is deterministic.
    // Otherwise, despite having the same seed two runs of the simulation could be different.
    public Map<Entity, Float> FoodsDist = new LinkedHashMap<>();
    public Map<Egg, Float> EggsDist = new LinkedHashMap<>();
    public Map<Fish, Float> FishDist = new LinkedHashMap<>();

    public boolean SawFood = false;
    public boolean SawEggs = false;
    public boolean SawUnfertilizedEggs = false;
    public boolean SawFish = false;

    /**
     * Categorizes a visible entity and records its squared distance from the observing fish.
     */
    public void AddEntry(Entity entity, float distanceSquared)
    {
        if(entity instanceof Egg)
        {
            EggsDist.put((Egg)entity, distanceSquared);
            SawEggs = true;

            if(!((Egg) entity).Fertilized)
            {
                SawUnfertilizedEggs = true;
            }

        }
        else if(entity instanceof Fish)
        {
            FishDist.put((Fish)entity, distanceSquared);
            SawFish = true;
        } else {
            FoodsDist.put( entity, distanceSquared);
            SawFood = true;
        }
    }

    /**
     * Clears all vision data and resets detection flags before the next scan step.
     */
    public void Reset()
    {
        FoodsDist.clear();
        EggsDist.clear();
        FishDist.clear();
        SawFood = false;
        SawEggs = false;
        SawFish = false;
        SawUnfertilizedEggs = false;
    }
    /**
     * Checks if a specific fish is currently present in the detected field of view list.
     */
    public boolean FishInFOV(Fish f)
    {
        if(!SawFish)
        {
            return false;
        } else {
            Float p = FishDist.get(f);
            return p != null;
        }
    }

}
