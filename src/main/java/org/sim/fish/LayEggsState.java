package org.sim.fish;

import org.sim.*;

import java.util.Map;

class LayEggsState extends State<Fish, FishStateTypes> {
    private boolean finished = false;

    public LayEggsState(Fish actor) {
        super(actor);
    }

    @Override
    public void Enter() {
        // TODO: Variable time til hatch?
        // TODO: Laying eggs should take time — in nature some fish spawn eggs quickly and some slowly
        // Idea for attribute: Time spent by mother on laying eggs vs time spend by the egg to hatch?
        SimManager.EntitiesToAdd.add(new Egg(actor.Position.copy(), actor.Attributes, 10f));
        finished = true;
    }

    @Override
    public void Update() {

    }

    @Override
    public void Draw() {

    }

    @Override
    public void Exit() {
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