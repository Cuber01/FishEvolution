package org.sim;
import processing.core.PApplet;
import processing.core.PVector;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Graphics extends PApplet {

    private final int CanvasX=SimManager.CanvasX;
    private final int CanvasY=SimManager.CanvasY;


    public Graphics(){}

    @Override
    public void settings() {

        size(CanvasX, CanvasY);

    }

    @Override
    public void setup() {
        background(0);
        SimManager manager = new SimManager(this);
        manager.Setup();

    }
    public void draw(){
        //draw_biomes();


    }
    public void draw_biomes(List<Biome> biomes){
        for(Biome b : biomes){
            fill(b.color.get(0),b.color.get(1),b.color.get(2));
            noStroke();
            rect(0,b.upperBorder,CanvasX,b.lowerBorder);

        }
    }
    public void draw_entity(PVector p){
        fill(255,0,0);
        rect(p.x,p.y,10,10);
    }
}
