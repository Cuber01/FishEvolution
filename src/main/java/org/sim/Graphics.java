package org.sim;
import org.sim.fish.Fish;
import org.sim.fish.FishStateTypes;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Graphics extends PApplet {

    private final int CanvasX=SimManager.CanvasX;
    private final int CanvasY=SimManager.CanvasY;
    //public List<Entity> entities_to_draw = new ArrayList<>();
    PImage fish_searching,fish_fleeing,fish_pursuing,fish_reproducing,img;

    public Graphics(){}

    @Override
    public void settings() {

        size(CanvasX, CanvasY);

    }

    @Override
    public void setup() {
        //fish_searching=loadImage("fish1.png");
//        fish_fleeing=loadImage("fish4.png");
//        fish_pursuing=loadImage("fish3.png");
//        fish_reproducing=loadImage("fish2.png");
        background(0);
        SimManager manager = new SimManager(this);


        manager.Setup();

        //manager.Setup();

    }
    public void draw(){

    }
    public void draw_biomes(List<Biome> biomes){
        for(Biome b : biomes){
            fill(b.color.get(0),b.color.get(1),b.color.get(2));
            noStroke();
            rect(0,b.upperBorder,CanvasX,b.lowerBorder);

        }
    }

    public void draw_entities(List<Entity> entities_to_draw){
        for(Entity e : entities_to_draw){
            if(e instanceof Fish) {
                draw_fish((Fish)e);
                continue;
            }
            if(e instanceof Food){
                draw_food((Food)e);
                continue;
            }

        }
    }
    private void draw_fish(Fish f){
        if (f.currentState.AssociatedType == FishStateTypes.Searching) {
            img=loadImage("fish4.png");
            //image(img, f.Position.x, f.Position.y);
            fill(0,255,0);
            rect(f.Position.x,f.Position.y,20,20);

            return;
        }
        if (f.currentState.AssociatedType == FishStateTypes.PursuingFood) {
            //img=loadImage("fish2.png");
            //image(img, f.Position.x, f.Position.y);
            fill(0,255,255);
            rect(f.Position.x,f.Position.y,20,20);
            return;
        }
        if (f.currentState.AssociatedType == FishStateTypes.Fleeing) {
            //img=loadImage("fish3.png");
            //(img, f.Position.x, f.Position.y);
            fill(100,0,255);
            rect(f.Position.x,f.Position.y,20,20);
            return;
        }
        if (f.currentState.AssociatedType == FishStateTypes.FertilizingEggs || f.currentState.AssociatedType == FishStateTypes.LayingEggs) {
            //img=loadImage("fish1.png");
            //image(img, f.Position.x, f.Position.y);
                fill(100,255,100);
            rect(f.Position.x,f.Position.y,20,20);
        }
    }
    private void draw_food(Food f){
        if(f instanceof Egg){
            fill(0,0,0);
            noStroke();
            rect(f.Position.x,f.Position.y,10,10);
            return;
        }
        if(f instanceof Meat){
            fill(255,0,0);
            noStroke();
            rect(f.Position.x,f.Position.y,10,10);
        }
    }
    public void stats_display(int x,int y,Fish f) {
        System.out.println("iubdoew9uoewu");
    }
}
