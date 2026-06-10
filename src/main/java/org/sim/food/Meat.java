package org.sim.food;

import org.sim.Entity;
import org.sim.SimManager;
import processing.core.PVector;
/**
 * Represents a piece of meat left behind by a dead fish, which slowly sinks to the bottom.
 */
public class Meat extends Entity {
    private static final float DefaultEnergy = 10f;
    private static final float drowningSpeed = 0.7f;

    /**
     * Creates a piece of meat at a given position containing energy from the deceased fish.
     */
    public Meat(PVector position, float energy)
    {
        this.Position= position;
        this.Energy = energy + DefaultEnergy;
    }

    /**
     * Sinks the meat downwards each tick until it reaches the bottom of the simulation canvas.
     */
    @Override
    public void Update()
    {
        if(Position.y + 10 < SimManager.CanvasY)
        {
            Position.y += drowningSpeed;
        }
    }

    /**
     * Handles a fish eating this meat and returns energy based on carnivore digestion efficiency.
     */
    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        if(IsDead) return 0;

        Die();
        return Energy * plantToMeatDigestion;
    }
}
