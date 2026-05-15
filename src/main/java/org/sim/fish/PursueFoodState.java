package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

import java.util.Map;

import static processing.core.PApplet.sq;

class PursueFoodState extends State<Fish, FishStateTypes> {
    public final static int timeUntilStopsPursuing = 10;
    private int timeUntilLastSawFood = 0;

    public EntityTypes TargetType;
    public Entity Target;

    public PursueFoodState(Fish actor, FishStateTypes type) {
        super(actor, type);
    }

    @Override
    public void Enter() {
        switch (TargetType) {
            case Fish:
                findClosestInMap(actor.InFOV.FishDist, EntityTypes.Fish);
                break;

            case Egg:
                findClosestInMap(actor.InFOV.EggsDist, EntityTypes.Egg);
                break;

            case Food:
                findClosestInMap(actor.InFOV.FoodsDist, EntityTypes.Food);
                break;
        }
    }

    private void findClosestInMap(Map<?, Float> map, EntityTypes type) {
        float bestDist = Float.POSITIVE_INFINITY;

        for (Object obj : map.keySet()) {
            Entity ent = (Entity) obj;
            Float d = map.get(ent);
            if (d < bestDist) {
                bestDist = d;
                Target = ent;
                TargetType = type;
            }
        }
    }

    @Override
    public void Update() {
        actor.Velocity = PVector.sub(Target.Position, actor.Position).normalize().mult(actor.Attributes.Speed);
        actor.Position.add(actor.Velocity);

        if (TargetType == EntityTypes.Fish) {
            if(!actor.InFOV.FishInFOV((Fish) Target))
            {
                timeUntilLastSawFood += 1;
            } else if (reachedFood())
            {
                resolveFishEncounter();
            }
        } else if (reachedFood())
        {
            actor.Energy += Target.Bite(actor.Attributes.PlantToMeatDigestion, actor.Attributes.Damage);
        }
    }

    private void resolveFishEncounter()
    {
        Fish prey = (Fish)Target;

        // TODO it would be nice if fish reacted to being attacked with fight or flee behavior
        if(prey.CurrentState instanceof PursueFoodState p && p.Target == actor)
        {
            // Combat

            float myRatio = actor.Attributes.Damage / prey.HP;
            float enemyRatio = prey.Attributes.Damage / actor.HP;
            if(myRatio > enemyRatio)
            {
                // TODO apply some damage to victor?
                actor.Energy += Target.Bite(actor.Attributes.PlantToMeatDigestion, prey.HP);
            } else {
                prey.Energy += actor.Bite(actor.Attributes.PlantToMeatDigestion, actor.HP);
            }

        } else if (prey.CurrentState.AssociatedType == FishStateTypes.Fleeing)
        {
            // Chase
            actor.Energy += Target.Bite(actor.Attributes.PlantToMeatDigestion, actor.Attributes.Damage/2);
        } else {
            // Harassment
            actor.Energy += Target.Bite(actor.Attributes.PlantToMeatDigestion, actor.Attributes.Damage);
        }
    }

    private boolean reachedFood() {
        return PVector.sub(Target.Position, actor.Position).magSq() < sq(Entity.DistTolerance * actor.Attributes.Speed);
    }



    @Override
    public void Exit() {
        Target = null;
        TargetType = null;
        timeUntilLastSawFood = 0;
    }

    @Override
    public FishStateTypes CheckTransitions() {
        if (Target.IsDead || timeUntilLastSawFood > timeUntilStopsPursuing || actor.Energy >= Fish.MaxEnergy)
        {
            return FishStateTypes.Searching;
        }
        return FishStateTypes.PursuingFood;
    }

}
