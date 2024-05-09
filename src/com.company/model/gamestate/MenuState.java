package com.company.model.gamestate;

import com.company.view.main.GamePanel;
import com.company.view.resources.Images;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static com.company.view.main.GamePanel.WIDTH;

public class MenuState extends GameState implements MouseListener, MouseMotionListener {

    private final int n;
    private final Color color1;
    private final double x;
    private final double y;
    private final double w;
    private final double h;
    private static boolean click;
    String[] list = new String[5];
    public static void setClick(boolean clk)
    {
        click = clk;
    }

    public MenuState(GameStateManager gsm) {
        super(gsm);
        x = WIDTH / 2 - 100;
        y = 0;
        w = 200;
        h = 75;
        n = 3;
        color1 = Color.WHITE;
        list[0] = "создать игру";
        list[1] = "присоединиться";
        list[2] = "выход";
    }
    public void init() { }
    public void tick() {
        if(GamePanel.mouseX > this.x && GamePanel.mouseX < this.x + this.w && GamePanel.mouseY > (this.y + 200) * 0 &&
                GamePanel.mouseY < (this.y + 200) * 0 + this.h) {
            list[0] = "СОЗДАТЬ ИГРУ";
            if(click) {
                gsm.getStates().push(new HostState(gsm));
            }
        }
        else {
            list[0] = "создать игру";
        }
        if(GamePanel.mouseX > this.x && GamePanel.mouseX < this.x + this.w && GamePanel.mouseY > (this.y + 200) * 1 &&
                GamePanel.mouseY < (this.y + 200) * 1 + this.h) {
            list[1] = "ПРИСОЕДИНИТЬСЯ";
            if(click) {
                gsm.getStates().push(new ClientState(gsm));
            }
        }
        else {
            list[1] = "присоединиться";
        }
        if(GamePanel.mouseX > this.x && GamePanel.mouseX < this.x + this.w && GamePanel.mouseY > (this.y + 200) * 2 &&
                GamePanel.mouseY < (this.y + 200) * 2 + this.h) {
            list[2] = "ВЫХОД";
            if(click) {
                System.exit(0);
            }
        }
        else {
            list[2] = "выход";
        }
    }
    public void draw(Graphics g) {
        Images.drawMainMenu(g, n, color1, x, y, list, w, h);
    }
    public double getY()
    {
        return y;
    }
    public void keyPressed(int k) { }
    public void keyReleased(int k) { }
    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) {
        MenuState.click = true;
    }
    public void mouseReleased(MouseEvent e) {
        MenuState.click = false;
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseDragged(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }
}
