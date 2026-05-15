package org.sim;

import java.util.ArrayList;
import java.util.List;

public class Biome {
    public Biome(int uborder,int lborder,int colorR,int colorG,int colorB){
        lowerBorder=lborder;
        upperBorder=uborder;
        color.add(colorR);
        color.add(colorG);
        color.add(colorB);
    }

    public List<Integer> color = new ArrayList<>(3);

    public float plantEnergy = 10;
    public float energyMax = 100;
    public float energySpawned = 0;

    public float visionPenalty;
    public int upperBorder;
    public int lowerBorder;

    public void SpawnPlants(Graphics graphicsHandle) {
        if(energySpawned >= energyMax) return;

        SimManager.EntitiesToAdd.add(new Plant(graphicsHandle,
                RND.RandomVector2(0, SimManager.CanvasX, upperBorder, lowerBorder),
                this,
                plantEnergy));
        energySpawned += plantEnergy;
    }

    public void PlantEaten(float energy)
    {
        energySpawned -= energy;
    }
}
