package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

import java.util.Map;

class FleeState extends State<Fish, FishStateTypes> {
    public static int timeUntilStopsFleeing = 10;
    private int timeUntilLastSawPredator = 0;
    private PVector fleeDirection;
    private Fish predator = null;

    public FleeState(Fish actor, FishStateTypes type) {
        super(actor, type);
    }

    @Override
    public void Enter() {

        for(Map.Entry<Fish, Float> entry : actor.InFOV.FishDist.entrySet())
        {
            if(entry.getKey().Target == actor)
            {
                predator = entry.getKey();
                break;
            }
        }

        // If a real predator was not found, our fish got scared for no reason and flees from a random fish
        if(predator == null)
        {
            predator = actor.InFOV.FishDist.keySet().iterator().next();
        }

        fleeDirection = PVector.mult(predator.Velocity, -1).normalize();

        actor.Velocity = PVector.mult(fleeDirection, actor.Attributes.Speed());
    }

    @Override
    public void Update() {

        actor.Position.add(actor.Velocity);

        float minX = 0f;
        float minY = 0f;
        float maxX = SimManager.CanvasX;
        float maxY = SimManager.CanvasY;

        if (actor.Position.x < minX) {
            actor.Position.x = minX;
            actor.Velocity.x = Math.abs(actor.Velocity.x);
            fleeDirection.x = Math.abs(fleeDirection.x);
        } else if (actor.Position.x > maxX) {
            actor.Position.x = maxX;
            actor.Velocity.x = -Math.abs(actor.Velocity.x);
            fleeDirection.x = -Math.abs(fleeDirection.x);
        }

        if (actor.Position.y < minY) {
            actor.Position.y = minY;
            actor.Velocity.y = Math.abs(actor.Velocity.y);
            fleeDirection.y = Math.abs(fleeDirection.y);
        } else if (actor.Position.y > maxY) {
            actor.Position.y = maxY;
            actor.Velocity.y = -Math.abs(actor.Velocity.y);
            fleeDirection.y = -Math.abs(fleeDirection.y);
        }

        if (actor.InFOV.FishInFOV(predator)) {
            fleeDirection = PVector.sub(actor.Position, predator.Position).normalize();
            actor.Velocity = PVector.mult(fleeDirection, actor.Attributes.Speed());
        } else {
            timeUntilLastSawPredator += 1;
        }
    }



    @Override
    public void Exit() {
        predator = null;
        timeUntilLastSawPredator = 0;
    }

    @Override
    public FishStateTypes CheckTransitions() {
        if( timeUntilLastSawPredator >= timeUntilStopsFleeing)
        {
            return FishStateTypes.Searching;
        }
        return FishStateTypes.Fleeing;
    }

}