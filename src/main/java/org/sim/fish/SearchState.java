package org.sim.fish;

import org.sim.RND;
import org.sim.Sex;
import org.sim.SimManager;
import org.sim.State;
import processing.core.PVector;

import java.lang.reflect.Type;

class SearchState extends State<Fish, FishStateTypes> {
    private PVector followPoint;
    private static final float distTolerance = 1f;


    public SearchState(Fish actor) {
        super(actor);
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
        if (PVector.dist(actor.position, followPoint) < distTolerance) {
            rerollPoint();
        } else {
            actor.position.add( followPoint.sub(actor.position)
                                           .normalize()
                                           .mult(actor.Attributes.Speed)
            );
        }
    }

    @Override
    public void Draw() {

    }

    @Override
    public void Exit() {

    }

    @Override
    public FishStateTypes CheckTransitions() {
        if( (actor.InFOV.SawFood && actor.Energy < Fish.BreedingEnergyMin) ||
            (actor.InFOV.SawEggs && actor.Energy <= Fish.StarvingEnergyMin ))
        {
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
