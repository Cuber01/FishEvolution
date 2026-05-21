package org.sim;

import processing.core.PVector;



public class Entity {
    public PVector Position;
    public static final float DistTolerance = 1f;
    public boolean IsDead = false;
    public org.sim.Graphics graphics_handle;
    public Entity(){}

//    public Entity(Graphics parent) {
//        graphics_handle=parent;
//    }

    // public Texture texture

    public void Update(Biome currentBiome) {}

    public float Bite(float plantToMeatDigestion, float damage)
    {
        return -1;
    }

    public void Die()
    {
        IsDead = true;
        SimManager.EntitiesToRemove.add(this);
    }
}
