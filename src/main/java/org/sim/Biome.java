package org.sim;

import java.awt.*;
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
    public int foodPerUpdate = 1;
    public int foodMax;

    public float visionPenalty;
    public int upperBorder;
    public int lowerBorder;

    public void SpawnFood(Graphics graphicsHandle) {
        for(int i = 0; i < foodPerUpdate; i++)
        {
            SimManager.EntitiesToAdd.add(new Food(graphicsHandle,
                    RND.RandomVector2(0, SimManager.CanvasX, upperBorder, lowerBorder), 10f));
        }
    }
}
