package org.sim;

import org.sim.food.Plant;

import java.awt.*;

public class Biome {
    public Biome(int upperBorder,int lowerBorder, Color color, float energyMax, float plantEnergy){
        this.lowerBorder =lowerBorder;
        this.upperBorder =upperBorder;
        this.Color = color;
        this.energyMax = energyMax;
        this.plantEnergy = plantEnergy;
    }

    public Color Color;
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
