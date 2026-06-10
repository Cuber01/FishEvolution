package org.sim.fish;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sim.Consts;
import org.sim.fish.genes.Sex;
import org.sim.fish.genes.Genes;
import processing.core.PVector;

import java.lang.constant.Constable;

import static org.junit.jupiter.api.Assertions.*;

class FishStatesTest {

    private Fish fish;

    @BeforeEach
    void setUp() {
        // Initialize default fish for testing
        fish = new Fish();
        fish.InFOV.Reset();
    }

    // ========================================================
    // 1. SEARCH STATE TESTS (CheckTransitions Logic)
    // ========================================================

    @Test
    @DisplayName("SearchState: Fish should remain in Searching state by default")
    void testSearchStateDefault() {
        SearchState searchState = new SearchState(fish, FishStateTypes.Searching);

        // Ensure energy is in a safe range (between starving and breeding thresholds)
        fish.Energy = 50f;

        FishStateTypes nextState = searchState.CheckTransitions();
        assertEquals(FishStateTypes.Searching, nextState,
                "Fish without external stimuli should continue searching.");
    }

    @Test
    @DisplayName("SearchState -> FertilizingEggs: Male fish transitions to fertilizing state upon seeing unfertilized eggs")
    void testSearchStateToFertilizingEggs() {
        SearchState searchState = new SearchState(fish, FishStateTypes.Searching);

        fish.sex = Sex.Male;
        fish.InFOV.SawUnfertilizedEggs = true;

        FishStateTypes nextState = searchState.CheckTransitions();
        assertEquals(FishStateTypes.FertilizingEggs, nextState,
                "Male fish should immediately transition to fertilizing eggs.");
    }

    @Test
    @DisplayName("SearchState -> PursuingFood: Fish sees food and has energy below the breeding threshold")
    void testSearchStateToPursueFood() {
        SearchState searchState = new SearchState(fish, FishStateTypes.Searching);

        fish.InFOV.SawFood = true;
        fish.Energy = Fish.BreedingEnergyMin - 10f;

        FishStateTypes nextState = searchState.CheckTransitions();

        assertEquals(FishStateTypes.PursuingFood, nextState);
        assertEquals(EntityTypes.Food, fish.TargetType, "TargetType should be set to Food.");
    }

    @Test
    @DisplayName("SearchState -> PursuingFood (Eating eggs): Starving fish treats eggs as food")
    void testSearchStateToEatEggsWhenStarving() {
        SearchState searchState = new SearchState(fish, FishStateTypes.Searching);

        fish.InFOV.SawEggs = true;
        fish.Energy = Fish.StarvingEnergyMin;

        FishStateTypes nextState = searchState.CheckTransitions();

        assertEquals(FishStateTypes.PursuingFood, nextState);
        assertEquals(EntityTypes.Egg, fish.TargetType, "Starving fish should pursue eggs (TargetType = Egg).");
    }

    @Test
    @DisplayName("SearchState -> LayingEggs: Female fish with high energy transitions to laying eggs state")
    void testSearchStateToLayingEggs() {
        SearchState searchState = new SearchState(fish, FishStateTypes.Searching);

        fish.sex = Sex.Female;
        fish.Energy = Fish.BreedingEnergyMin + 5f;

        FishStateTypes nextState = searchState.CheckTransitions();
        assertEquals(FishStateTypes.LayingEggs, nextState,
                "Well-fed female fish should transition to laying eggs.");
    }

    // ========================================================
    // 2. PURSUE FOOD STATE TESTS
    // ========================================================

    @Test
    @DisplayName("PursueFoodState: Transition to Searching when fish reaches maximum energy")
    void testPursueFoodStateFullEnergy() {
        Fish targetDummy = new Fish();
        fish.Target = targetDummy; // Set a mock target to prevent NullPointerException

        PursueFoodState pursueState = new PursueFoodState(fish, FishStateTypes.PursuingFood);

        // Simulate full energy
        fish.Energy = Fish.MaxEnergy;

        FishStateTypes nextState = pursueState.CheckTransitions();
        assertEquals(FishStateTypes.Searching, nextState,
                "Upon reaching MaxEnergy, the fish should stop pursuing food.");
    }

    // ========================================================
    // 3. FLEE STATE TESTS
    // ========================================================

    @Test
    @DisplayName("FleeState: Fish returns to Searching after flee timeout")
    void testFleeStateTimeout() {
        Fish predator = new Fish();
        // Add a predator to FOV so Enter() executes correctly
        fish.InFOV.AddEntry(predator, 10f);

        FleeState fleeState = new FleeState(fish, FishStateTypes.Fleeing);
        fleeState.Enter();

        // Clear fish's field of view (predator disappeared)
        fish.InFOV.Reset();

        // Call Update() exactly as many times as the timeout limit (10)
        for (int i = 0; i < FleeState.timeUntilStopsFleeing; i++) {
            fleeState.Update();
        }

        FishStateTypes nextState = fleeState.CheckTransitions();
        assertEquals(FishStateTypes.Searching, nextState,
                "Fish should stop fleeing if the predator has disappeared for the specified time.");
    }

    // ========================================================
    // 4. LAY EGGS STATE TESTS
    // ========================================================

    @Test
    @DisplayName("LayEggsState: State immediately returns to Searching after laying an egg")
    void testLayEggsStateFinished() {
        LayEggsState layEggsState = new LayEggsState(fish, FishStateTypes.LayingEggs);

        // Executing Enter() in LayEggsState sets the 'finished' flag to true and deducts energy
        layEggsState.Enter();

        FishStateTypes nextState = layEggsState.CheckTransitions();
        assertEquals(FishStateTypes.Searching, nextState,
                "After laying eggs once, the fish must return to the Searching state.");
    }
}