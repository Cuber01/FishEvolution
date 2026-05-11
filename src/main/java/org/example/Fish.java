package org.example;

import java.util.List;

public class Fish extends Entity {
    public Genes attributes;

    public enum FishState {
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

    }

    public void Update()
    {
        // Obliczyć co jest w polu widzenia
    }

    public void Draw()
    {

    }

    public void CalculateFov()
    {

    }
}
