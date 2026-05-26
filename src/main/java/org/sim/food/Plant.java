package org.sim.food;

import org.sim.Biome;
import org.sim.Processing;
import processing.core.PVector;

public class Plant extends Food {
    private Biome spawnedBy;

    public Plant(Processing graphicsHandle, PVector position, Biome spawnedBy, float energy) {
        this.graphics_handle = graphicsHandle;
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
