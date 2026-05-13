package org.sim.fish;

import org.sim.Egg;
import org.sim.Entity;
import org.sim.Food;
import processing.core.PVector;

import java.util.Dictionary;
import java.util.Map;

public class FOVInfo {
    public Map<Entity, Float> FoodsDist;
    public Map<Entity, Float> EggsDist;
    public Map<Entity, Float> FishDist;

    public boolean SawFood;
    public boolean SawEggs;
    public boolean SawFish;

    public void AddEntry(Entity entity, PVector myPosition, PVector theirPosition)
    {
        if(entity instanceof Egg)
        {
            EggsDist.put(entity, PVector.sub(myPosition, theirPosition).magSq());
            SawEggs = true;
        } else if(entity instanceof Food)
        {
            FoodsDist.put(entity, PVector.sub(myPosition, theirPosition).magSq());
            SawFood = true;
        }
        else if(entity instanceof Fish)
        {
            FishDist.put(entity, PVector.sub(myPosition, theirPosition).magSq());
            SawFish = true;
        }
    }

}
