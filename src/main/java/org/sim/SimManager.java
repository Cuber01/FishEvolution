package org.sim;

import org.sim.fish.Fish;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimManager {
    public static List<Entity> EntitiesToAdd = new ArrayList<>(0);
    private static List<Entity> entities = new CopyOnWriteArrayList<>();
    private static List<Biome> biomes = new ArrayList<Biome>();
    private static Graphics graphics_handle;

    private static double timePerTick = 1000000000.0 / 60.0; //main loop speed

    public static int CanvasX=1200;
    public static int CanvasY=800;

    public SimManager(Graphics gm){
        graphics_handle=gm;
    }
    public void Setup(){ //robocza metoda do testów
        Biome test_biome1 =new Biome(0,200,1,100,200);
        Biome test_biome2 =new Biome(200,400,15, 73, 79);
        biomes.add(test_biome1);
        biomes.add(test_biome2);
        graphics_handle.draw_biomes(biomes);
        Entity example = new Entity(graphics_handle);
        example.Position=new PVector(100,100);
        entities.add(example);


        MainLoop();
    }
    public static void MainLoop() {
        long lastTime = System.nanoTime();
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                update();
                delta--;
            }
        }
    }
    private static void update(){

        for (Biome b : biomes) {
            b.SpawnFood();
        }

        // Multithread?
//        for (Entity e : entities) {
//            if(e instanceof Fish)
//            {
//                Fish f = (Fish)e;
//                f.CalculateFov();
//            }
//        }
        //graphics_handle.draw_biomes(biomes);
        for (Entity e : entities) {
            //e.Update(biomes.get(0)); // TODO get current biome

            e.Draw();
        }

        //entities.addAll(EntitiesToAdd);
        EntitiesToAdd.clear();
    }

}
