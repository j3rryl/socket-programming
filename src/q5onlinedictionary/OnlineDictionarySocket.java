package q5onlinedictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class OnlineDictionarySocket {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 8080;
        String requestPath = "/dictionary/api/word?word=";

        try {
            // Create a socket connection to the server
            Socket socket = new Socket(serverHost, serverPort);

            // Get the input and output streams of the socket
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //User input
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the word you wish to search for: ");
            String searchQuery = inputReader.readLine();

            // Send the request to the server
            String request = "GET " + requestPath + searchQuery + " HTTP/1.1\r\n" +
                    "Host: " + serverHost + "\r\n\r\n";
            outputStream.write(request.getBytes());

            // Read and print the response from the server (skipping the headers)
            boolean headersFinished = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!headersFinished) {
                    if (line.isEmpty()) {
                        headersFinished = true;
                    }
                } else {
                    System.out.println(line);
                }
            }

            // Close the socket, input stream, and output stream
            reader.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

