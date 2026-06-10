package org.sim.food;

import org.sim.Biome;
import org.sim.Entity;
import processing.core.PVector;
/**
 * Represents a static plant (algae) that spawns in water biomes and serves as food for herbivores.
 */
public class Plant extends Entity {
    private Biome spawnedBy;
    /**
     * Creates a new plant instance at a specific coordinate connected to its parent biome.
     */
    public Plant(PVector position, Biome spawnedBy, float energy) {
        this.Position = position;
        this.Energy = energy;
        this.spawnedBy = spawnedBy;
    }
    /**
     * Triggers plant destruction when eaten and returns energy calculated with herbivore digestion.
     */
    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        if(IsDead) return 0;

        Die();
        return Energy * (1-plantToMeatDigestion);
    }
    /**
     * Destroys the plant and notifies the parent biome to allow new plants to grow.
     */
    @Override public void Die()
    {
        super.Die();
        spawnedBy.PlantEaten(Energy);
    }
}
