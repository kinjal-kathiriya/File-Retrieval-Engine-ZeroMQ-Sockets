#ifndef CLIENT_SIDE_ENGINE_H
#define CLIENT_SIDE_ENGINE_H

class ClientSideEngine {
    // TO-DO declare the client ZMQ context
    // TO-DO declare the client REQUEST ZMQ socket

    public:
        // constructor
        ClientSideEngine();

        // default virtual destructor
        virtual ~ClientSideEngine() = default;

        void indexFiles();

        void searchFiles();
        
        void openConnection();

        void closeConnection();
};

#endif