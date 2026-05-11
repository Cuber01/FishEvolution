package org.example;

public class Biome {
    // Kolor

    public int foodPerUpdate;
    public int foodMax;

    public float visionPenalty;
    public int upperBorder;
    public int lowerBorder;

    public void SpawnFood() {
        SimManager.EntitiesToAdd.add(new Food());
    }
}
