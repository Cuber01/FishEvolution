package org.example;

public class Genes {
    public final float Speed;
    public final float MaxHP;
    public final float Damage;
    public final float Vision;

    // These values are 0-1
    public final float Aggressiveness;
    public final float PlantToMeatDigestion; // Where 0 = full plant no meat, 1 = full meat no plant

    // Possible additions: Lifespan, Color (teams), Egg amount vs Quality, Armor

    public Genes(float Speed, float MaxHp,
                 float Damage, float Vision,
                 float Aggressiveness, float PlantToMeatDigestion)
    {
        this.Speed = Speed;
        this.MaxHP = MaxHp;
        this.Damage = Damage;
        this.Vision = Vision;
        this.Aggressiveness = Aggressiveness;
        this.PlantToMeatDigestion = PlantToMeatDigestion;
    }

    public Genes MutateGenes(Genes otherGene)
    {
        float newSpeed = mutateAttribute(this.Speed, otherGene.Speed, 10f);
        float newMaxHP = mutateAttribute(this.MaxHP, otherGene.MaxHP, 10f);
        float newDamage = mutateAttribute(this.Damage, otherGene.Damage, 10f);
        float newVision = mutateAttribute(this.Vision, otherGene.Vision, 10f);
        float newAggressiveness = mutateAttribute(this.Aggressiveness, otherGene.Aggressiveness, 1f);
        float newPlantToMeatDigestion = mutateAttribute(this.PlantToMeatDigestion, otherGene.PlantToMeatDigestion, 1f);

        return new Genes(newSpeed, newMaxHP, newDamage, newVision, newAggressiveness, newPlantToMeatDigestion);
    }

    private static float mutateAttribute(float attributeA, float attributeB, float mutationWeight)
    {
        return (attributeA + attributeB) / 2 + ((float)(Math.random() - 0.5f) * mutationWeight);
    }
}
