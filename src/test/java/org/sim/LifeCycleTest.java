package org.sim;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sim.fish.Fish;
import org.sim.fish.genes.Genes;
import org.sim.food.Egg;
import processing.core.PVector;
import static org.junit.jupiter.api.Assertions.*;

class LifeCycleTest {

    @Test
    @DisplayName("Fish should trigger death sequence and mark IsDead when HP reaches or drops below zero")
    void testFishDeathOnZeroHP() {
        // Set up a live fish with 50 HP
        Fish fish = new Fish();
        fish.Attributes = new Genes(2.0f, 100f, 10f, 60f, 0.5f, 0.5f);
        fish.HP = 50f;

        // Trigger a bite inflicting damage greater than current health points (deal 60 damage)
        fish.Bite(0.5f, 60f);

        // Check if flags are set correctly
        assertTrue(fish.IsDead, "The fish should be marked as dead (IsDead = true) after losing all HP!");
    }

    @Test
    @DisplayName("Fertilized egg should countdown hatch time on update tick")
    void testEggHatchingProcess() {
        Genes mockGenes = new Genes(2.0f, 100f, 10f, 60f, 0.5f, 0.5f);
        // Create an egg with hatch time set to 10 ticks
        Egg egg = new Egg(new PVector(100, 100), mockGenes, 10f, 50f);

        // The egg must be fertilized for the countdown to start
        egg.Fertilized = true;

        // Execute one update cycle (tick)
        egg.Update();

        // Verify that the object safely updates its state without throwing exceptions
        assertFalse(egg.IsDead, "The egg should not decay or die after just one valid update tick.");
    }
    @Test
    @DisplayName("Fish energy should decay over time due to metabolic upkeep during update tick")
    void testEnergyDecayOverTime() {
        Fish fish = new Fish();
        fish.Attributes = new Genes(2.0f, 100f, 10f, 60f, 0.5f, 0.5f);
        fish.Energy = 50f;

        // Run a single update tick
        fish.Update();

        // Energy must be lower than starting 50f because living costs energy
        assertTrue(fish.Energy < 50f,
                "Fish energy must decrease after an update tick due to metabolic upkeep!");
    }

    @Test
    @DisplayName("Fish should die of starvation when energy reaches zero")
    void testStarvationDeath() {
        Fish fish = new Fish();
        fish.Attributes = new Genes(2.0f, 100f, 10f, 60f, 0.5f, 0.5f);

        // Force energy to zero to simulate extreme starvation
        fish.Energy = 0f;

        // Update should trigger the starvation check
        fish.Update();

        assertTrue(fish.IsDead, "Fish should be marked as dead (IsDead = true) when energy reaches zero!");
    }
}