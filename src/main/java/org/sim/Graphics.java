package org.sim;
import org.sim.fish.Fish;
import org.sim.fish.FishStateTypes;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;



import java.util.List;

import static org.sim.SimManager.entities;

public class Graphics extends PApplet {

    private final int CanvasX=SimManager.CanvasX;
    private final int CanvasY=SimManager.CanvasY;
    //public List<Entity> entities_to_draw = new ArrayList<>();
    private PImage fish_searching, fish_fleeing, fish_pursuing, fish_reproducing;
    private PImage img;
    private SimManager manager;

    public Graphics(){}

    @Override
    public void settings() {
        size(CanvasX, CanvasY);
    }

    @Override
    public void setup() {
        // preload images once
        fish_searching = loadImage("fish1.png");
        fish_reproducing = loadImage("fish2.png");
        fish_pursuing = loadImage("fish3.png");
        fish_fleeing = loadImage("fish4.png");

        if (fish_searching == null) println("WARNING: fish1.png not found");
        if (fish_reproducing == null) println("WARNING: fish2.png not found");
        if (fish_pursuing == null) println("WARNING: fish3.png not found");
        if (fish_fleeing == null) println("WARNING: fish4.png not found");



        background(0);
        manager = new SimManager(this);
        manager.Setup();

    }

    public void draw(){
        background(0);
        manager.Update();
    }

    public void draw_biomes(List<Biome> biomes){
        for(Biome b : biomes){
            if (b.color != null && b.color.size() >= 3) {
                fill(b.color.get(0), b.color.get(1), b.color.get(2));
            }
            noStroke();
            float height = b.lowerBorder - b.upperBorder;
            rect(0, b.upperBorder, SimManager.CanvasX, height);
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
    public void draw_fish(Fish f){
        PImage sprite = null;
        if (f.CurrentState != null && f.CurrentState.AssociatedType != null) {
            sprite = switch (f.CurrentState.AssociatedType) {
                case Searching -> fish_searching;
                case PursuingFood -> fish_pursuing;
                case Fleeing -> fish_fleeing;
                case FertilizingEggs, LayingEggs -> fish_reproducing;
                default -> fish_searching;
            };
        }

        if (sprite != null) {
            image(sprite, f.Position.x, f.Position.y);
        } else {
            // fallback
            fill(0,255,0);
            rect(f.Position.x,f.Position.y,20,20);
        }
    }
    private void draw_food(Food f){
        PImage sprite = null;
        if (f instanceof Egg) {
            sprite = null;
        } else if (f instanceof Meat) {
            sprite = null;
        }

        if (sprite != null) {
            image(sprite, f.Position.x, f.Position.y);
        } else {
            if (f instanceof Egg) {
                fill(0,0,0);
                noStroke();
                rect(f.Position.x,f.Position.y,10,10);
            } else if (f instanceof Meat) {
                fill(255,0,0);
                noStroke();
                rect(f.Position.x,f.Position.y,10,10);
            } else {
                fill(200);
                noStroke();
                rect(f.Position.x,f.Position.y,8,8);
            }
        }
    }

    public void mousePressed()
    {
        if(mouseButton == RIGHT) return;

        PVector mousePos = new PVector(mouseX, mouseY);
        Fish nearestFish = null;
        float minDistance = Float.MAX_VALUE;

        for (Entity e : entities) {
            if (e instanceof Fish) {
                Fish f = (Fish) e;

                float d = mousePos.dist(f.Position);

                if (d < minDistance) {
                    minDistance = d;
                    nearestFish = f;
                }
            }
        }

        if (nearestFish != null) {
            stats_display(mouseX, mouseY, nearestFish);
        }
    }

    public void stats_display(int x,int y,Fish f) {
        System.out.println("iubdoew9uoewu");
    }
}
