package org.sim;

import processing.core.PVector;

public class Egg extends Food {
    public Genes gene;
    public float TimeTilHatch;

    public Egg(PVector position, Genes motherGenes, float timeTilHatch)
    {
        this.Position = position;
        this.gene = motherGenes;
        this.TimeTilHatch = timeTilHatch;
    }

    public void Fertilize(Genes fatherGenes)
    {
        gene = gene.MutateGenes(fatherGenes);
    }

}
