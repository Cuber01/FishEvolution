package org.sim.fish.genes;

import org.sim.Consts;
import org.sim.IProfilable;
import org.sim.RND;

import java.util.ArrayList;
import java.util.List;

/**
 * @param Aggressiveness       These values are 0-1
 * @param PlantToMeatDigestion Where 0 = full plant no meat, 1 = full meat no plant
 */
public record Genes(float Speed, float MaxHP, float Damage, float VisionRange, float Aggressiveness,
                    float PlantToMeatDigestion) implements IProfilable {
    // Possible additions: Lifespan, Color (teams), Egg amount vs Hatch time, Armor, MaxEnergy, Energy at birth

    public Genes Copy() {
        return new Genes(this.Speed, this.MaxHP, this.Damage, this.VisionRange, this.Aggressiveness, this.PlantToMeatDigestion);
    }

    public Genes() {
        this(RND.RandomFloat(Consts.GENE_SPEED_MIN, Consts.GENE_SPEED_MAX), RND.RandomFloat(Consts.GENE_MAX_HP_MIN, Consts.GENE_MAX_HP_MAX), RND.RandomFloat(Consts.GENE_DAMAGE_MIN, Consts.GENE_DAMAGE_MAX), RND.RandomFloat(Consts.GENE_VISION_RANGE_MIN, Consts.GENE_VISION_RANGE_MAX), RND.RandomFloat(Consts.GENE_AGGRESSIVENESS_MIN, Consts.GENE_AGGRESSIVENESS_MAX), RND.RandomFloat(Consts.GENE_PLANT_TO_MEAT_DIGESTION_MIN, Consts.GENE_PLANT_TO_MEAT_DIGESTION_MAX));
    }

    public Genes MutateGenes(Genes otherGene) {
        float newSpeed = mutateAttribute(this.Speed, otherGene.Speed, Consts.SPEED_MUTATION_WEIGHT);
        float newMaxHP = mutateAttribute(this.MaxHP, otherGene.MaxHP, Consts.MAX_HP_MUTATION_WEIGHT);
        float newDamage = mutateAttribute(this.Damage, otherGene.Damage, Consts.DAMAGE_MUTATION_WEIGHT);
        float newVision = mutateAttribute(this.VisionRange, otherGene.VisionRange, Consts.VISION_RANGE_MUTATION_WEIGHT);
        float newAggressiveness = mutateAttribute(this.Aggressiveness, otherGene.Aggressiveness, Consts.AGGRESSIVENESS_MUTATION_WEIGHT);
        float newPlantToMeatDigestion = mutateAttribute(this.PlantToMeatDigestion, otherGene.PlantToMeatDigestion, Consts.PLANT_TO_MEAT_DIGESTION_MUTATION_WEIGHT);

        return new Genes(newSpeed, newMaxHP, newDamage, newVision, newAggressiveness, newPlantToMeatDigestion);
    }

    public float GetGeneticEnergyUpkeep() {
        float sum = 0;
        sum += Speed * Consts.SPEED_COST_MODIFIER;
        sum += VisionRange * Consts.VISION_RANGE_COST_MODIFIER;
        sum += MaxHP * Consts.MAX_HP_COST_MODIFIER;
        sum += Damage * Consts.DAMAGE_COST_MODIFIER;
        return sum * Consts.GLOBAL_COST_MODIFIER;
    }

    private static float mutateAttribute(float attributeA, float attributeB, float mutationWeight) {
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
