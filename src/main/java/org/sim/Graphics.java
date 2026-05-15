package org.sim;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

public class Graphics extends PApplet {

    private SimManager manager;

    public Graphics()
    {

    }

    @Override
    public void settings() {
        size(SimManager.CanvasX, SimManager.CanvasY);
    }

    @Override
    public void setup() {
        background(0);
        manager = new SimManager(this);
        manager.Setup();
    }

    public void draw(){
        if (manager != null) {
            SimManager.Update();
        }
    }

    public void draw_biomes(List<Biome> biomes){
        if (biomes == null) return;
        for(Biome b : biomes){
            if (b.color != null && b.color.size() >= 3) {
                fill(b.color.get(0), b.color.get(1), b.color.get(2));
            }
            noStroke();
            float height = b.lowerBorder - b.upperBorder;
            rect(0, b.upperBorder, SimManager.CanvasX, height);
        }
    }

    public void draw_entity(PVector p){
        fill(255,0,0);
        rect(p.x,p.y,10,10);
    }

    public void draw_fish(PVector p){
        fill(0,255,0);
        rect(p.x,p.y,10,10);
    }
}
