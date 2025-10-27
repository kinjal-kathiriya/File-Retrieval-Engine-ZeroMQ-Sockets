package csc435.app;

import java.util.Scanner;

public class ServerAppInterface {
    private ServerSideEngine engine;

    public ServerAppInterface(ServerSideEngine engine) {
        this.engine = engine;
    }

    public void readCommands() {
        Scanner sc = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("> ");
            command = sc.nextLine().trim().toLowerCase();

            if (command.equals("quit") || command.equals("stop")) {
                System.out.println("Server stopping...");
                engine.stopServer();
                System.out.println("Server stopped successfully.");
                break;
            }

            System.out.println("Unrecognized command!");
        }

        sc.close();
    }
}