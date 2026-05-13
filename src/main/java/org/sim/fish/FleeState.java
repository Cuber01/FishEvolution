package org.sim.fish;

import org.sim.*;
import processing.core.PVector;

import java.util.Map;

public class FleeState extends State<Fish, FishStateTypes> {
    public static int timeUntilStopsFleeing = 10;
    private int timeUntilLastSawPredator = 0;
    private PVector fleeDirection;
    private Fish predator = null;

    public FleeState(Fish actor) {
        super(actor);
    }

    @Override
    public void Enter() {

        for(Map.Entry<Fish, Float> entry : actor.InFOV.FishDist.entrySet())
        {
            if(entry.getKey().PursueFoodState.Target == actor)
            {
                predator = entry.getKey();
            }
        }

        // If a real predator was not found, our fish got scared for no reason and flees from a random fish
        if(predator == null) {
            predator = actor.InFOV.FishDist.entrySet().iterator().next().getKey();
        }

        // TODO check: It is likely acceptable to operate on data from last frame regarding velocity here
        fleeDirection = predator.Velocity.mult(-1);
    }

    // TODO check for bounds and bounce off them
    @Override
    public void Update() {
        actor.Velocity = fleeDirection;
        actor.Position.add(actor.Velocity);

        if(actor.InFOV.FishInFOV(predator))
        {
            fleeDirection = PVector.sub(actor.Position, predator.Position);
        } else {
            timeUntilLastSawPredator += 1;
        }
    }

    @Override
    public void Draw() {

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