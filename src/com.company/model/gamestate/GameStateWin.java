package com.company.model.gamestate;

import com.company.view.main.GamePanel;
import com.company.view.resources.Images;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static com.company.view.main.GamePanel.WIDTH;

public class GameStateWin extends GameState implements MouseListener, MouseMotionListener{

    private final int n;
    private final Color color1;
    private final double x;
    private final double y;
    private final double w;
    private final double h;
    public static boolean click;

    String[] list = new String[1];

    public GameStateWin(GameStateManager gsm) {
        super(gsm);
        x = WIDTH / 2 - 100;
        y = 0;
        w = 200;
        h = 75;
        n = 1;
        color1 = Color.WHITE;

        list[0] = "выход";
    }
    public void init() {
    }
    public void tick() {
        if(GamePanel.mouseX > this.x && GamePanel.mouseX < this.x + this.w && GamePanel.mouseY > (this.y + 100) * 4 &&
                GamePanel.mouseY < (this.y + 100) * 4 + this.h) {
            list[0] = "ВЫХОД";
            if(click) {
                System.exit(0);
            }
        }
        else {
            list[0] = "выход";
        }
    }

    public void draw(Graphics g) {
        Images.drawWinMenu(g, n, x, y, color1, list, h, w);
    }
    public void keyPressed(int k) {}
    public void keyReleased(int k) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) { click = true; }
    public void mouseReleased(MouseEvent e) { click = false; }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseDragged(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }
}