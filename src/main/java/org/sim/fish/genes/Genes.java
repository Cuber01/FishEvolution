package org.sim.fish.genes;

import org.sim.Consts;
import org.sim.IProfilable;
import org.sim.RND;

import java.util.ArrayList;
import java.util.List;

public class Genes implements IProfilable {
    public final float Speed;
    public final float MaxHP;
    public final float Damage;
    public final float VisionRange;

    // These values are 0-1
    public final float Aggressiveness;
    public final float PlantToMeatDigestion; // Where 0 = full plant no meat, 1 = full meat no plant

    // Possible additions: Lifespan, Color (teams), Egg amount vs Hatch time, Armor, MaxEnergy, Energy at birth

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
        this.EggEnergy = EggEnergy;
        this.EggChildren = EggChildren;
    }

    public Genes Copy()
    {
        return new Genes(this.Speed, this.MaxHP, this.Damage, this.VisionRange, this.Aggressiveness, this.PlantToMeatDigestion, this.EggEnergy, this.EggChildren);
    }

    public Genes()
    {
        this.EggEnergy = RND.RandomFloat(Consts.GENE_EGG_ENERGY_MIN, Consts.GENE_EGG_ENERGY_MAX);
        this.EggChildren = RND.RandomFloat(Consts.GENE_EGG_CHILDREN_MIN, Consts.GENE_EGG_CHILDREN_MAX);
        this.Speed = RND.RandomFloat(Consts.GENE_SPEED_MIN, Consts.GENE_SPEED_MAX);
        this.MaxHP = RND.RandomFloat(Consts.GENE_MAX_HP_MIN, Consts.GENE_MAX_HP_MAX);
        this.Damage = RND.RandomFloat(Consts.GENE_DAMAGE_MIN, Consts.GENE_DAMAGE_MAX);
        this.VisionRange = RND.RandomFloat(Consts.GENE_VISION_RANGE_MIN, Consts.GENE_VISION_RANGE_MAX);
        this.Aggressiveness = RND.RandomFloat(Consts.GENE_AGGRESSIVENESS_MIN, Consts.GENE_AGGRESSIVENESS_MAX);
        this.PlantToMeatDigestion = RND.RandomFloat(Consts.GENE_PLANT_TO_MEAT_DIGESTION_MIN, Consts.GENE_PLANT_TO_MEAT_DIGESTION_MAX);
    }

    public Genes MutateGenes(Genes otherGene)
    {
        float newSpeed = mutateAttribute(this.Speed, otherGene.Speed, Consts.SPEED_MUTATION_WEIGHT);
        float newMaxHP = mutateAttribute(this.MaxHP, otherGene.MaxHP, Consts.MAX_HP_MUTATION_WEIGHT);
        float newDamage = mutateAttribute(this.Damage, otherGene.Damage, Consts.DAMAGE_MUTATION_WEIGHT);
        float newVision = mutateAttribute(this.VisionRange, otherGene.VisionRange, Consts.VISION_RANGE_MUTATION_WEIGHT);
        float newAggressiveness = mutateAttribute(this.Aggressiveness, otherGene.Aggressiveness, Consts.AGGRESSIVENESS_MUTATION_WEIGHT);
        float newPlantToMeatDigestion = mutateAttribute(this.PlantToMeatDigestion, otherGene.PlantToMeatDigestion, Consts.PLANT_TO_MEAT_DIGESTION_MUTATION_WEIGHT);
        float newEggEnergy = mutateAttribute(this.EggEnergy, otherGene.EggEnergy, Consts.EGG_ENERGY_MUTATION_WEIGHT);
        float newEggChildren = mutateAttribute(this.EggChildren, otherGene.EggChildren, Consts.EGG_CHILDREN_MUTATION_WEIGHT);

        return new Genes(newSpeed, newMaxHP, newDamage, newVision, newAggressiveness, newPlantToMeatDigestion, newEggEnergy, newEggChildren);
    }

    public float GetGeneticEnergyUpkeep()
    {
        float sum = 0;
        sum += Speed * Consts.SPEED_COST_MODIFIER;
        sum += VisionRange * Consts.VISION_RANGE_COST_MODIFIER;
        sum += MaxHP * Consts.MAX_HP_COST_MODIFIER;
        sum += Damage * Consts.DAMAGE_COST_MODIFIER;
        return sum * Consts.GLOBAL_COST_MODIFIER;
    }

    private static float mutateAttribute(float attributeA, float attributeB, float mutationWeight)
    {
        return (attributeA + attributeB) / 2 + RND.RandomFloat(-mutationWeight, mutationWeight);
    }

    @Override
    public List<String> GetProfile() {
        List<String> info = new ArrayList<>();
        info.add("Speed: " + String.format("%.2f", Speed));
        info.add("MaxHP: " + String.format("%.2f", MaxHP));
        info.add("Damage: " + String.format("%.2f", Damage));
        info.add("Vision: " + String.format("%.2f", VisionRange));
        info.add("Aggressiveness: " + String.format("%.2f", Aggressiveness));
        info.add("PlantToMeatDigestion: " + String.format("%.2f", PlantToMeatDigestion));
        return info;
    }
}
