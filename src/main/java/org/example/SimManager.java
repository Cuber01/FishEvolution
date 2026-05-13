package org.example;

import java.util.ArrayList;
import java.util.List;

public class SimManager {
    public static List<Entity> EntitiesToAdd;
    private static List<Entity> entities;
    private static List<Biome> biomes = new ArrayList<Biome>();

    public static int CanvasX=1200;
    public static int CanvasY=800;

    public static void Setup(){ //robocza metoda do testów
        Biome test_biome1 =new Biome(0,200,1,100,200);
        Biome test_biome2 =new Biome(200,400,15, 73, 79);
        biomes.add(test_biome1);
        biomes.add(test_biome2);
        Graphics.biomes=biomes;

    }
    public static void Main() {

        for (Biome b : biomes) {
            b.SpawnFood();
        }

        // Multithread?
        for (Entity e : entities) {
            if(e instanceof Fish)
            {
                Fish f = (Fish)e;
                f.CalculateFov();
            }
        }

        for (Entity e : entities) {
            e.Update(biomes.get(0)); // TODO get current biome

            e.Draw();
        }

        entities.addAll(EntitiesToAdd);
        EntitiesToAdd.clear();
        Graphics.entities=entities;
    }

}
