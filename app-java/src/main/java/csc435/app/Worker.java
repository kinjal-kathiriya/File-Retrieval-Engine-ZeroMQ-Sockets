package csc435.app;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;
import org.zeromq.SocketType;


public class Worker implements Runnable {
    private IndexStore store;
    private ZContext context;

    public Worker(IndexStore store, ZContext context) {
        this.store = store;
        this.context = context;
    }

    @Override
    public void run() {
        ZMQ.Socket workerSocket = context.createSocket(ZMQ.REP);

        try {
            // Connect to the DEALER socket
            workerSocket.connect("inproc://backend");

            while (!Thread.currentThread().isInterrupted()) {
                // Receive message from DEALER socket
                String request = workerSocket.recvStr(0).trim();
                System.out.println("Received request: " + request); // Debugging statement
                String response = "";

                // Process the request
                if (request.startsWith("Index")) {
                    // Indexing operation
                    String data = request.substring(6); // Remove "INDEX " prefix
                    store.indexData(data);
                    response = "Index Successfully";
                } else if (request.startsWith("Search")) {
                    // Search operation
                    String data = request.substring(7); // Remove "SEARCH " prefix
                    String result = store.searchData(data);
                    response = result != null ? result : "not found";
                } else if (request.equals("quit")) {
                    // Terminate the worker thread
                    break;
                } else {
                    response = "Invalid Request";
                }

                // Send the response back to the client
                workerSocket.send(response, 0);
                System.out.println("Sent response: " + response); // Debugging statement
            }
        } catch (ZMQException e) {
            if (e.getErrorCode() != ZMQ.Error.ETERM.getCode()) {
                e.printStackTrace();
            }
        } finally {
            workerSocket.close();
        }
    }
}
