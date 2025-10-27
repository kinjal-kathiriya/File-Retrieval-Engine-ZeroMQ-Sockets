#include <iostream>
#include <memory>

#include "IndexStore.hpp"
#include "ServerSideEngine.hpp"
#include "ServerAppInterface.hpp"

int main(int argc, char** argv)
{
    int serverPort = 1;
    int numWorkers = 1;

    // TO-DO initialize the server port and the number of workers from argv[1] and argv[2]

    std::shared_ptr<IndexStore> store = std::make_shared<IndexStore>();
    std::shared_ptr<ServerSideEngine> engine = std::make_shared<ServerSideEngine>(store);
    std::shared_ptr<ServerAppInterface> interface = std::make_shared<ServerAppInterface>(engine);

    // Start the ZMQProxy server that listens to all available interfaces and to the specified port,
    // and that uses multiple workers to compute the replies for multiple concurrent requests
    engine->startZMQProxy(serverPort, numWorkers);

    // Read commands from the user
    interface->readCommands();

    return 0;
}