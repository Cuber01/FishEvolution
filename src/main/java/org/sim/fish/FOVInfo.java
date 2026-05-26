package org.sim.fish;

import org.sim.food.Egg;
import org.sim.Entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class FOVInfo {
    public Map<Entity, Float> FoodsDist = new LinkedHashMap<>();
    public Map<Egg, Float> EggsDist = new LinkedHashMap<>();
    public Map<Fish, Float> FishDist = new LinkedHashMap<>();

    public boolean SawFood = false;
    public boolean SawEggs = false;
    public boolean SawUnfertilizedEggs = false;
    public boolean SawFish = false;

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
