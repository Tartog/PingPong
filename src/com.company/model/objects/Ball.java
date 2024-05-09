package com.company.model.objects;

import com.company.model.entites.Opponent;
import com.company.model.entites.Player;
import com.company.model.gamestate.GameStateManager;
import com.company.view.resources.Images;

import java.awt.*;

public class Ball extends Rectangle {

    private double ball_x = 413, ball_y = 258;
    private double startBall_x = 413, startBall_y = 258;
    private static int width, height;
    private int ballSpeedX = 5;
    private int ballSpeedY = 5 ;
    private boolean Reflect;
    private boolean Reflect2;

    public Ball(int width, int height, boolean temp) {
        if(!temp) { ballSpeedX = - ballSpeedX; }
        this.width = width;
        this.height = height;
        setBounds((int)ball_x, (int)ball_y, width, height);
    }

    public void tick(Player p, Opponent o) {
        setBounds((int)ball_x, (int)ball_y, width, height);
        ball_x += ballSpeedX;
        ball_y += ballSpeedY;
        if((this.intersects(new Rectangle(20, (int)p.getPlayerY(), p.getPlayerWidth(), p.getPlayerHeight()))) && (Reflect)) {
            ballSpeedX = - ballSpeedX;
            Reflect = false;
            Reflect2 = true;
        }
        if((this.intersects(new Rectangle(844, (int) o.getOpponent_y(), o.getPlayerWidth(), o.getPlayerHeight()))) && (Reflect2))
        {
            ballSpeedX = - ballSpeedX;
            Reflect = true;
            Reflect2 = false;
        }
        else
        {
            if (ball_x <= -20) {
                ballSpeedX = -ballSpeedX;
                Reflect = false;
                Reflect2 = true;
                if (ballSpeedX > 0) {
                    p.setScoreOpponent();
                } else {
                    p.setScorePlayer();
                }
                ball_x = startBall_x;
                ball_y = startBall_y;
            }
            else if(ball_x >= 850)
            {
                ballSpeedX = -ballSpeedX;
                Reflect = true;
                Reflect2 = false;
                if (ballSpeedX > 0) {
                    p.setScoreOpponent();
                } else {
                    p.setScorePlayer();
                }
                ball_x = startBall_x;
                ball_y = startBall_y;
            }
        }
        if(ball_y <= -20 || ball_y >= 530) { ballSpeedY *= -1; }
    }
    public void draw(Graphics g) { Images.drawObject(g, (int)ball_x, (int)ball_y); }
}