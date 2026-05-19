package org.sim;

import org.sim.fish.Fish;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimManager {
    public static ArrayList<Entity> EntitiesToAdd = new ArrayList<>(0);
    public static ArrayList<Entity> EntitiesToRemove = new ArrayList<>(0);
    public static final ArrayList<Entity> entities = new ArrayList<>();
    private static final ArrayList<Biome> biomes = new ArrayList<Biome>();
    private static Graphics graphics_handle;

    private int fish_count;
    private static double timePerTick = 1000000000.0 / 60.0; //main loop speed

    public static int CanvasX=1200;
    public static int CanvasY=800;

    public SimManager(Graphics gm) {
        graphics_handle=gm;
    }

    public void Setup()
    {

        Biome test_biome1 =new Biome(0,200,1,100,200);
        Biome test_biome2 =new Biome(200,400,15, 73, 79);
        Biome test_biome3 =new Biome(400,800,20, 0, 0);
        biomes.add(test_biome1);
        biomes.add(test_biome2);
        biomes.add(test_biome3);
        for(int i=0;i<100;i++) entities.add(new Fish(graphics_handle));

        //MainLoop();
        graphics_handle.draw();
    }

    public void MainLoop() {
        long lastTime = System.nanoTime();
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                Update();
                delta--;
            }
        }
    }


    public void Update(){
        fish_count=0;
        for (Biome b : biomes) {
            b.SpawnPlants(graphics_handle);
        }

        // Multithread?
//        for (Entity e : entities) {
//            if(e instanceof Fish)
//            {
//                Fish f = (Fish)e;
//                f.CalculateFov();
//            }
//        }

        for (Entity e : entities) {
            e.Update(biomes.get(0)); // TODO get current biome
            if(e instanceof Fish) // TODO this is expensive and dumb
            {
                ((Fish)e).CalculateFOV(entities);
                fish_count++;
            }
        }

        entities.addAll(EntitiesToAdd);
        for(Entity e : EntitiesToRemove)
        {
            entities.remove(e);
        }

        EntitiesToRemove.clear();
        EntitiesToAdd.clear();

        graphics_handle.background(0);

        graphics_handle.draw_biomes(biomes);
        graphics_handle.draw_info(fish_count);
        graphics_handle.draw_entities(entities);


    }

}
