package com.company.view.resources;

import com.company.model.entites.Opponent;
import com.company.model.entites.Player;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static com.company.view.main.GamePanel.HEIGHT;
import static com.company.view.main.GamePanel.WIDTH;

public class Images {

    private static BufferedImage background;
    private static BufferedImage player;
    private static BufferedImage ball;
    public static void drawPlayer(Graphics g, Player p)
    {
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.ITALIC, 50);
        g.setFont(font);
        g.drawString(/*"hp - " + */ "" + p.getScorePlayer() /*+ "/3"*/, 380, 50);
        g.setColor(Color.BLACK);
        g.drawString(/*"coin - " +*/ "" + p.getScoreOpponent(), 480, 50);
        g.drawImage(player, (int) p.getPlayerX(), (int) p.getPlayerY(), p.getPlayerWidth(), p.getPlayerHeight(), null);// отрисовываем элемент в координатах

    }
    public static void drawPlayer(Graphics g, Opponent p)
    {
        g.drawImage(player, (int) p.getPlayerX(), (int) p.getPlayerY(), p.getPlayerWidth(), p.getPlayerHeight(), null);// отрисовываем элемент в координатах
    }
    public static void drawBackground(Graphics g)
    {
        g.drawImage(background, 0, 0, WIDTH,  HEIGHT, null);// отрисовываем элемент в координатах
    }
    public static void drawObject(Graphics g,int x, int y)
    {
        g.setColor(Color.BLACK);
        g.drawImage(ball, x + 17, y + 17, 30, 30, null);
    }
    public static void drawMainMenu(Graphics g, int n, Color color1, double x, double y, String[] list, double w, double h)
    {
        String img;
        img = "res/Back/backMenu.png";
        g.drawImage(new ImageIcon(img).getImage(), 0, 0, null);
        for(int i = 0;i < n;i++) {
            img = "res/Button/button.png";
            g.drawImage(new ImageIcon(img).getImage(), (int)x, (int) (y + 200) * i + 10, null);

            g.setColor(Color.BLACK);

            Font font = new Font("Arial", Font.ITALIC, 25);
            g.setFont(font);
            long length = (int) g.getFontMetrics().getStringBounds(list[i], g).getWidth();
            g.drawString(list[i], (int) (x + w / 2) - (int) (length / 2), (int) ((y + 200) * i + 10 + (h/3) * 2));
        }
    }
    public static void drawWinMenu(Graphics g, int n, double x, double y, Color color1, String[] list, double h, double w)
    {



        String img;
        img = "res/Back/backMenu.png";
        g.drawImage(new ImageIcon(img).getImage(), 0, 0, null);
        for(int i = 0;i < n;i++) {
            img = "res/Button/button.png";
            g.drawImage(new ImageIcon(img).getImage(), (int) x, (int) (y + 100) * 4 + 10, null);

            g.setColor(Color.BLACK);

            Font font = new Font("Arial", Font.ITALIC, 25);
            g.setFont(font);
            long length = (int) g.getFontMetrics().getStringBounds(list[i],g).getWidth();
            g.drawString(list[i], (int) (x + w / 2) - (int) (length / 2), (int) ((y + 100) * 4 + 10 + (h/3) * 2));
        }



        g.setColor(Color.YELLOW);
        Font font2 = new Font("Arial", Font.ITALIC, 50);
        g.setFont(font2);
        g.drawString("YOU WIN !!!", 380, 50);

    }
    public static void drawGameOver(Graphics g, int n, Color color1, double x, double y, double w, double h, String[] list) {



        String img;
        img = "res/Back/backMenu.png";
        g.drawImage(new ImageIcon(img).getImage(), 0, 0, null);
        for(int i = 0;i < n;i++) {
            img = "res/Button/button.png";
            g.drawImage(new ImageIcon(img).getImage(), (int) x, (int) (y + 100) * 4 + 10, null);

            g.setColor(Color.BLACK);

            Font font = new Font("Arial", Font.ITALIC, 25);
            g.setFont(font);
            long length = (int) g.getFontMetrics().getStringBounds(list[i],g).getWidth();
            g.drawString(list[i], (int) (x + w / 2) - (int) (length / 2), (int) ((y + 100) * 4 + 10 + (h/3) * 2));
        }


        g.setColor(Color.RED);
        Font font2 = new Font("Arial", Font.ITALIC, 50);
        g.setFont(font2);
        g.drawString("YOU LOSE", 380, 50);

    }
    public Images()
    {
        player = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        ball = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/Back/wp.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player = ImageIO.read(getClass().getResourceAsStream("/Player/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ball = ImageIO.read(getClass().getResourceAsStream("/Coin/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}