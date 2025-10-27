#include "ClientSideEngine.hpp"

ClientSideEngine::ClientSideEngine() {
    // TO-DO implement constructor
}

void ClientSideEngine::indexFiles() {
    // TO-DO crawl the files from the input folder
    // TO-DO for each file either read and send the words to the server,
    // or count the words and send the counted words to the server
}

void ClientSideEngine::searchFiles() {
    // TO-DO extract the terms from the query
    // for each term contact the server to retrieve the list of documents that contain the word
    // combine the results of a multi-term query
    // return top 10 results
}

void ClientSideEngine::openConnection() {
    // TO-DO create client ZMQ context
    // TO-DO create and connect client REQUEST ZMQ socket
}

void ClientSideEngine::closeConnection() {
    // TO-DO close client REQUEST ZMQ socket
}