package csc435.app;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZMQManager {
    public static void setupSockets() {
        // Create a ZContext object
        ZContext context = new ZContext();

        // Create and configure sockets
        ZMQ.Socket frontend = context.createSocket(ZMQ.ROUTER);
        frontend.bind("tcp://*:5555"); // Bind ROUTER socket to port 5555

        ZMQ.Socket backend = context.createSocket(ZMQ.DEALER);
        backend.bind("inproc://backend"); // Bind DEALER socket to in-process address

        // Add any other initialization or logic here

        // Don't forget to close the context when done
        context.close();
    }
}
