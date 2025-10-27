package csc435.app;

import org.zeromq.ZContext;

public class ProxyWorker implements Runnable {
    private String port;
    private Integer numWorkers;

    private ZContext context;

    public ProxyWorker(ZContext context, String port, int numWorkers) {
        this.context = context;
        this.port = port;
        this.numWorkers = numWorkers;
    }
    
    @Override
    public void run() {
        // TO-DO create and bind ROUTER and DEALER ZMQ sockets
        // TO-DO create worker threads
        // TO-DO create ZMQ Proxy
        // TO-DO join worker threads
        // TO-DO close ROUTER and DEALER sockets and the context
    }
}