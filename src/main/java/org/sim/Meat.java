package org.sim;

import processing.core.PVector;

public class Meat extends Food {

    public Meat(Graphics graphicsHandle, PVector position, float energy)
    {
        super(graphicsHandle, position, energy);
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        Die();
        return Energy * plantToMeatDigestion;
    }
}
