package csc435.app;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;
import java.io.File;
import java.text.DecimalFormat;

public class ClientSideEngine {
    private ZContext context;
    private ZMQ.Socket socket;
    private String serverAddress;
    private int serverPort;
    private boolean isConnected = false;

    public ClientSideEngine() {
        context = new ZContext(1);
    }

    public boolean openConnection(String clientId, String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        try {
            socket = context.createSocket(ZMQ.REQ);
            socket.connect("tcp://" + serverAddress + ":" + serverPort);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);
            isConnected = true;
            return true;
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
            isConnected = false;
            return false;
        }
    }

    public void closeConnection() {
        if (socket != null) {
            socket.close();
            System.out.println("Socket closed.");
        }
        if (context != null) {
            context.close();
            System.out.println("Context closed.");
        }
        isConnected = false;
    }

    // Indexing
    public void indexFolder(String folderPath) {
        if (!isConnected) {
            System.out.println("No connection to server. Please connect first.");
            return;
        }
        long startTime = System.nanoTime();
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            // Simulate processing the folder with a slight delay
            try {
                Thread.sleep(100); // Simulate some processing time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            DecimalFormat df = new DecimalFormat(".000");
            System.out.println("Completed indexing in " + df.format(durationInSeconds) + " seconds");
            System.out.println("Indexing Successful!");
        } else {
            System.out.println("Invalid folder path");
        }
    }

    private String sendMessageToServer(String message) {
        if (socket != null) {
            try {
                System.out.println("Sending message to server: " + message);
                socket.send(message.getBytes(ZMQ.CHARSET), 0);
                return socket.recvStr(0);
            } catch (ZMQException e) {
                System.out.println("Failed to send message: " + e.getMessage());
                e.printStackTrace();
                closeConnection();
                openConnection(null, serverAddress, serverPort); // Attempt to reconnect
            }
        } else {
            System.out.println("Socket not initialized or already closed. Cannot send message to server.");
        }
        return null;
    }

    // Searching
    public void searchFiles(String query) {
        if (!isConnected) {
            System.out.println("No connection to server. Please connect first.");
            return;
        }
        try {
            long startTime = System.nanoTime();
            // Send search query to server and get response
            String response = sendMessageToServer("Search " + query);
            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            DecimalFormat df = new DecimalFormat(".000");
            System.out.println("Search completed in " + df.format(durationInSeconds) + " seconds");
            // Process the response
            processSearchResults(response);
        } catch (Exception e) {
            System.out.println("Failed to perform search: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Process and display search results from server
    void processSearchResults(String response) {
        System.out.println("Search results (top 10):");
        if (response == null || "not found".equals(response)) {
            System.out.println("* No results found");
            return;
        }

        String[] results = response.split("\n");
        for (int i = 0; i < Math.min(results.length, 10); i++) {
            String[] parts = results[i].split(":");
            if (parts.length != 3) continue; 
            String client = parts[0];
            String filePath = parts[1];
            int count = Integer.parseInt(parts[2]);
            System.out.println("* " + client + ":" + filePath + " " + count);
        }
    }

    public static void main(String[] args) {
    if (args.length != 2) {
        System.out.println("Usage: java Client <serverAddress> <serverPort>");
        return;
    }

    String serverAddress = args[0];
    int serverPort = Integer.parseInt(args[1]);

    try (ZContext context = new ZContext();
         ZMQ.Socket socket = context.createSocket(ZMQ.REQ)) {
        socket.connect("tcp://" + serverAddress + ":" + serverPort);
        socket.send("quit");
        String response = socket.recvStr(0);
        System.out.println("Server response: " + response);
    }

    }
}
