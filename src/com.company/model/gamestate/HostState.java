package com.company.model.gamestate;

import com.company.model.entites.Opponent;
import com.company.model.entites.Player;
import com.company.model.objects.Ball;
import com.company.view.resources.Back;
import java.awt.*;
import java.io.IOException;

public class HostState extends GameState{
    private Player player1;
    private Opponent opponent;
    private Ball ball;
    private Back back;



    public HostState(GameStateManager gsm) {
        super(gsm);
    }
    public void init() {
        try {
            player1 = new Player(20,  100, 0, 250, true);
            opponent = new Opponent(20, 100, 874, 250, true, player1.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ball = new Ball(15, 15, true);
        back = new Back();
    }
    public void tick() {
        try {
            player1.tick(gsm);
            opponent.tick();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(opponent.getPlayer2Connected()) { ball.tick(player1, opponent); }
    }
    public void draw(Graphics g) {
        back.draw(g);
        player1.draw(g);
        opponent.draw(g);
        ball.draw(g);
    }
    public void keyPressed(int k) {
        player1.keyPressed(k);
    }
    public void keyReleased(int k) {
        player1.keyReleased(k);
    }
}