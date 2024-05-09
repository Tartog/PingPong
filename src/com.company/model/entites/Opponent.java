package com.company.model.entites;

import com.company.view.resources.Images;
import java.awt.*;
import java.io.IOException;
import java.net.*;

public class Opponent extends Rectangle {
    private double opponent_x, opponent_y;
    private static int width, height;
    private final double moveSpeed = 5;
    private int port = -1;
    private InetAddress ipAddress;
    private boolean isHost;
    private DatagramSocket clientSocket;
    private boolean player2Connected = false;

    public double getOpponent_y()
    {
        return opponent_y;
    }


    public Opponent(int width, int height, double x, double y, boolean h, int p) throws IOException {
        isHost = h;
        port = p;
        opponent_x = x;
        opponent_y = y;
        this.width = width;
        this.height = height;
        setBounds((int) opponent_x, (int) opponent_y, width, height);

        ipAddress = InetAddress.getByName("localhost");

        clientSocket = new DatagramSocket();

        byte[] sendData = new byte[1];

        if(isHost) {
            sendData[0] = 5;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);
            System.out.println("отправивили сообщение о подключении оппонента");

            ResponseThread rt = new ResponseThread();
            rt.start();
        }
        else
        {
            sendData[0] = 6;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);
            System.out.println("отправивили сообщение о подключении оппонента");

            ResponseThread rt = new ResponseThread();
            rt.start();
        }
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
        return opponent_x;
    }
    public double getPlayerY()
    {
        return opponent_y;
    }
    public void tick() { setBounds((int) opponent_x, (int) opponent_y, width, height); }
    public void draw(Graphics g)
    {
        Images.drawPlayer(g, this);
    }
    public boolean getPlayer2Connected()
    {
        return player2Connected;
    }



    private class ResponseThread extends Thread
    {
        public void run()
        {
            byte[] receiveData = new byte[1];

            System.out.println(clientSocket.getPort());
            while(true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    clientSocket.receive(receivePacket);
                    player2Connected = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] message = receivePacket.getData();

                if(message[0] == 3)
                {
                    if(opponent_y == 475) { opponent_y = 475; }
                    else { opponent_y += moveSpeed; }
                }
                else if(message[0] == 4)
                {
                    if(opponent_y == 0) { opponent_y = 0; }
                    else { opponent_y -= moveSpeed; }
                }
                else { System.err.println("ошибка"); }
            }
        }
    }
}