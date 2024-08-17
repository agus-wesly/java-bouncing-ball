package org.example;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
    final int BASE = 10;
    int RANGE;
    private double x;
    private double y;
    private double speedX;
    private double speedY;

    public Ball(int x, int y, int range) {
        this.x = x;
        this.y = y;
        this.speedX = 0;
        this.speedY = 0;
        this.RANGE = range;

    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    public void setDirection(double speedX, double speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) (x * BASE), (int) (y * BASE), BASE, BASE);
    }

    public void checkIfHitWall() {
        if (isHitXWall()) {
            this.speedX = this.speedX * -1;
        }
        if (isHitYWall()) {
            this.speedY = this.speedY * -1;
        }
    }

    public void checkIfHitTarget(Target[] targets) {
        for (int i = 0; i < targets.length; ++i) {
            Target t = targets[i];
            if (t == null)
                continue;
            if ((t.getX() == Math.floor(x) || t.getX() == Math.ceil(x))
                    && (t.getY() == Math.round(y) || t.getY() == Math.ceil(y))) {
                this.speedY = this.speedY * -1;
                targets[i] = null;
                return;
            }
        }
    }

    public boolean isHitYWall() {
        return y < 0.0;
    }

    public boolean isHitXWall() {
        return x < 0.0 || x > RANGE;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
