package csc435.app;

import java.util.Scanner;
import java.io.File;

public class ClientAppInterface {
    private ClientSideEngine engine;

    public ClientAppInterface(ClientSideEngine engine) {
        this.engine = engine;
    }

    public void readCommands() {
        Scanner sc = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("> ");
            command = sc.nextLine();

            if (command.equals("quit")) {
                engine.closeConnection();
                System.out.println("Client disconnected.");
                break;
            }

            if (command.startsWith("connect")) {
                String[] tokens = command.split(" ");
                if (tokens.length == 4) {
                    String clientId = tokens[1];
                    String serverAddress = tokens[2];
                    int serverPort = Integer.parseInt(tokens[3]);
                    if (engine.openConnection(clientId, serverAddress, serverPort)) {
                        System.out.println("Connection successful!");
                    } else {
                        System.out.println("Connection failed!");
                    }
                } else {
                    System.out.println("Invalid connect command format. Usage: connect <clientId> <serverAddress> <serverPort>");
                }
                continue;
            }

            if (command.startsWith("index")) {
                String[] tokens = command.split(" ");
                if (tokens.length == 2) {
                    String folderPath = tokens[1];
                    File folder = new File(folderPath);
                    if (folder.exists() && folder.isDirectory()) {
                        engine.indexFolder(folderPath);
                    } else {
                        System.out.println("Invalid folder path or folder does not exist.");
                    }
                } else {
                    System.out.println("Invalid index command format. Usage: index <folderPath>");
                }
                continue;
            }
            if (command.startsWith("search")) {
    String[] tokens = command.split(" ", 2);
    if (tokens.length == 2) {
        String query = tokens[1];
        engine.searchFiles(query);
    } else {
        System.out.println("Invalid search command format. Usage: search <query>");
    }
    continue;
}
            System.out.println("Unrecognized command!");
        }

        sc.close();
    }
}