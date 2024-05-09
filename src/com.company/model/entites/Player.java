package com.company.model.entites;

import com.company.model.gamestate.GameOverState;
import com.company.model.gamestate.GameStateManager;
import com.company.model.gamestate.GameStateWin;
import com.company.view.resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Player extends Rectangle {

    private boolean up = false, down = false;
    private double player_x, player_y;
    private static int width, height;
    private final double moveSpeed = 5;
    private int serverPort = 9876;
    private int port = 9876;
    private InetAddress ipAddress;
    private boolean isHost;
    private int scorePlayer = 0;
    private int scoreOpponent = 0;
    private DatagramSocket clientSocket;
    private boolean startGame = false;


    public Player(int width, int height, double x, double y, boolean h) throws IOException {
        isHost = h;
        player_x = x;
        player_y = y;
        this.width = width;
        this.height = height;
        setBounds((int)player_x, (int)player_y, width, height);
        ipAddress = InetAddress.getByName("localhost");
        clientSocket = new DatagramSocket();
        byte[] sendData = new byte[1];
        byte[] receiveData = new byte[1];

        if(isHost) {

            sendData[0] = 1;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            port = receivePacket.getPort();
        }
        else
        {
            sendData[0] = 2;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            port = receivePacket.getPort();
        }
    }
    public int getScorePlayer()
    {
        return scorePlayer;
    }
    public int getScoreOpponent()
    {
        return scoreOpponent;
    }
    public void setScorePlayer()
    {
        scorePlayer++;
    }
    public void setScoreOpponent()
    {
        scoreOpponent++;
    }
    public int getPort()
    {
        return port;
    }
    public int getPortServer(){return serverPort; }
    public boolean getStartGame()
    {
        return startGame;
    }
    public int getPlayerWidth()
    {
        return width;
    }
    public int getPlayerHeight()
    {
        return height;
    }
    public double getPlayerX()
    {
        return player_x;
    }
    public double getPlayerY()
    {
        return player_y;
    }
    public void tick(GameStateManager gsm) throws IOException {
        setBounds((int)player_x, (int)player_y, width, height);

        if(scoreOpponent == 3) { gsm.getStates().push(new GameOverState(gsm)); }
        if(scorePlayer == 3) { gsm.getStates().push(new GameStateWin(gsm)); }

        if(down) {
            if(player_y == 475)
            {
                player_y = 475;
                byte[] sendData = new byte[1];
                sendData[0] = 3;

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                clientSocket.send(sendPacket);
            }
            else
            {
                player_y += moveSpeed;
                byte[] sendData = new byte[1];
                sendData[0] = 3;

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                clientSocket.send(sendPacket);
            }

        }
        if(up) {
            if(player_y == 0)
            {
                player_y = 0;
                byte[] sendData = new byte[1];
                sendData[0] = 4;

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                clientSocket.send(sendPacket);
            }
            else {
                player_y -= moveSpeed;

                byte[] sendData = new byte[1];
                sendData[0] = 4;

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                clientSocket.send(sendPacket);
            }
        }
    }
    public void draw(Graphics g)
    {
        Images.drawPlayer(g, this);
    }
    public void keyPressed(int k) {

        if(k == KeyEvent.VK_W)
        {
            up = true;
            startGame = true;
        }

        if(k == KeyEvent.VK_S)
        {
            down = true;
            startGame = true;
        }
    }
    public void keyReleased(int k) {
        if(k == KeyEvent.VK_W) up = false;
        if(k == KeyEvent.VK_S) down = false;
    }
}