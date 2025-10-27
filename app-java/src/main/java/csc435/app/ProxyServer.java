package csc435.app;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ProxyServer {
    public static void main(String[] args) {
        // Create a ZContext object
        try (ZContext context = new ZContext()) {
            // Create a frontend socket to accept connections from clients
            ZMQ.Socket frontend = context.createSocket(ZMQ.ROUTER);
            frontend.bind("tcp://*:5559");

            // Create a backend socket to connect to workers
            ZMQ.Socket backend = context.createSocket(ZMQ.DEALER);
            backend.bind("tcp://*:5560");

            // Start the proxy
            ZMQ.proxy(frontend, backend, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}