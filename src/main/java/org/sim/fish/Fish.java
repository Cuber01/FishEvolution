package org.sim.fish;

import org.sim.*;

import java.util.HashSet;
import java.util.List;

public class Fish extends Entity {
    public Genes Attributes;
    public FOVInfo InFOV;

    public static final float StarvingEnergyMin = 10f; // Will eat eggs
    public static final float BreedingEnergyMin = 80f; // Will breed

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
