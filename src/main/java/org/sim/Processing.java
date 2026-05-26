package org.sim;
import org.sim.fish.Fish;
import org.sim.food.Egg;
import org.sim.food.Meat;
import org.sim.food.Plant;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;



import java.util.List;


public class Processing extends PApplet {

    private PImage fish_searching, fish_fleeing, fish_pursuing, fish_reproducing, egg, meat, glon;
    private SimManager manager;
    private final SimDataMonitor dataMonitor = new SimDataMonitor();

    private boolean is_selected = false;
    private Fish selected_fish;

    private boolean paused = false;
    private float currentFrameRate = 60f;


    public static void main(String[] args) {
        processing.core.PApplet.main("org.sim.Processing");
    }

    @Override
    public void settings() {
        size(Consts.CANVAS_WIDTH + Consts.SIDEBAR_WIDTH, Consts.CANVAS_HEIGHT);
    }

    @Override
    public void setup() {
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

    // ======================= DRAWING =========================

    public void draw(){
        background(0);
        manager.Update();

        fill(255);
        textSize(12);
        String status = (SimManager.Paused ? "PAUSED" : "RUNNING") + " | FPS:" + (int) currentFrameRate;
        text(status, 10, 15);
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
            draw_entity(e);
        }
    }

    private void draw_entity(Entity e){
        PImage sprite = null;

        if(e instanceof Fish f)
        {
            if (f.CurrentState != null && f.CurrentState.AssociatedType != null) {
                sprite = switch (f.CurrentState.AssociatedType) {
                    case Searching -> fish_searching;
                    case PursuingFood -> fish_pursuing;
                    case Fleeing -> fish_fleeing;
                    case FertilizingEggs, LayingEggs -> fish_reproducing;
                };
            }
        } else {
            if (e instanceof Egg) {
                sprite = egg;
            } else if (e instanceof Meat) {
                sprite = meat;
            } else if (e instanceof Plant) {
                sprite = glon;
            }
        }

        image(sprite, e.Position.x, e.Position.y);
    }


    public void draw_info(int fish_count){
        textSize(25);
        fill(255,0,0);
        text("fish alive: "+fish_count, SimManager.CanvasX+10, 20);

        if(is_selected) {
            if(selected_fish.IsDead) is_selected=false;
            fill(0,255,255);
            rect(selected_fish.Position.x,selected_fish.Position.y,20,20);
            textSize(15);
            fill(255,255,255);
            text("selected fish:", SimManager.CanvasX+5, 100);
            textSize(12);
            PVector lastOffset = drawProfile(selected_fish.GetProfile(), SimManager.CanvasX+5, 120, 0, 20);
            drawProfile(selected_fish.Attributes.GetProfile(), SimManager.CanvasX+5+(int)lastOffset.x, 120+(int)lastOffset.y, 0, 20);
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

    public void stats_display(Fish f) {
        is_selected =true;
        selected_fish=f;
    }

    // ================= PROCESSING OVERRIDES ===================

    public void mousePressed()
    {
        if(mouseButton == RIGHT) return;

        PVector mousePos = new PVector(mouseX, mouseY);
        Fish nearestFish = null;
        float minDistance = Float.MAX_VALUE;

        for (Entity e : SimManager.GetEntities()) {
            if (e instanceof Fish f) {

                float d = mousePos.dist(f.Position);

                if (d < minDistance) {
                    minDistance = d;
                    nearestFish = f;
                }
            }
        }

        if (nearestFish != null && minDistance<100) {
            stats_display(nearestFish);
        }else is_selected=false;
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

    @Override
    public void dispose() {
        dataMonitor.ExportCsv("raport.csv");
        super.dispose();
    }
}
