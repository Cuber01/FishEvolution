package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

import java.util.Map;

import static org.sim.fish.EntityTypes.Egg;
import static org.sim.fish.EntityTypes.Food;
import static processing.core.PApplet.sq;

class PursueFoodState extends State<Fish, FishStateTypes> {
    private final static int timeUntilStopsPursuing = 10;
    private int timeUntilLastSawFood = 0;
    private boolean ate = false;

    public PursueFoodState(Fish actor, FishStateTypes type) {
        super(actor, type);
    }

    @Override
    public void Enter() {
        switch (actor.TargetType) {
            case Fish:
                findClosestInMap(actor.InFOV.FishDist, EntityTypes.Fish);
                break;

            case Egg:
                findClosestInMap(actor.InFOV.EggsDist, Egg);
                break;

            case Food:
                findClosestInMap(actor.InFOV.FoodsDist, Food);
                break;
        }
    }

    private void findClosestInMap(Map<?, Float> map, EntityTypes type) {
        float bestDist = Float.POSITIVE_INFINITY;

        for (Map.Entry<?, Float> entry : map.entrySet()) {
            Entity ent = (Entity) entry.getKey();
            Float d = entry.getValue();
            if (d < bestDist) {
                bestDist = d;
                actor.Target = ent;
                actor.TargetType = type;
            }
        }
    }

    @Override
    public void Update() {
        actor.Velocity = PVector.sub(actor.Target.Position, actor.Position).normalize().mult(actor.Attributes.Speed());
        actor.Position.add(actor.Velocity);

        if (actor.TargetType == EntityTypes.Fish) {
            if(!actor.InFOV.FishInFOV((Fish) actor.Target))
            {
                timeUntilLastSawFood += 1;
            } else if (reachedFood())
            {
                resolveFishEncounter();
                ate = true;
            }
        } else if (reachedFood())
        {
            actor.Energy += actor.Target.Bite(actor.Attributes.PlantToMeatDigestion(), actor.Attributes.Damage());
            ate = true;
        }
    }

    private void resolveFishEncounter()
    {
        Fish prey = (Fish)actor.Target;

        // TODO it would be nice if fish reacted to being attacked with fight or flee behavior
        if(prey.Target == actor)
        {
            // Combat

            float myRatio = actor.Attributes.Damage() / prey.HP;
            float enemyRatio = prey.Attributes.Damage() / actor.HP;
            if(myRatio > enemyRatio)
            {
                actor.Energy += actor.Target.Bite(actor.Attributes.PlantToMeatDigestion(), prey.HP);
            } else {
                prey.Energy += actor.Bite(actor.Attributes.PlantToMeatDigestion(), actor.HP);
            }

        } else if (prey.CurrentState.AssociatedType == FishStateTypes.Fleeing)
        {
            // Chase
            actor.Energy += actor.Target.Bite(actor.Attributes.PlantToMeatDigestion(), actor.Attributes.Damage() /2);
        } else {
            // Harassment
            actor.Energy += actor.Target.Bite(actor.Attributes.PlantToMeatDigestion(), actor.Attributes.Damage());
        }
    }

    private boolean reachedFood() {
        return PVector.sub(actor.Target.Position, actor.Position).magSq() < sq(Entity.DistTolerance * actor.Attributes.Speed());
    }



    @Override
    public void Exit() {
        actor.Target = null;
        actor.TargetType = null;
        timeUntilLastSawFood = 0;
        ate = false;
    }

    @Override
    public FishStateTypes CheckTransitions() {
        if (actor.Target.IsDead || timeUntilLastSawFood > timeUntilStopsPursuing || actor.Energy >= Fish.MaxEnergy || ate)
        {
            return FishStateTypes.Searching;
        }
        return FishStateTypes.PursuingFood;
    }

}
