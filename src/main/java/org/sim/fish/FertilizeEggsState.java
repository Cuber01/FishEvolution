package org.sim.fish;

import org.sim.Egg;
import org.sim.SimManager;
import org.sim.State;

public class FertilizeEggsState extends State<Fish, FishStateTypes> {
    private boolean finished = false;

    public FertilizeEggsState(Fish actor) {
        super(actor);
    }

    @Override
    public void Enter() {
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