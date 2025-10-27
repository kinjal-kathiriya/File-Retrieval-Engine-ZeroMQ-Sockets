package csc435.app;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class SocketProxy {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Create ROUTER socket and bind to a specific address
            ZMQ.Socket router = context.createSocket(ZMQ.ROUTER);
            router.bind("tcp://*:5555");

            // Create DEALER socket and bind to another address
            ZMQ.Socket dealer = context.createSocket(ZMQ.DEALER);
            dealer.bind("tcp://*:5556");

            // Set up a ZMQ proxy to route messages between ROUTER and DEALER
            ZMQ.proxy(router, dealer, null);
        }
    }
}