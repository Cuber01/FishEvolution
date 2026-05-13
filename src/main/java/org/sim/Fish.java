package org.sim;

public class Fish extends Entity {
    public Genes attributes;

    public enum FishState {
        Searching,
        PursuingFood,
        Fleeing,
        LayingEggs,
        FertilizingEggs
    }

    public Sex sex;
    private int hp;
    private float energy;
    private FishState state;

    public Fish()
    {
        this.state = FishState.Searching;
        enterState(this.state);
    }

    public void Update()
    {
        if(this.state == null) return;

        switch(this.state) {
            case Searching:
                updateSearching();
                break;
            case PursuingFood:
                updatePursuingFood();
                break;
            case Fleeing:
                updateFleeing();
                break;
            case LayingEggs:
                updateLayingEggs();
                break;
            case FertilizingEggs:
                updateFertilizingEggs();
                break;
            default:
                break;
        }
    }

    public void Draw()
    {

    }

    public void CalculateFov()
    {

    }

    private void setState(FishState newState) {
        exitState(this.state);
        this.state = newState;
        enterState(newState);
    }

    private void enterState(FishState s) {
        switch(s) {
            case Searching: enterSearching(); break;
            case PursuingFood: enterPursuingFood(); break;
            case Fleeing: enterFleeing(); break;
            case LayingEggs: enterLayingEggs(); break;
            case FertilizingEggs: enterFertilizingEggs(); break;
        }
    }

    private void exitState(FishState s) {
        switch(s) {
            case Searching: exitSearching(); break;
            case PursuingFood: exitPursuingFood(); break;
            case Fleeing: exitFleeing(); break;
            case LayingEggs: exitLayingEggs(); break;
            case FertilizingEggs: exitFertilizingEggs(); break;
        }
    }

    private void enterSearching() {
    }

    private void updateSearching() {

    }

    private void exitSearching() {
    }

    private void enterPursuingFood() {
    }

    private void updatePursuingFood() {
    }

    private void exitPursuingFood() {
    }

    private void enterFleeing() {
    }

    private void updateFleeing() {
    }

    private void exitFleeing() {
    }

    private void enterLayingEggs() {
    }

    private void updateLayingEggs() {
    }

    private void exitLayingEggs() {
    }

    private void enterFertilizingEggs() {
    }

    private void updateFertilizingEggs() {
    }

    private void exitFertilizingEggs() {
    }
}
