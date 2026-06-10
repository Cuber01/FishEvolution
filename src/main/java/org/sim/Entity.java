package org.sim;

import processing.core.PVector;

/**
 * Represents the base class for all moving and non-moving objects in the simulation world.
 * It tracks basic properties like position, energy level, and life status.
 */

public class Entity {
    public PVector Position;
    public static final float DistTolerance = 1f;
    public boolean IsDead = false;
    public float Energy;

    /**
     * Updates the inner logic and state of the entity during each simulation step.
     */
    public void Update() {}
    /**
     * Handles the event when another entity tries to bite or eat this object.
     * @param plantToMeatDigestion The digestion efficiency coefficient of the attacker.
     * @param damage The amount of damage dealt by the attacker.
     * @return The amount of energy transferred to the attacker.
     */
    public float Bite(float plantToMeatDigestion, float damage)
    {
        return -1;
    }
    /**
     * Changes the status of the entity to dead and triggers its removal from the simulation.
     */
    public void Die()
    {
        IsDead = true;
        SimManager.DespawnEntity(this);
    }
}
