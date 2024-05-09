package com.company.model.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

    private ArrayList<InetAddress> IP;
    private ArrayList<Integer> Ports;
    private DatagramSocket server;
    private ArrayList<RoomThread> Rooms;


    public Server() throws IOException {
        server = new DatagramSocket(9876);

        IP = new ArrayList<InetAddress>();
        Ports = new ArrayList<Integer>();
        Rooms = new ArrayList<RoomThread>();




        while (true) {
            byte[] receiveData = new byte[1];
            byte[] sendData = new byte[1];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            server.receive(receivePacket);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            IP.add(IPAddress);
            Ports.add(port);
            System.out.println(IPAddress);
            System.out.println(port);

            byte[] message = receivePacket.getData();


            int q = 0;
            if(message[0] == 1)
            {
                DatagramSocket Room = new DatagramSocket();

                RoomThread ct = new RoomThread(Room, receivePacket.getAddress(), receivePacket.getPort());

                Rooms.add(ct);

                ct.start();
            }
            else if(message[0] == 2)
            {
                for(int i = 0;i < Rooms.size();i++)
                {
                    if(Rooms.get(i).getPlayersCount() == 1)
                    {
                        System.out.println("Есть свободная комната");
                        Rooms.get(i).setIpPlayer2(receivePacket.getAddress());
                        Rooms.get(i).setPortPlayer2(receivePacket.getPort());

                        System.out.println(receivePacket.getAddress());
                        System.out.println(receivePacket.getPort());


                        Rooms.get(i).secondPlayerConnected();
                        q = 1;
                        break;
                    }
                }
                if(q == 0) {
                    System.out.println("Нет свободных комнат");
                    q = 1;
                }
            }
            /*else if(message[0] == 5)
            {
                System.out.println("так так так... а вот и наш оппонент");
            }*/



        }
    }




    private class RoomThread extends Thread{
        private DatagramSocket roomSocket;
        private int playersCount = 1;


        private InetAddress IpPlayer1;
        private InetAddress IpPlayer2;
        private int portPlayer1;
        private int portPlayer2;
        private int portOpponent1;
        private int portOpponent2;


        public int getPlayersCount()
        {
            return playersCount;
        }

        public void setIpPlayer2(InetAddress ip)
        {
            IpPlayer2 = ip;
        }
        public void setPortPlayer2(int p)
        {
            portPlayer2 = p;
        }


        public RoomThread(DatagramSocket s, InetAddress ip, int p)
        {
            roomSocket = s;
            IpPlayer1 = ip;
            portPlayer1 = p;
            byte[] sendData = new byte[1];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpPlayer1, portPlayer1);
            try {
                roomSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        private void secondPlayerConnected()
        {
            System.out.println("второй игрок подключился");
            byte[] sendData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpPlayer2, portPlayer2);
            try {
                roomSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            playersCount = 2;
        }

        public void run()
        {
            while (true) {
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    System.out.println("жду пакет");
                    roomSocket.receive(receivePacket);
                    System.out.println("получил пакет");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] message = receivePacket.getData();

                if(message[0] == 5)
                {
                    portOpponent1 = receivePacket.getPort();
                    //System.out.println("ну ка ну ка ну ка, что у нас здесь ?");

                }
                else if(message[0] == 6)
                {
                    portOpponent2 = receivePacket.getPort();
                }
                else {
                    sendData = receivePacket.getData();

                    if (receivePacket.getPort() == portPlayer1 || receivePacket.getAddress() == IpPlayer1) {
                        //System.out.println("И ВОТ МЫ ЗДЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕСЬ!!!!!!!!!!!!!!!!!!!");
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpPlayer2, portOpponent2);
                        try {
                            System.out.println("отправляю пакет второму игроку");
                            roomSocket.send(sendPacket);
                            System.out.println("отправил пакет второму игроку");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (receivePacket.getPort() == portPlayer2 || receivePacket.getAddress() == IpPlayer2) {
                        //System.out.println("И ВОТ МЫ ЗДЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕЕСЬ!!!!!!!!!!!!!!!!!!!");
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpPlayer1, portOpponent1);
                        try {
                            System.out.println("отправляю пакет первому игроку");
                            roomSocket.send(sendPacket);
                            System.out.println("отправил пакет первому игроку");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("***********************************************");
                    System.out.println(receivePacket.getPort());
                    System.out.println(receivePacket.getAddress());

                    System.out.println(portPlayer1);
                    System.out.println(IpPlayer1);

                    System.out.println(portPlayer2);
                    System.out.println(IpPlayer2);
                    System.out.println("***********************************************");
                }
            }
        }

    }
}
