package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

public class Fish extends Entity {
    public Genes Attributes;
    public FOVInfo InFOV;
    public PVector Velocity;

    public PursueFoodState PursueFoodState;

    public static final float StarvingEnergyMin = 10f; // Will eat eggs
    public static final float BreedingEnergyMin = 80f; // Will breed
    public static final float MaxEnergy = 100f;

    public enum FishState {
        Searching,
        PursuingFood,
        Fleeing,
        LayingEggs,
        FertilizingEggs
    }

    public Sex Gender;
    public int HP;
    public float Energy;


}
