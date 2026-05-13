package org.sim;

import org.sim.fish.Fish;

import java.util.List;

public class SimManager {
    public static List<Entity> EntitiesToAdd;
    private static List<Entity> entities;
    private static List<Biome> biomes;

    public static float CanvasX;
    public static float CanvasY;

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
    }

}
