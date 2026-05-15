package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

import java.util.Map;

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

        // TODO add proper eating and damaging fish
        if(PVector.sub(Target.Position, actor.Position).magSq() < Entity.DistTolerance) {
            actor.Energy += Target.Bite(actor.Attributes.PlantToMeatDigestion);
            Target.Dead = true;
            SimManager.EntitiesToRemove.add(Target);
        } else if (TargetType == EntityTypes.Fish && !actor.InFOV.FishInFOV((Fish) Target)) {
            timeUntilLastSawFood += 1;
        }
    }

    @Override
    public void Draw() {

    }

    @Override
    public void Exit() {
        Target = null;
        TargetType = null;
        timeUntilLastSawFood = 0;
    }

    @Override
    public FishStateTypes CheckTransitions() {
        if (Target.Dead || timeUntilLastSawFood > timeUntilStopsPursuing)
        {
            return FishStateTypes.Searching;
        }
        return FishStateTypes.PursuingFood;
    }

}
