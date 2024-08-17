package org.example;

import java.awt.*;

public class Target {
    private int x;
    private int y;
    private int BASE = 10;

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x * BASE, y * BASE, 10, 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
