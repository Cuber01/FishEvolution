package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

public class Fish extends Entity {
    public Genes Attributes;
    public FOVInfo InFOV;
    public PVector Velocity;

    // TODO Make this private
    public final PursueFoodState PursueFoodState = new PursueFoodState(this, FishStateTypes.PursuingFood);
    private final FertilizeEggsState FertilizeEggs = new FertilizeEggsState(this, FishStateTypes.FertilizingEggs);
    private final LayEggsState LayEggs = new LayEggsState(this, FishStateTypes.LayingEggs);
    private final FleeState Flee = new FleeState(this, FishStateTypes.Fleeing);
    private final SearchState Search = new SearchState(this, FishStateTypes.Searching);
    public State<Fish, FishStateTypes> currentState = Search;

    public static final float StarvingEnergyMin = 10f; // Will eat eggs
    public static final float BreedingEnergyMin = 80f; // Will breed
    public static final float MaxEnergy = 100f;

    public Sex Gender;
    public int HP;
    public float Energy;

    @Override
    public void Update(Biome currentBiome) {
        FishStateTypes newState = currentState.CheckTransitions();
        if (newState != currentState.AssociatedType) {
            currentState.Exit();
            switch (newState) {
                case Searching:
                    currentState = Search;
                    break;

                case PursuingFood:
                    currentState = PursueFoodState;
                    break;

                case FertilizingEggs:
                    currentState = FertilizeEggs;
                    break;

                case LayingEggs:
                    currentState = LayEggs;
                    break;

                case Fleeing:
                    currentState = Flee;
                    break;
            }
            currentState.Enter();
        }

        currentState.Update();
    }
}
