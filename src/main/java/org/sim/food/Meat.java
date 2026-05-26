package org.sim.food;

import org.sim.Entity;
import org.sim.SimManager;
import processing.core.PVector;

public class Meat extends Entity {
    public static final float DefaultEnergy = 10f;
    private static final float drowningSpeed = 0.7f;

    public Meat(PVector position, float energy)
    {
        this.Position= position;
        this.Energy = energy + DefaultEnergy;
    }

    // TODO Allow fish to eat just a fragment of food if they're near max?
    @Override
    public void Update()
    {
        if(Position.y + 10 < SimManager.CanvasY)
        {
            Position.y += drowningSpeed;
        }
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        if(IsDead) return 0;

        Die();
        return Energy * plantToMeatDigestion;
    }
}
