package org.example;

import processing.core.PApplet;

public class Test_Processing extends PApplet {

    public static void main(String[] args) {

        PApplet.main("org.example.Test_Processing");
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