package org.sim;

import processing.core.PVector;

public class Food extends Entity {
    public FoodType Type;
    public float Energy;

    public Food(Graphics graphicsHandle, PVector position, float energy)
    {
        this.graphics_handle = graphicsHandle;
        this.Position = position;
        this.Energy = energy;
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        Die();
        return Energy * (1-plantToMeatDigestion);
    }
}
