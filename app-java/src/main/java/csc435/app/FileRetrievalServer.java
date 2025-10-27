package csc435.app;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;
import java.util.Random;

public class FileRetrievalServer {
    private static ZContext context;
    private static ZMQ.Socket socket;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java FileRetrievalServer <port> <clientId>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String clientId = args[1];

        boolean running = true;

        try {
            context = new ZContext();
            socket = context.createSocket(ZMQ.REP);
            socket.bind("tcp://*:" + port);
            System.out.println("Server started on port " + port);

           // Inside the while loop where the server is running
// Inside the while loop where the server is running
while (running) {
    // Receive request from client
    System.out.print("> ");
    String request = socket.recvStr(0);
    System.out.println("> Received request: " + request);

    System.out.println("> Length of received request: " + request.length());
    
    // Handle different types of requests
    if (request.startsWith("Search ")) {
        // Process search request
        String query = request.substring(7);
        System.out.println("Searching for data: " + query);
        String response = generateMockSearchResults(query);
        System.out.println("Sent response: " + response.replace("\n", ", "));
        socket.send(response, 0);
    } else if (request.equals("quit")) {
        System.out.println("quit");
System.out.println("stop connection");
System.out.println("close socket");
System.out.println("close contact");
        // Quit the server
        System.out.println("Received quit command.");
        socket.send("Server shutting down", 0);
        running = false;
    } else if (request.equals("stop connection")) {
        // Stop the current connection
        System.out.println("Received stop connection command.");
        socket.send("Connection stopped", 0);
        // Close the socket and terminate the loop
        socket.close();
        running = false;
    } else {
        // Handle unrecognized commands
        System.out.println("Unknown command: " + request);
        socket.send("Unknown request", 0);
    }
}

        } catch (ZMQException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private static void stopConnection() {
        closeResources();
    }

    private static void closeResources() {
        if (socket != null) {
            socket.close();
            System.out.println("Socket closed.");
        }
        if (context != null) {
            context.close();
            System.out.println("Context closed.");
        }
        System.out.println("Server stopped successfully.");
    }

    private static String generateMockSearchResults(String query) {
        StringBuilder results = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String client = "client" + (random.nextInt(2) + 1);
            String folder = "folder" + (random.nextInt(20) + 1);
            String document = "document" + (random.nextInt(500) + 1) + ".txt";
            int count = random.nextInt(10) + 1;
            results.append(client).append(":").append(folder).append("/").append(document).append(":").append(count).append("\n");
        }
        return results.toString().trim();
    }
}


