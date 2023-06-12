package q4mathudpserver;

import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        // Create a UDP socket
        DatagramSocket clientSocket = new DatagramSocket();

        InetAddress IPAddress = InetAddress.getByName("localhost");
        int port = 9876;

        // Get the operation and numbers from the user
        String operation = "*";
        double num1 = 10.23;
        double num2 = 50;

        // Prepare the data to send
        String requestData = operation + ";" + num1 + "," + num2;
        byte[] sendData = requestData.getBytes();

        // Create a packet with the data and send it to the server
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(sendPacket);

        byte[] receiveData = new byte[1024];

        // Receive the response from the server
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);

        // Extract the result from the response
        double result = Double.parseDouble(new String(receivePacket.getData()).trim());

        System.out.println("Result: " + result);

        // Close the socket
        clientSocket.close();
    }
}
