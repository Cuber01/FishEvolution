package org.sim;

import java.util.List;

public class SimManager {
    public static List<Entity> EntitiesToAdd;
    private static List<Entity> entities;
    private static List<Biome> biomes;

    public int CanvasX;
    public int CanvasY;

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
