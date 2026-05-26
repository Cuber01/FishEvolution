package org.sim;

import processing.core.PVector;



public class Entity {
    public PVector Position;
    public static final float DistTolerance = 1f;
    public boolean IsDead = false;
    public float Energy;

    public void Update() {}

    public float Bite(float plantToMeatDigestion, float damage)
    {
        return -1;
    }

    public void Die()
    {
        IsDead = true;
        SimManager.DespawnEntity(this);
    }
}
