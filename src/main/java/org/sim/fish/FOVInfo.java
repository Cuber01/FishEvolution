package org.sim.fish;

import org.sim.Egg;
import org.sim.Entity;
import org.sim.Food;
import processing.core.PVector;

import java.util.Dictionary;
import java.util.Map;

public class FOVInfo {
    public Map<Food, Float> FoodsDist = new java.util.HashMap<>();
    public Map<Egg, Float> EggsDist = new java.util.HashMap<>();
    public Map<Fish, Float> FishDist = new java.util.HashMap<>();

    public boolean SawFood = false;
    public boolean SawEggs = false;
    public boolean SawFish = false;

    public void AddEntry(Entity entity, float distanceSquared)
    {
        if(entity instanceof Egg)
        {
            EggsDist.put((Egg)entity, distanceSquared);
            SawEggs = true;
        } else if(entity instanceof Food)
        {
            FoodsDist.put((Food)entity, distanceSquared);
            SawFood = true;
        }
        else if(entity instanceof Fish)
        {
            FishDist.put((Fish)entity, distanceSquared);
            SawFish = true;
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
    }

    public boolean FishInFOV(Fish f)
    {
        if(!SawFish)
        {
            return false;
        } else {
            Float p = FishDist.get(f);
            if(p == null)
            {
                return false;
            } else {
                return true;   
            }
        }
    }

}
