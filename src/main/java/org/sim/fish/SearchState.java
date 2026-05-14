package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

class SearchState extends State<Fish, FishStateTypes> {
    private PVector followPoint;

    public SearchState(Fish actor, FishStateTypes type) {
        super(actor, type);
    }

    @Override
    public void Enter() {
        rerollPoint();
    }

    private void rerollPoint() {
        followPoint = RND.RandomVector2(0, SimManager.CanvasX, 0,SimManager.CanvasY);
    }

    @Override
    public void Update() {
        if (PVector.dist(actor.Position, followPoint) < Entity.DistTolerance) {
            rerollPoint();
        } else {
            actor.Velocity = ( followPoint.sub(actor.Position)
                                           .normalize()
                                           .mult(actor.Attributes.Speed)
            );
            actor.Position.add(actor.Velocity);
        }
    }

    @Override
    public void Draw() {
        actor.graphics_handle.draw_fish(actor.Position);
    }

    @Override
    public void Exit() {

    }

    @Override
    public FishStateTypes CheckTransitions() {
        if( (actor.InFOV.SawFood && actor.Energy < Fish.BreedingEnergyMin))
        {
            actor.PursueFoodState.TargetType = EntityTypes.Food;
            return FishStateTypes.PursuingFood;
        } else if ((actor.InFOV.SawEggs && actor.Energy <= Fish.StarvingEnergyMin))
        {
            actor.PursueFoodState.TargetType = EntityTypes.Egg;
            return FishStateTypes.PursuingFood;
        } else if ((actor.InFOV.SawFish && RND.Chance(actor.Attributes.Aggressiveness
                                                      + actor.Attributes.PlantToMeatDigestion,
                                            Fish.MaxEnergy-actor.Energy)))
        {
            actor.PursueFoodState.TargetType = EntityTypes.Fish;
            return FishStateTypes.PursuingFood;
        }


        if (actor.InFOV.SawFish && RND.Chance(
                 actor.HP / actor.Attributes.MaxHP,
                actor.Attributes.Aggressiveness+
                             actor.Attributes.Damage)
                )
        {
            return FishStateTypes.Fleeing;
        }

        if ( actor.Gender == Sex.Male
             && actor.InFOV.SawEggs
             && actor.Energy >= Fish.BreedingEnergyMin)
        {
            return FishStateTypes.FertilizingEggs;
        }

        if ( actor.Gender == Sex.Female
             && actor.Energy >= Fish.BreedingEnergyMin)
        {
            return FishStateTypes.LayingEggs;
        }

        return FishStateTypes.Searching;
    }

}
