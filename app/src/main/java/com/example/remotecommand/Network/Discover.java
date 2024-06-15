package com.example.remotecommand.Network;

import static com.example.remotecommand.MainActivity.host_ip;

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Discover implements Runnable {
    public static void discoverServer() {
        int portNumber = 54321; // Use the same port for receiving broadcasts


        while (true) {
            try {

                DatagramSocket socket = new DatagramSocket(portNumber);
                byte[] receiveData = new byte[1024];
                // Listen for broadcasts
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(packet);
                Log.d("IP", "Ready to receive broadcast packets!");
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                Log.d("IP", "Received IP address: " + receivedMessage);

                host_ip = receivedMessage;

                socket.close();
            } catch (Exception e) {
//            e.printStackTrace();
                Log.d("IP", "Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        discoverServer();
    }
}
