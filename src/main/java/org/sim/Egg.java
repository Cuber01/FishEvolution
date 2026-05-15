package org.sim;

import processing.core.PVector;

public class Egg extends Food {
    public Genes gene;
    public float TimeTilHatch;
    public static final float DefaultEnergy = 20f;

    public Egg(Graphics graphicsHandle, PVector position, Genes motherGenes, float timeTilHatch)
    {
        this.graphics_handle = graphicsHandle;
        this.Position = position;
        this.gene = motherGenes;
        this.TimeTilHatch = timeTilHatch;
        this.Energy = DefaultEnergy;
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
