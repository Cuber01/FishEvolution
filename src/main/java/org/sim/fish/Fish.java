package org.sim.fish;

import org.sim.*;
import processing.core.PVector;


public class Fish extends Entity {
    public Genes Attributes;
    public FOVInfo InFOV = new FOVInfo();
    public PVector Velocity = new PVector();

    // TODO Make this private
    public final PursueFoodState PursueFoodState = new PursueFoodState(this, FishStateTypes.PursuingFood);
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
    public float Energy = 50f;

    public Fish(Graphics graphicsHandle, PVector position, Genes genes, Sex sex)
    {
        this.Position = position;
        this.graphics_handle = graphicsHandle;
        this.Attributes = genes;
        this.sex = sex;
        this.HP = genes.MaxHP;
        this.Energy = MaxEnergy;
        CurrentState.Enter();
    }

    public Fish(Graphics graphicsHandle)
    {
        this.graphics_handle = graphicsHandle;
        this.Position = RND.RandomVector2(0, SimManager.CanvasX, 0, SimManager.CanvasY);
        this.Attributes = new Genes();
        this.sex = Math.random() > 0.5 ? Sex.Male : Sex.Female;
        this.HP = Attributes.MaxHP;
        this.Energy = MaxEnergy;
        CurrentState.Enter();
    }

    @Override
    public void Update(Biome currentBiome)
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
        Energy -= Attributes.GetGeneticEnergyUpkeep();
        if(Energy < 0)
        {
            Die();
        }

        if(HP < Attributes.MaxHP && Energy > StarvingEnergyMin)
        {
            HP += 0.1f;
            Energy -= 1f;
        }
    }

    public void CalculateFOV(List<Entity> entities)
    {
        InFOV.Reset();

        for(Entity entity : entities)
        {
            float squaredDist = PVector.sub(entity.Position, this.Position).magSq();
            if(entity != this && squaredDist < Attributes.VisionRange*Attributes.VisionRange)
            {
                InFOV.AddEntry(entity, squaredDist);
            }
        }
    }

    @Override
    public float Bite(float plantToMeatDigestion, float damage)
    {
        HP -= damage;
        if (HP <= 0)
        {
            Die();
        }

        float energyLost = Energy * Math.min( damage / Attributes.MaxHP, 0.8f);
        Energy -= energyLost;

        return plantToMeatDigestion * energyLost;
    }

    @Override
    public void Die()
    {
        super.Die();
        SimManager.EntitiesToAdd.add(new Meat(graphics_handle, Position, Energy));
    }

    @Override
    public void Draw()
    {
        graphics_handle.draw_fish(Position);
        //currentState.Draw();
    }


}
