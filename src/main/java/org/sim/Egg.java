package org.sim;

import processing.core.PVector;

public class Egg extends Food {
    public Genes gene;
    public float TimeTilHatch;

    public Egg(Graphics graphicsHandle, PVector position, Genes motherGenes, float timeTilHatch)
    {
        super(graphicsHandle, position, 10f);
        this.Position = position;
        this.gene = motherGenes;
        this.TimeTilHatch = timeTilHatch;
    }

    public void Fertilize(Genes fatherGenes)
    {
        gene = gene.MutateGenes(fatherGenes);
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        Die();
        return Energy;
    }
}
