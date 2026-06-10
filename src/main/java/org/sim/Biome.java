package org.sim;

import org.sim.food.Plant;

import java.awt.*;
/**
 * Represents a horizontal water depth zone (layer) with specific environmental parameters.
 * It defines how fast plants can grow and sets maximum energy limits for the zone.
 */
public class Biome {
    /**
     * Creates a new biome layer with defined boundaries, colors, and energy capacities.
     */
    public Biome(int upperBorder,int lowerBorder, Color color, float energyMax, float plantEnergy){
        this.lowerBorder =lowerBorder;
        this.upperBorder =upperBorder;
        this.Color = color;
        this.energyMax = energyMax;
        this.plantEnergy = plantEnergy;
    }

    public Color Color;
    private float plantEnergy = 10;
    private float energyMax = 100;
    private float energySpawned = 0;

    public float visionPenalty;
    public int upperBorder;
    public int lowerBorder;
    /**
     * Spawns new plant entities inside the biome boundaries if the total energy limit is not exceeded.
     */
    public void SpawnPlants() {
        if(energySpawned >= energyMax) return;

        SimManager.SpawnEntity(new Plant(
                RND.RandomVector2(0, SimManager.CanvasX, upperBorder, lowerBorder),
                this,
                plantEnergy));
        energySpawned += plantEnergy;
    }
    /**
     * Decreases the biome's tracked energy count when a plant inside this zone is eaten.
     * Called by plants on death.
     */
    public void PlantEaten(float energy)
    {
        energySpawned -= energy;
    }
}
