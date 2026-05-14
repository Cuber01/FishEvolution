package org.example;



public class Entity {
    Graphics graphics_handle;
    public Entity(){}
    public Entity(Graphics parent){
        graphics_handle=parent;
    }

    public Point position;
    // public Texture texture

    public void Update(Biome currentBiome) {}
    public void Draw() {
        graphics_handle.draw_ent(position);
    }
}
