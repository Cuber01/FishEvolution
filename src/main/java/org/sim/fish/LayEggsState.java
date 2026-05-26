package org.sim.fish;

import org.sim.*;
import org.sim.food.Egg;

import static org.sim.fish.Fish.ReproductionEnergyUse;


class LayEggsState extends State<Fish, FishStateTypes> {
    private boolean finished = false;

    public LayEggsState(Fish actor, FishStateTypes type) {
        super(actor, type);
    }

    @Override
    public void Enter() {
        // TODO: Variable time til hatch?
        // TODO: Laying eggs should take time — in nature some fish spawn eggs quickly and some slowly
        // Idea for attribute: Time spent by mother on laying eggs vs time spend by the egg to hatch?
        SimManager.SpawnEntity(new Egg(actor.Position, actor.Attributes, 10f, ReproductionEnergyUse));
        actor.Energy -= ReproductionEnergyUse;
        finished = true;
    }

    @Override
    public void Update() {

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