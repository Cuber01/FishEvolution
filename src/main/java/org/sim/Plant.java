package org.sim;

import processing.core.PVector;

public class Plant extends Food {
    private Biome spawnedBy;

    public Plant(Graphics graphicsHandle, PVector position, Biome spawnedBy, float energy) {
        this.graphics_handle = graphicsHandle;
        this.Position = position;
        this.Energy = energy;
        this.spawnedBy = spawnedBy;
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        Die();
        return Energy * (1-plantToMeatDigestion);
    }

    @Override public void Die()
    {
        super.Die();
        spawnedBy.PlantEaten(Energy);
    }
}
