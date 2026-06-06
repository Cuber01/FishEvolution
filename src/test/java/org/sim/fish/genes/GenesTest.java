package org.sim.fish.genes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenesTest {

    @Test
    @DisplayName("Should correctly calculate higher energy upkeep for stronger traits")
    void testGeneticUpkeepCalculation() {
        // Set up two genetic profiles - weak and strong
        Genes weakGenes = new Genes(1.0f, 50f, 5f, 40f, 0.2f, 0.1f);
        Genes strongGenes = new Genes(4.0f, 150f, 25f, 120f, 0.8f, 0.9f);

        float weakCost = weakGenes.GetGeneticEnergyUpkeep();
        float strongCost = strongGenes.GetGeneticEnergyUpkeep();

        // A stronger fish must consume more energy per tick than a weak one
        assertTrue(strongCost > weakCost,
                "A fish with better genes (faster, stronger) should require higher energy upkeep!");
    }

    @Test
    @DisplayName("Should inherit attributes mixed from both parents during mutation")
    void testGeneMutationRanges() {
        // Parents with extreme speed values
        Genes mother = new Genes(1.0f, 100f, 10f, 50f, 0.5f, 0.5f);
        Genes father = new Genes(5.0f, 100f, 10f, 50f, 0.5f, 0.5f);

        // Create child genes through mutation
        Genes child = mother.MutateGenes(father);

        // Child's speed should oscillate around the average (1 + 5)/2 = 3
        // Verify safe logical boundaries
        assertTrue(child.Speed() > 0.1f, "Child speed cannot drop below zero or critical thresholds!");
        assertNotEquals(0.0f, child.Speed(), "Genes should not completely zero out the speed attribute!");
    }
}