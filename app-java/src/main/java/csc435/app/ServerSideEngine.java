package csc435.app;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

public class ServerSideEngine {
    private Thread zmqProxyThread;
    private ZContext context;
    private IndexStore store;
    private boolean running;
    private ZMQ.Socket frontend;
    private ZMQ.Socket backend;
    private ZMQ.Socket terminationSocket;

    public ServerSideEngine(IndexStore store) {
        this.store = store;
        this.context = new ZContext();
        this.running = false;
    }

    public void startZMQProxy(int serverPort, int numWorkers) {
        running = true;
        zmqProxyThread = new Thread(() -> {
            try {
                frontend = context.createSocket(ZMQ.ROUTER);
                frontend.bind("tcp://*:" + serverPort);
                System.out.println("Frontend socket bound to port: " + serverPort);

                backend = context.createSocket(ZMQ.DEALER);
                backend.bind("inproc://backend");
                System.out.println("Backend socket bound to inproc://backend");

                terminationSocket = context.createSocket(ZMQ.PAIR);
                terminationSocket.bind("inproc://termination");
                System.out.println("Termination socket bound to inproc://termination");

                Thread[] workers = new Thread[numWorkers];
                for (int i = 0; i < numWorkers; i++) {
                    workers[i] = new Thread(new Worker(store, context));
                    workers[i].start();
                }

                ZMQ.Poller poller = context.createPoller(3);
                poller.register(frontend, ZMQ.Poller.POLLIN);
                poller.register(backend, ZMQ.Poller.POLLIN);
                poller.register(terminationSocket, ZMQ.Poller.POLLIN);

                while (running) {
                    int events = poller.poll();
                    if (events == -1) break;

                    if (poller.pollin(0)) {
                        ZMQ.proxy(frontend, backend, null);
                    }
                    if (poller.pollin(2)) {
                        terminationSocket.recv(0);
                        System.out.println("Received termination signal");
                        break;
                    }
                }

                for (Thread worker : workers) {
                    worker.interrupt();
                    worker.join();
                }

                frontend.close();
                backend.close();
                terminationSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                context.close();
            }
        });
        zmqProxyThread.start();
    }

    public void stopServer() {
        System.out.println("Stopping server...");
        running = false;

        try (ZMQ.Socket sender = context.createSocket(ZMQ.PAIR)) {
            sender.connect("inproc://termination");
            sender.send("", 0);
            System.out.println("Termination signal sent");
        }

        try {
            zmqProxyThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        closeSockets();
        closeContext();

        System.out.println("Server stopped successfully.");
    }

    private void closeSockets() {
        if (frontend != null) {
            frontend.close();
        }
        if (backend != null) {
            backend.close();
        }
    }

    private void closeContext() {
        if (context != null) {
            context.close();
        }
    }
}



