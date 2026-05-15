package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

import java.util.List;

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
    private State<Fish, FishStateTypes> currentState = Search;

    public static final float StarvingEnergyMin = 10f; // Will eat eggs
    public static final float BreedingEnergyMin = 80f; // Will breed
    public static final float MaxEnergy = 100f;

    public Sex sex;
    public float HP;
    public float Energy = 50f;

    public Fish(PVector position, Graphics graphicsHandle, Genes genes, Sex sex)
    {
        this.Position = position;
        this.graphics_handle = graphicsHandle;
        this.Attributes = genes;
        this.sex = sex;
        this.HP = genes.MaxHP;
        currentState.Enter();
    }

    @Override
    public void Update(Biome currentBiome)
    {
        currentState.Update();

        FishStateTypes newState = currentState.CheckTransitions();
        if (newState != currentState.AssociatedType) {
            currentState.Exit();
            switch (newState) {
                case Searching:
                    currentState = Search;
                    break;

                case PursuingFood:
                    currentState = PursueFoodState;
                    break;

                case FertilizingEggs:
                    currentState = FertilizeEggs;
                    break;

                case LayingEggs:
                    currentState = LayEggs;
                    break;

                case Fleeing:
                    currentState = Flee;
                    break;
            }
            currentState.Enter();
        }

    }

    public void CalculateFOV(List<Entity> entities)
    {
        InFOV.Reset();

        for(Entity entity : entities)
        {
            // TODO: use just dist?
            if(entity != this &&
               entity.Position.dist(this.Position) < Attributes.VisionRange)
            {
                InFOV.AddEntry(entity, this.Position, entity.Position);
            }
        }
    }

    @Override
    public void Draw()
    {
        graphics_handle.draw_fish(Position);
        //currentState.Draw();
    }


}
