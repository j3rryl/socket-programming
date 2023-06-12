package q4mathudpserver;

import sampleexercises.MathService;
import sampleexercises.PlainMathService;

import java.net.*;

public class UDPServer {
    protected MathService mathService;
    protected static PlainMathService ps = new PlainMathService();
    public static void main(String[] args) throws Exception {

        // Create a UDP socket
        DatagramSocket serverSocket = new DatagramSocket(9876);

        byte[] receiveData = new byte[1024];
        byte[] sendData;

        while (true) {
            // Receive data from the client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            // Extract the received data from the packet
            String receivedData = new String(receivePacket.getData()).trim();

            // Split the received data into operation and numbers
            String[] parts = receivedData.split(";");
            String operation = parts[0];
            String[] numbers = parts[1].split(",");

            // Perform the requested operation
            double result = 0;
            double firstValue = Double.parseDouble(numbers[0]);
            double secondValue = Double.parseDouble(numbers[1]);
            switch (operation) {
                case "+":
                    result = ps.add(firstValue, secondValue);
                    break;
                case "-":
                    result = ps.sub(firstValue, secondValue);
                    break;
                case "*":
                    result = ps.mul(firstValue, secondValue);
                    break;
                case "/":
                    result = ps.div(firstValue, secondValue);
                    break;
                default:
                    result = 0;
                    break;
            }

            // Prepare the response
            sendData = String.valueOf(result).getBytes();

            // Get the client's IP address and port
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            // Create a packet with the response data and send it back to the client
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}


