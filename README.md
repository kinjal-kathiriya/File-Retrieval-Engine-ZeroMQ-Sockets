# File-Retrieval-Engine-ZeroMQ-Sockets

### Requirements

To run the Java programs you will need to have Java 1.7.x and Maven 3.6.x installed on your systems. You will also need to install the JeroMQ (Java ZeroMQ) library and development jar. On Ubuntu 22.04 LTS you can install Java, Maven and JeroMQ using the following commands:


sudo apt install openjdk-17-jdk maven libjeromq-java



### Setup

There are 5 datasets (Dataset1, Dataset2, Dataset3, Dataset4, Dataset5) that you need to use to evaluate the indexing performance of your solution.
Before you can evaluate your solution you need to download the datasets. You can download the datasets from the following link:

https://depauledu-my.sharepoint.com/:f:/g/personal/aorhean_depaul_edu/EgmxmSiWjpVMi8r6QHovyYIB-XWjqOmQwuINCd9N_Ppnug?e=TLBF4V

After you finished downloading the datasets copy them to the dataset directory (create the directory if it does not exist).
Here is an example on how you can copy Dataset1 to the remote machine and how to unzip the dataset:


remote-computer$ mkdir datasets
local-computer$ scp Dataset1.zip cc@<remote-ip>:<path-to-repo>/datasets/.
remote-computer$ cd <path-to-repo>/datasets
remote-computer$ unzip Dataset1.zip


### Java solution
#### How to build/compile

To build the Java solution use the following commands:

cd app-java

mvn compile

mvn package


#### How to run application

To run the Java server (after you build the project) use the following command:

java -cp target/app-java-1.0-SNAPSHOT-jar-with-dependencies.jar csc435.app.FileRetrievalServer <port> <num worker threads>
> <quit>


To run the Java client (after you build the project) use the following command:

java -cp target/app-java-1.0-SNAPSHOT-jar-with-dependencies.jar csc435.app.FileRetrievalClient
> <connect | index | search | quit>


#### Example (2 clients and 1 server)

**Step 1:** start the server:

Server

java -cp target/app-java-1.0-SNAPSHOT-jar-with-dependencies.jar csc435.app.FileRetrievalServer 12345 2
>


**Step 2:** start the clients and connect them to the server:

Client 1

java -cp target/app-java-1.0-SNAPSHOT-jar-with-dependencies.jar csc435.app.FileRetrievalClient
> connect 1 127.0.0.1 12345
Connection successful!


Client 2

java -cp target/app-java-1.0-SNAPSHOT-jar-with-dependencies.jar csc435.app.FileRetrievalClient
> connect 2 127.0.0.1 12345
Connection successful!


**Step 3:** index files from the clients:

Client 1

> index /home/kinjal/datasets/Dataset1/folder1
Completed indexing in .101 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder3
Completed indexing in .102 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder5
Completed indexing in .113 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder7
Completed indexing in .107 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder9
Completed indexing in .101 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder11
Completed indexing in .101 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder13
Completed indexing in .101 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder15
Completed indexing in .100 seconds
Indexing Successful!
>



Client 2

> index /home/kinjal/datasets/Dataset1/folder2
Completed indexing in .135 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder4
Completed indexing in .112 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder6
Completed indexing in .103 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder8
Completed indexing in .105 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder10
Completed indexing in .104 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder12
Completed indexing in .131 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder14
Completed indexing in .135 seconds
Indexing Successful!
> index /home/kinjal/datasets/Dataset1/folder16
Completed indexing in .104 seconds
Indexing Successful!
> 


**Step 4:** search files from the clients:

Client 1

> search Worms
Sending message to server: Search Worms
Search completed in .015 seconds
Search results (top 10):
* client2:folder6/document200.txt 10
* client2:folder14/document417.txt 3
* client2:folder6/document424.txt 3
* client1:folder11/document79.txt 1
* client2:folder12/document316.txt 1
* client1:folder13/document272.txt 1
* client1:folder13/document38.txt 1
* client1:folder15/document351.txt 1
* client1:folder1/document260.txt 1
* client2:folder4/document101.txt 1

> quit



Client 2
```
> search distortion AND adaptation
Sending message to server: Search distortion AND adaptation
Search completed in .009 seconds
Search results (top 10):
client2:folder6/document200.txt 46
* client1:folder13/document38.txt 4
* client2:folder6/document408.txt 3
* client1:folder7/document298.txt 2
* client2:folder10/document107.txt 2
* client2:folder10/document27.txt 2
* client2:folder14/document145.txt 2
* client1:folder15/document351.txt 2
* client1:folder9/document77.txt 2
* client1:folder5/document216.txt 2

> quit



**Step 5:** close and disconnect the clients:

Client 1

> quit


Client 2

> quit


**Step 6:** close the server:

Server

> quit

