package org.example;

import java.util.ArrayList;
import java.util.List;

public class Biome {
    public Biome(int uborder,int lborder,int colorR,int colorG,int colorB){
        lowerBorder=lborder;
        upperBorder=uborder;
        color.add(colorR);
        color.add(colorG);
        color.add(colorB);
    }
    public List<Integer> color = new ArrayList<>(3);
    public int foodPerUpdate;
    public int foodMax;

    public float visionPenalty;
    public int upperBorder;
    public int lowerBorder;

    public void SpawnFood() {
        SimManager.EntitiesToAdd.add(new Food());
    }
}
