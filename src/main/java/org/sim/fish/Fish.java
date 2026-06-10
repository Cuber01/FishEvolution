package org.sim.fish;

import org.sim.*;
import org.sim.fish.genes.Genes;
import org.sim.fish.genes.Sex;
import org.sim.food.Meat;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents the main living actor of the simulation.
 * It moves around, looks for targets via FOV, processes its AI state machine, and reproduces.
 */
public class Fish extends Entity implements IProfilable {
    public Genes Attributes;
    public FOVInfo InFOV = new FOVInfo();
    public PVector Velocity = new PVector();

    private final PursueFoodState PursueFoodState = new PursueFoodState(this, FishStateTypes.PursuingFood);
    private final FertilizeEggsState FertilizeEggs = new FertilizeEggsState(this, FishStateTypes.FertilizingEggs);
    private final LayEggsState LayEggs = new LayEggsState(this, FishStateTypes.LayingEggs);
    private final FleeState Flee = new FleeState(this, FishStateTypes.Fleeing);
    private final SearchState Search = new SearchState(this, FishStateTypes.Searching);
    public State<Fish, FishStateTypes> CurrentState = Search;

    public static final float StarvingEnergyMin = 20f; // Will eat eggs
    public static final float BreedingEnergyMin = 80f; // Will breed
    public static final float MaxEnergy = 100f;
    public static final float ReproductionEnergyUse = 50f;

    public Sex sex;
    public float HP;

    public EntityTypes TargetType;
    public Entity Target;

    public Fish(PVector position, Genes genes, Sex sex)
    {
        this.Position = position;
        this.Attributes = genes;
        this.sex = sex;
        this.HP = genes.MaxHP();
        this.Energy = ReproductionEnergyUse;
        CurrentState.Enter();
    }

    public Fish()
    {
        this.Position = RND.RandomVector2(0, SimManager.CanvasX, 0, SimManager.CanvasY);
        this.Attributes = new Genes();
        this.sex = RND.RandomBoolean() ? Sex.Male : Sex.Female;
        this.HP = Attributes.MaxHP();
        this.Energy = MaxEnergy;
        CurrentState.Enter();
    }
    /**
     * Updates the internal life logic of the fish on every simulation tick.
     * It updates the state, subtracts continuous genetic energy upkeep, checks for starvation,
     * regenerates health points (HP) if the fish has enough energy,
     * and triggers the death sequence if energy drops below zero.
     */
    @Override
    public void Update()
    {
        if(IsDead) return;

        CurrentState.Update();
        FishStateTypes newState = CurrentState.CheckTransitions();
        if (newState != CurrentState.AssociatedType) {
            CurrentState.Exit();
            switch (newState) {
                case Searching:
                    CurrentState = Search;
                    break;

                case PursuingFood:
                    CurrentState = PursueFoodState;
                    break;

                case FertilizingEggs:
                    CurrentState = FertilizeEggs;
                    break;

                case LayingEggs:
                    CurrentState = LayEggs;
                    break;

                case Fleeing:
                    CurrentState = Flee;
                    break;
            }
            CurrentState.Enter();
        }

        handleEnergy();
    }

    private void handleEnergy()
    {
        // Energy use
        Energy -= Attributes.GetGeneticEnergyUpkeep();
        if(Energy < 0)
        {
            Die();
        }

        // Regeneration
        if(HP < Attributes.MaxHP() && Energy > StarvingEnergyMin)
        {
            HP += 0.1f;
            Energy -= 1f;
        }
    }

    /**
     * Scans the world list to find which objects are close enough to be seen by this fish.
     */
    public void CalculateFOV(ArrayList<Entity> entities)
    {
        InFOV.Reset();

        for(Entity entity : entities)
        {
            float squaredDist = PVector.sub(entity.Position, this.Position).magSq();
            if(entity != this && squaredDist < Attributes.VisionRange() * Attributes.VisionRange())
            {
                InFOV.AddEntry(entity, squaredDist);
            }
        }
    }

    /**
     * Lowers the health of this fish when it gets bitten, and returns digested energy to the attacker.
     */
    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        HP -= damage;
        if (HP <= 0)
        {
            Die();
        }

        float energyLost = Energy * Math.min( damage / Attributes.MaxHP(), 0.8f);
        Energy -= energyLost;

        return plantToMeatDigestion * energyLost;
    }

    /**
     * Handles the fish death sequence by removing it and spawning a piece of meat in its place.
     */
    @Override
    public void Die()
    {
        super.Die();
        SimManager.SpawnEntity(new Meat(Position, Energy));
    }

    /**
     * Provides a list of text strings containing current fish status for the IProfile interface.
     */
    @Override
    public List<String> GetProfile() {
        List<String> info = new ArrayList<>();
        info.add("Sex: " + sex);
        info.add("HP: " + HP);
        info.add("Energy: " + Energy);
        info.add("State: " + CurrentState.AssociatedType);
        return info;
    }
}
