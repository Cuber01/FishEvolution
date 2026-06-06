package org.sim.fish;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sim.fish.genes.Genes;
import org.sim.fish.genes.Sex;
import static org.junit.jupiter.api.Assertions.*;

class SearchStateTest {

    @Test
    @DisplayName("Male fish should immediately transition to FertilizingEggs when seeing unfertilized eggs")
    void testMaleFishTransitionsToFertilizing() {
        // Set up a male fish in the default state
        Fish maleFish = new Fish();
        maleFish.sex = Sex.Male;
        maleFish.Attributes = new Genes(2.0f, 100f, 10f, 60f, 0.5f, 0.5f);

        // Simulate field of view: fish spots unfertilized eggs
        maleFish.InFOV.Reset();
        maleFish.InFOV.SawUnfertilizedEggs = true;

        // Trigger the decision-making mechanism
        FishStateTypes nextState = maleFish.CurrentState.CheckTransitions();

        // Verify the expected behavior
        assertEquals(FishStateTypes.FertilizingEggs, nextState,
                "A male fish should immediately want to fertilize unfertilized eggs upon seeing them!");
    }

    @Test
    @DisplayName("Starving fish should ignore breeding requirements and pursue eggs as food source")
    void testStarvingFishPursuesEggAsFood() {
        // Set up a very hungry fish (energy below StarvingEnergyMin)
        Fish hungryFish = new Fish();
        hungryFish.Energy = Fish.StarvingEnergyMin - 5f;
        hungryFish.Attributes = new Genes(2.0f, 100f, 10f, 60f, 0.5f, 0.5f);

        // Simulate field of view: fish sees eggs
        hungryFish.InFOV.Reset();
        hungryFish.InFOV.SawEggs = true;

        // Verify the decision
        FishStateTypes nextState = hungryFish.CurrentState.CheckTransitions();

        // Expecting the fish to pursue the egg as food source
        assertEquals(FishStateTypes.PursuingFood, nextState, "A starving fish should treat eggs as a food source!");
        assertEquals(EntityTypes.Egg, hungryFish.TargetType, "The target type of the attack should be set to Egg!");
    }

}