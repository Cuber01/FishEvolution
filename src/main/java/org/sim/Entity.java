package org.sim;

import processing.core.PVector;



public class Entity {
    public PVector Position;
    public static final float DistTolerance = 1f;
    public boolean Dead = false;
    org.sim.Graphics graphics_handle;
    public Entity(){}

//    public Entity(Graphics parent) {
//        graphics_handle=parent;
//    }

    // public Texture texture

    public void Update(Biome currentBiome) {}

}
