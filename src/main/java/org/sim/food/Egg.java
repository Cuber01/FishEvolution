package org.sim.food;

import org.sim.Entity;
import org.sim.RND;
import org.sim.fish.genes.Genes;
import org.sim.fish.genes.Sex;
import org.sim.SimManager;
import org.sim.fish.Fish;
import processing.core.PVector;

public class Egg extends Entity {
    public Genes gene;
    protected float TimeTilDecay = 500f;
    private float timeTilHatch;
    public boolean Fertilized;

    public Egg(PVector position, Genes motherGenes, float timeTilHatch, float energy)
    {
        this.Position = position.copy();
        this.gene = motherGenes.Copy();
        this.timeTilHatch = timeTilHatch;
        this.Energy = energy;
    }

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

    public void Fertilize(Genes fatherGenes)
    {
        if(Fertilized) return;

        gene = gene.MutateGenes(fatherGenes);
        Fertilized = true;
    }

    public void Hatch()
    {
        SimManager.SpawnEntity(new Fish(Position.copy(), gene,
                RND.RandomBoolean() ? Sex.Male : Sex.Female));
        Die();
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        if(IsDead) return 0;

        Die();
        return Energy;
    }
}
