package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public interface PizzeriaInterface
{
    Socket acceptConnection(ServerSocket serverSocket);
    ServerSocket openSocket(int portNumber, int acceptTimeout);
    void runServer() throws IOException;
}
