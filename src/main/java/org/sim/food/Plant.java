package org.sim.food;

import org.sim.Biome;
import org.sim.Entity;
import processing.core.PVector;

public class Plant extends Entity {
    private Biome spawnedBy;

    public Plant(PVector position, Biome spawnedBy, float energy) {
        this.Position = position;
        this.Energy = energy;
        this.spawnedBy = spawnedBy;
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        if(IsDead) return 0;

        Die();
        return Energy * (1-plantToMeatDigestion);
    }

    @Override public void Die()
    {
        super.Die();
        spawnedBy.PlantEaten(Energy);
    }
}
