package org.sim;

import org.sim.fish.Fish;

import java.awt.*;
import java.util.ArrayList;
/**
 * Main manager class that controls the core simulation loop.
 */
public class SimManager {
    private static final ArrayList<Entity> Entities = new ArrayList<>();
    private static final ArrayList<Entity> EntitiesToAdd = new ArrayList<>(0);
    private static final ArrayList<Entity> EntitiesToRemove = new ArrayList<>(0);
    private static final ArrayList<Biome> biomes = new ArrayList<>();
    private static Processing graphics_handle;
    private final SimDataMonitor dataMonitor;

    public static int Ticks = 0;
    public static int FishCount;
    public static boolean Paused = false;

    public static final int CanvasX = Consts.CANVAS_WIDTH;
    public static final int CanvasY = Consts.CANVAS_HEIGHT;

    /**
     * Creates a new simulation manager with graphics and data collection modules.
     */
    public SimManager(Processing gm, SimDataMonitor dataMonitor) {
        graphics_handle=gm;
        this.dataMonitor = dataMonitor;
    }

    /**
     * Initializes biomes and fish.
     */
    public void Setup()
    {
        Biome shallow =new Biome(Consts.SHALLOW_BIOME_UPPER_BORDER, Consts.MIDDLE_BIOME_UPPER_BORDER, Consts.SHALLOW_BIOME_COLOR, Consts.SHALLOW_BIOME_ENERGY_MAX, Consts.SHALLOW_BIOME_PLANT_ENERGY);
        Biome middle =new Biome(Consts.MIDDLE_BIOME_UPPER_BORDER, Consts.DEEP_BIOME_UPPER_BORDER, Consts.MIDDLE_BIOME_COLOR, Consts.MIDDLE_BIOME_ENERGY_MAX, Consts.MIDDLE_BIOME_PLANT_ENERGY);
        Biome deep =new Biome(Consts.DEEP_BIOME_UPPER_BORDER, Consts.CANVAS_HEIGHT, Consts.DEEP_BIOME_COLOR, Consts.DEEP_BIOME_ENERGY_MAX, Consts.DEEP_BIOME_PLANT_ENERGY);
        biomes.add(shallow);
        biomes.add(middle);
        biomes.add(deep);

        for(int i=0;i<Consts.INITIAL_FISH_COUNT;i++) Entities.add(new Fish());
    }

    /**
     * Updates the entire simulation state on every single tick.
     * It handles the simulation timer, asks biomes to spawn new plants, updates
     * the logic of all alive entities, tracks population count, and triggers
     * data gathering or exporting when needed.
     */
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
            b.SpawnPlants();
        }

        for (Entity e : Entities) {
            e.Update();
            if(e instanceof Fish)
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


        if(Ticks % Consts.SIM_DATA_GATHERING_RATE == 0)
        {
            dataMonitor.GatherData();
        }

        if (FishCount == 0) {
            dataMonitor.ExportCsv("simulation_data.csv");
            Paused=true;
            return;
        }

        graphics_handle.background(0);

        graphics_handle.draw_biomes(biomes);
        graphics_handle.draw_info(FishCount);
        graphics_handle.draw_entities(Entities);
    }

    /**
     * Returns an unmodifiable list of all active entities currently present in the simulation.
     */
    public static java.util.List<Entity> GetEntities() {
        return java.util.Collections.unmodifiableList(Entities);
    }

    /**
     * Safely queues a new entity (such as a baby fish, a laid egg, or a piece of meat)
     * to be added into the simulation world at the start of the next frame.
     * @param entity The entity object that needs to be spawned.
     */
    public static void SpawnEntity(Entity entity) {
        EntitiesToAdd.add(entity);
    }

    /**
     * Safely queues an existing entity to be removed from the simulation world
     * (for example, when a plant is eaten or a fish decays), removing it from the simulation.
     * @param entity The entity object that needs to be deleted.
     */
    public static void DespawnEntity(Entity entity) {
        EntitiesToRemove.add(entity);
    }

}
