package org.example;

import processing.core.PApplet;



public class Test_Processing extends PApplet {



    @Override
    public void settings() {
        size(SimManager.CanvasX, SimManager.CanvasY);
    }

    @Override
    public void setup() {
        background(0);

    }

    @Override
    public void draw() {
        fill(255, 0, 0);
        //ellipse(mouseX, mouseY, 50, 50);
        rectangle a=new rectangle(0,0,mouseX,mouseY,mouseX%255);
        a.draw();
    }


    class rectangle {
        float x, y, a, b;
        int color;

        rectangle(float x, float y, float z, float q, int c) {
            this.x = x;
            this.y = y;
            this.a = z;
            this.b = q;
            this.color = c;
        }

        void draw() {
            fill(color);
            rect(x,y,a,b);
        }
    }
}