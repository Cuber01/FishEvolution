package org.sim;
import org.sim.fish.Fish;
import org.sim.food.Egg;
import org.sim.food.Food;
import org.sim.food.Meat;
import org.sim.food.Plant;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;



import java.util.List;

import static org.sim.SimManager.Entities;

public class Graphics extends PApplet {

    private final int CanvasX=SimManager.CanvasX;
    private final int CanvasY=SimManager.CanvasY;
    private PImage fish_searching, fish_fleeing, fish_pursuing, fish_reproducing,egg,meat,glon;
    private PImage img;
    private SimManager manager;
    private final SimDataMonitor dataMonitor = new SimDataMonitor();
    private boolean is_selected =false;
    private Fish selected_fish;
    private boolean paused = false;
    private float currentFrameRate = 60f;
    public Graphics(){}

    @Override
    public void settings() {
        size(Consts.CANVAS_WIDTH + Consts.SIDEBAR_WIDTH, Consts.CANVAS_HEIGHT);
    }

    @Override
    public void setup() {
        // preload images once
        fish_searching = loadImage("fish1 mini.png");
        fish_reproducing = loadImage("fish3 mini.png");
        fish_pursuing = loadImage("fish2 mini.png");
        fish_fleeing = loadImage("fish 4 mini.png");
        egg = loadImage("egg.png");
        meat = loadImage("meat.png");
        glon = loadImage("glon.png");

        dataMonitor.BuildHeader();

        background(0);
        manager = new SimManager(this, dataMonitor);
        manager.Setup();

        frameRate(currentFrameRate);
    }

    public void draw(){
        background(0);
        manager.Update();

        fill(255);
        textSize(12);
        String status = (SimManager.Paused ? "PAUSED" : "RUNNING") + " | FPS:" + (int) currentFrameRate;
        text(status, 10, 15);
    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            paused = !paused;
            SimManager.Paused = paused;
        }

        if (keyCode == RIGHT) {
            currentFrameRate = Math.min(240f, currentFrameRate + 10f);
            frameRate(currentFrameRate);
        } else if (keyCode == LEFT) {
            currentFrameRate = Math.max(1f, currentFrameRate - 10f);
            frameRate(currentFrameRate);
        }
    }

    public void draw_biomes(List<Biome> biomes){
        for(Biome b : biomes){
            fill(b.Color.getRed(), b.Color.getGreen(), b.Color.getBlue());
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
            switch (f.CurrentState.AssociatedType) {
                case Searching:
                    sprite = fish_searching;
                    break;
                case PursuingFood:
                    sprite = fish_pursuing;
                    break;
                case Fleeing:
                    sprite = fish_fleeing;
                    break;
                case FertilizingEggs:
                    sprite = fish_reproducing;
                    break;
                case LayingEggs:
                    sprite = fish_reproducing;
                    break;
                default:
                    sprite = fish_searching;
            }
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
            // you can provide an egg sprite if available, otherwise draw fallback
            sprite = egg;
        } else if (f instanceof Meat) {
            sprite = meat;
        } else if (f instanceof Plant) {
            sprite = glon;
        }

        if (sprite != null) {
            image(sprite, f.Position.x, f.Position.y);
        } else {
            if (f instanceof Egg) {
                fill(255,255,255);
                noStroke();
                rect(f.Position.x,f.Position.y,10,10);
            } else if (f instanceof Meat) {
                fill(255,0,0);
                noStroke();
                rect(f.Position.x,f.Position.y,10,10);
            } else if (f instanceof Plant) {
                fill(0,255,0);
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

        for (Entity e : Entities) {
            if (e instanceof Fish) {
                Fish f = (Fish) e;

                float d = mousePos.dist(f.Position);

                if (d < minDistance) {
                    minDistance = d;
                    nearestFish = f;
                }
            }
        }

        if (nearestFish != null && minDistance<100) {
            stats_display(mouseX, mouseY, nearestFish);
        }else is_selected=false;
    }

    public void draw_info(int fish_count){
        textSize(25);
        fill(255,0,0);
        text("fish alive: "+fish_count, CanvasX+10, 20);
        //text(fish_count, CanvasX+40, 40);
        if(is_selected) {
            if(selected_fish.IsDead) is_selected=false;
            fill(0,255,255);
            rect(selected_fish.Position.x,selected_fish.Position.y,20,20);
            textSize(15);
            fill(255,255,255);
            text("selected fish:", CanvasX+5, 100);
            textSize(12);
            PVector lastOffset = drawProfile(selected_fish.GetProfile(), CanvasX+5, 120, 0, 20);
            drawProfile(selected_fish.Attributes.GetProfile(), CanvasX+5+(int)lastOffset.x, 120+(int)lastOffset.y, 0, 20);
        }
    }

    private PVector drawProfile(List<String> profile, int startX, int startY, int offsetX, int offsetY)
    {
        PVector currentOffset = new PVector();

        for(String entry : profile)
        {
            text(entry, startX + currentOffset.x, startY + currentOffset.y);
            currentOffset.x += offsetX;
            currentOffset.y += offsetY;
        }

        return currentOffset;
    }

    public void stats_display(int x,int y,Fish f) {
        is_selected =true;
        selected_fish=f;
    }

    @Override
    public void dispose() {
        dataMonitor.ExportCsv("raport.csv");
        super.dispose();
    }
}
