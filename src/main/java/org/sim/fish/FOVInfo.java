package org.sim.fish;

import org.sim.Egg;
import org.sim.Entity;
import org.sim.Food;
import processing.core.PVector;

import java.util.Dictionary;
import java.util.Map;

public class FOVInfo {
    public Map<Food, Float> FoodsDist;
    public Map<Egg, Float> EggsDist;
    public Map<Fish, Float> FishDist;

    public boolean SawFood;
    public boolean SawEggs;
    public boolean SawFish;

    public void AddEntry(Entity entity, PVector myPosition, PVector theirPosition)
    {
        if(entity instanceof Egg)
        {
            EggsDist.put((Egg)entity, PVector.sub(myPosition, theirPosition).magSq());
            SawEggs = true;
        } else if(entity instanceof Food)
        {
            FoodsDist.put((Food)entity, PVector.sub(myPosition, theirPosition).magSq());
            SawFood = true;
        }
        else if(entity instanceof Fish)
        {
            FishDist.put((Fish)entity, PVector.sub(myPosition, theirPosition).magSq());
            SawFish = true;
        }
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
