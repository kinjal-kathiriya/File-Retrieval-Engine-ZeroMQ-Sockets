#ifndef SERVER_SIDE_ENGINE_H
#define SERVER_SIDE_ENGINE_H

#include <memory>

#include "IndexStore.hpp"

class ServerSideEngine {
    // TO-DO declare the ZMQProxy thread
    // TO-DO declare the server ZMQ context
    // TO-DO declare the index store
    std::shared_ptr<IndexStore> store;

    public:
        // constructor
        ServerSideEngine(std::shared_ptr<IndexStore> store);

        // default virtual destructor
        virtual ~ServerSideEngine() = default;

        void startZMQProxy(int serverPort, int numWorkers);

        void runZMQProxy();

        void runWorker();
        
        void stopZMQProxy();
};

#endif