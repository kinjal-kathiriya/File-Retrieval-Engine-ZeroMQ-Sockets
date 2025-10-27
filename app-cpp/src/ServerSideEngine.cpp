#include "ServerSideEngine.hpp"

ServerSideEngine::ServerSideEngine(std::shared_ptr<IndexStore> store) : store(store) {
    // TO-DO implement constructor
}

void ServerSideEngine::startZMQProxy(int serverPort, int numWorkers) {
    // TO-DO create the ZMQ context
    // TO-DO create the ZMQ Proxy thread
}

void ServerSideEngine::runZMQProxy() {
    // TO-DO create and bind ROUTER and DEALER ZMQ sockets
    // TO-DO create worker threads
    // TO-DO create ZMQ Proxy
    // TO-DO join worker threads
    // TO-DO close ROUTER and DEALER sockets and the context
}

void ServerSideEngine::runWorker() {
    // TO-DO create and connect REPLY ZMQ socket
    // TO-DO Receive INDEX or SEARCH messages
    // TO-DO Compute the reply (perform indexing or search)
    // TO-DO Send INDEX and SEARCH reply messages
}

void ServerSideEngine::stopZMQProxy() {
    // TO-DO send quit messages to the workers
    // TO-DO shutdown the context
    // TO-DO join the ZMQ Proxy thread
}