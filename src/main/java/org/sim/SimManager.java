package org.sim;

import org.sim.fish.Fish;

import java.awt.*;
import java.util.ArrayList;

public class SimManager {
    public static ArrayList<Entity> EntitiesToAdd = new ArrayList<>(0);
    public static ArrayList<Entity> EntitiesToRemove = new ArrayList<>(0);
    public static final ArrayList<Entity> Entities = new ArrayList<>();
    private static final ArrayList<Biome> biomes = new ArrayList<Biome>();
    private static Processing graphics_handle;
    private final SimDataMonitor dataMonitor;

    public static int Ticks = 0;
    public static int FishCount;
    public static boolean Paused = false;

    public static final int CanvasX = Consts.CANVAS_WIDTH;
    public static final int CanvasY = Consts.CANVAS_HEIGHT;

    public SimManager(Processing gm, SimDataMonitor dataMonitor) {
        graphics_handle=gm;
        this.dataMonitor = dataMonitor;
    }

    public void Setup()
    {
        Biome shallow =new Biome(Consts.SHALLOW_BIOME_UPPER_BORDER, Consts.MIDDLE_BIOME_UPPER_BORDER, Consts.SHALLOW_BIOME_COLOR, Consts.SHALLOW_BIOME_ENERGY_MAX, Consts.SHALLOW_BIOME_PLANT_ENERGY);
        Biome middle =new Biome(Consts.MIDDLE_BIOME_UPPER_BORDER, Consts.DEEP_BIOME_UPPER_BORDER, Consts.MIDDLE_BIOME_COLOR, Consts.MIDDLE_BIOME_ENERGY_MAX, Consts.MIDDLE_BIOME_PLANT_ENERGY);
        Biome deep =new Biome(Consts.DEEP_BIOME_UPPER_BORDER, Consts.CANVAS_HEIGHT, Consts.DEEP_BIOME_COLOR, Consts.DEEP_BIOME_ENERGY_MAX, Consts.DEEP_BIOME_PLANT_ENERGY);
        biomes.add(shallow);
        biomes.add(middle);
        biomes.add(deep);

        for(int i=0;i<Consts.INITIAL_FISH_COUNT;i++) Entities.add(new Fish(graphics_handle));
    }

    public void Update(){
        if (Paused) {
            graphics_handle.background(0);
            graphics_handle.draw_biomes(biomes);
            graphics_handle.draw_info(FishCount);
            graphics_handle.draw_entities(Entities);
            return;
        }

        FishCount = 0;
        Ticks += 1;

        for (Biome b : biomes) {
            b.SpawnPlants(graphics_handle);
        }

        // Multithread fov?
        for (Entity e : Entities) {
            e.Update(); // TODO get current biome
            if(e instanceof Fish) // TODO this is expensive and dumb
            {
                ((Fish)e).CalculateFOV(Entities);
                FishCount++;
            }
        }

        Entities.addAll(EntitiesToAdd);
        for(Entity e : EntitiesToRemove)
        {
            Entities.remove(e);
        }

        EntitiesToRemove.clear();
        EntitiesToAdd.clear();

        FishCount = 0;
        for (Entity e : Entities) {
            if (e instanceof Fish) {
                FishCount++;
            }
        }

        if(Ticks % Consts.SIM_DATA_GATHERING_RATE == 0)
        {
            dataMonitor.GatherData();
        }

        if (FishCount == 0) {
            dataMonitor.ExportCsv("simulation_data.csv");
            return;
        }

        graphics_handle.background(0);

        graphics_handle.draw_biomes(biomes);
        graphics_handle.draw_info(FishCount);
        graphics_handle.draw_entities(Entities);


    }

}
