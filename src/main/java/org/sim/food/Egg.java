package org.sim.food;

import org.sim.RND;
import org.sim.fish.genes.Genes;
import org.sim.Graphics;
import org.sim.fish.genes.Sex;
import org.sim.SimManager;
import org.sim.fish.Fish;
import processing.core.PVector;

public class Egg extends Food {
    public Genes gene;
    public float TimeTilDecay = 500f;
    public float TimeTilHatch;
    public boolean Fertilized;

    public Egg(Graphics graphicsHandle, PVector position, Genes motherGenes, float timeTilHatch, float energy)
    {
        this.graphics_handle = graphicsHandle;
        this.Position = position.copy();
        this.gene = motherGenes.Copy();
        this.TimeTilHatch = timeTilHatch;
        this.Energy = energy;
    }

    @Override
    public void Update()
    {
        if(Fertilized)
        {
            TimeTilHatch -= 1;
            if(TimeTilHatch <= 0)
            {
                Hatch();
                return;
            }
        }

        TimeTilDecay -= 1;
        if(TimeTilDecay <= 0)
        {
            SimManager.EntitiesToAdd.add(new Meat(graphics_handle, Position.copy(), Energy));
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
        SimManager.EntitiesToAdd.add(new Fish(graphics_handle, Position.copy(), gene,
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
