package org.sim.fish;

import org.sim.Egg;
import org.sim.Entity;
import org.sim.State;
import processing.core.PVector;

import java.util.Map;

import static processing.core.PApplet.sq;

class FertilizeEggsState extends State<Fish, FishStateTypes> {
    private boolean finished = false;
    private Egg target;

    public FertilizeEggsState(Fish actor, FishStateTypes type) {
        super(actor, type);
    }

    @Override
    public void Enter() {
        Map<Egg, Float> map = actor.InFOV.EggsDist;
        float bestDist = Float.POSITIVE_INFINITY;

        for (Egg egg : map.keySet()) {
            Float d = map.get(egg);
            if (d < bestDist) {
                bestDist = d;
                target = egg;
            }
        }
    }

    @Override
    public void Update() {
        actor.Velocity = target.Position.sub(actor.Position).normalize().mult(actor.Attributes.Speed);
        actor.Position.add(actor.Velocity);

        if(PVector.sub(target.Position, actor.Position).magSq() < sq(Entity.DistTolerance * actor.Attributes.Speed)) {
            target.Fertilize(actor.Attributes);
            finished = true;
        }
    }

    @Override
    public void Exit() {
        target = null;
        finished = false;
    }

    @Override
    public FishStateTypes CheckTransitions() {
        if(finished)
        {
            return FishStateTypes.Searching;
        }
        return FishStateTypes.LayingEggs;
    }

}