package org.sim;

import processing.core.PVector;

public class Meat extends Food {
    public static final float DefaultEnergy = 10f;
    public static final float drowningSpeed = 1f;

    public Meat(Graphics graphicsHandle, PVector position, float energy)
    {
        this.graphics_handle = graphicsHandle;
        this.Position= position;
        this.Energy = energy + DefaultEnergy;
    }

    // TODO Allow fish to eat just a fragment of food if they're near max?
    @Override
    public void Update(Biome biome)
    {
        if(Position.y + 2 < SimManager.CanvasY)
        {
            Position.y += drowningSpeed;
        }
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        Die();
        return Energy * plantToMeatDigestion;
    }
}
