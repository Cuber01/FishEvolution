package org.sim.food;

import org.sim.Entity;
import org.sim.RND;
import org.sim.fish.genes.Genes;
import org.sim.fish.genes.Sex;
import org.sim.SimManager;
import org.sim.fish.Fish;
import processing.core.PVector;
/**
 * Represents a fish egg that can be fertilized by males and eventually hatches into a new fish.
 */
public class Egg extends Entity {
    public Genes gene;
    protected float TimeTilDecay = 500f;
    private float timeTilHatch;
    public boolean Fertilized;

    /**
     * Creates a new egg containing the mother's DNA.
     */
    public Egg(PVector position, Genes motherGenes, float timeTilHatch, float energy)
    {
        this.Position = position.copy();
        this.gene = motherGenes.Copy();
        this.timeTilHatch = timeTilHatch;
        this.Energy = energy;
    }

    /**
     * Manages the egg timer logic, counting down to hatching or organic decay.
     */
    @Override
    public void Update()
    {
        if(Fertilized)
        {
            timeTilHatch -= 1;
            if(timeTilHatch <= 0)
            {
                Hatch();
                return;
            }
        }

        TimeTilDecay -= 1;
        if(TimeTilDecay <= 0)
        {
            SimManager.SpawnEntity(new Meat(Position.copy(), Energy));
            Die();
        }

    }

    /**
     * Mixes mother's genes with the father's genes, applies mutations, and activates the hatching process.
     * Called by father fish.
     */
    public void Fertilize(Genes fatherGenes)
    {
        if(Fertilized) return;

        gene = gene.MutateGenes(fatherGenes);
        Fertilized = true;
    }

    /**
     * Spawns a new fish into the simulation world using the finalized genetic combination.
     */
    public void Hatch()
    {
        SimManager.SpawnEntity(new Fish(Position.copy(), gene,
                RND.RandomBoolean() ? Sex.Male : Sex.Female));
        Die();
    }

    /**
     * Handles the event when a starving fish bites or eats this egg.
     * It instantly kills the egg and transfers its stored energy to the attacking fish.
     * @param plantToMeatDigestion The digestion efficiency coefficient of the attacker.
     * @param damage The amount of damage dealt to the egg.
     * @return The amount of energy converted and returned to the attacker.
     */
    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        if(IsDead) return 0;

        Die();
        return Energy;
    }
}
