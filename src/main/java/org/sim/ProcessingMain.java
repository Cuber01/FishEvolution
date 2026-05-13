package org.sim;

import processing.core.PApplet;

public class ProcessingMain extends PApplet {

    public static void main(String[] args) {
        PApplet.main("org.sim.ProcessingMain");
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        background(0);
    }

    @Override
    public void draw() {
        fill(255, 0, 0);
        ellipse(mouseX, mouseY, 50, 50);
    }
}