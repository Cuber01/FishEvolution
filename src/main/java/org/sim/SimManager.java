package org.sim;

import org.sim.fish.Fish;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimManager {
    public static List<Entity> EntitiesToAdd = new ArrayList<>(0);
    private static final List<Entity> entities = new CopyOnWriteArrayList<>();
    private static final List<Biome> biomes = new ArrayList<Biome>();
    private static Graphics graphics_handle;

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
        biomes.add(test_biome1);
        biomes.add(test_biome2);

        Fish example = new Fish(new PVector(50,50), graphics_handle, new Genes(1,1,1,10,0.5f,0), Sex.Male);
        entities.add(example);
    }

    public static void Update(){
        for (Biome b : biomes) {
            b.SpawnFood(graphics_handle);
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
        }

        entities.addAll(EntitiesToAdd);
        EntitiesToAdd.clear();

        graphics_handle.background(0);

        graphics_handle.draw_biomes(biomes);

        for (Entity e : entities) {
            e.Draw();
        }


    }

}
