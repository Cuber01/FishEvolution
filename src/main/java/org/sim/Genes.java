package org.sim;

public class Genes {
    public final float Speed;
    public final float MaxHP;
    public final float Damage;
    public final float VisionRange;

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
        this.VisionRange = Vision;
        this.Aggressiveness = Aggressiveness;
        this.PlantToMeatDigestion = PlantToMeatDigestion;
    }

    public Genes()
    {
        this.Speed = RND.RandomFloat(0.5f, 5f);
        this.MaxHP = RND.RandomFloat(0.5f, 5f);
        this.Damage = RND.RandomFloat(0.5f, 2.5f);
        this.VisionRange = RND.RandomFloat(10f, 50f);
        this.Aggressiveness = RND.RandomFloat(0.0f, 1.0f);
        this.PlantToMeatDigestion = RND.RandomFloat(0.0f, 1.0f);;
    }

    public Genes MutateGenes(Genes otherGene)
    {
        float newSpeed = mutateAttribute(this.Speed, otherGene.Speed, 5f);
        float newMaxHP = mutateAttribute(this.MaxHP, otherGene.MaxHP, 5f);
        float newDamage = mutateAttribute(this.Damage, otherGene.Damage, 5f);
        float newVision = mutateAttribute(this.VisionRange, otherGene.VisionRange, 5f);
        float newAggressiveness = mutateAttribute(this.Aggressiveness, otherGene.Aggressiveness, 0.1f);
        float newPlantToMeatDigestion = mutateAttribute(this.PlantToMeatDigestion, otherGene.PlantToMeatDigestion, 0.1f);

        return new Genes(newSpeed, newMaxHP, newDamage, newVision, newAggressiveness, newPlantToMeatDigestion);
    }

    private static float mutateAttribute(float attributeA, float attributeB, float mutationWeight)
    {
        return (attributeA + attributeB) / 2 + ((float)(Math.random() - 0.5f) * mutationWeight);
    }
}
