package org.example;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainWindow extends JPanel implements KeyListener {

    private int BASE = 10;
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private int RANGE;

    private class Crutch {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        Crutch() {
            final int midPoint = RANGE / 2;
            this.x1 = (midPoint - 6);
            this.y1 = RANGE - 2;
            this.x2 = (midPoint + 6);
            this.y2 = RANGE - 2;
        }

        public void moveLeft() {
            int newX = x1 - 1;
            if (newX >= 0) {
                x1 = newX;
                x2 = x2 - 1;
            }
        }

        public void moveRight() {
            int newX = x2 + 1;
            if (newX <= RANGE) {
                x2 = newX;
                x1 = x1 + 1;
            }

        }

        public void checkIfHitBall(Ball ball) {
            boolean trueX = ball.getX() >= this.x1 && ball.getX() <= this.x2;
            boolean trueY = ball.getY() == RANGE - 3;
            if (!(trueX && trueY))
                return;
            int sub = x2 - x1;
            double res = ((ball.getX() - x1) / sub) * 100;
            if (res > 0 && res < 100) {
                // set ball direction
                if (res < 50) { // left
                    double div = 1 - (res / 50);
                    div = div * -1;
                    ball.setDirection(div, -1);

                } else { // right
                    double div = res / 100;
                    ball.setDirection(div, -1);
                }
            }
        }

        public void draw(Graphics g) {
            g.setColor(Color.green);
            g.drawLine(x1 * BASE, y1 * BASE, x2 * BASE, y2 * BASE);
        }
    }

    private Crutch crutch;
    private Ball ball;
    private Target[] targets;

    boolean isRunning;
    Thread gameLoop;

    MainWindow(int width, int height) {
        this.RANGE = width / this.BASE;
        Dimension dim = new Dimension(width, height);
        setPreferredSize(dim);
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.black);

        this.crutch = new Crutch();
        final int midPoint = RANGE / 2;
        this.ball = new Ball(midPoint, midPoint, this.RANGE);
        this.ball.setDirection(0, 1);

        // Target
        int k = 0;
        this.targets = new Target[90];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 30; ++j) {
                targets[k++] = new Target(j * 2, i * 2);
            }
        }

        this.setupGameLoop();
        this.isRunning = true;
        this.gameLoop.start();

        // this.ball.setDirection(0, 1);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // line
        // for (int i = 1; i <= HEIGHT / BASE; ++i) {
        // g.setColor(Color.GRAY);
        // g.drawLine(0, i * BASE, WIDTH, i * BASE);

        // }
        // for (int i = 1; i <= WIDTH / BASE; ++i) {
        // g.setColor(Color.GRAY);
        // g.drawLine(i * BASE, 0, i * BASE, HEIGHT);

        // }

        // crutch
        crutch.draw(g);
        // ball
        ball.draw(g);
        // target
        for (Target t : targets) {
            if (t != null) {
                t.draw(g);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            crutch.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            crutch.moveRight();
        }
    }

    private void setupGameLoop() {
        gameLoop = new Thread(() -> {
            while (isRunning) {
                ball.move();
                // Check for collition
                crutch.checkIfHitBall(ball);
                // Check if ball hit wall
                ball.checkIfHitWall();
                ball.checkIfHitTarget(targets);
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
