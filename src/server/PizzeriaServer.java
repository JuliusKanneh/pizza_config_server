// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package server;

import networking.requests.CustomRequest;
import protocol.PizzeriaProtocol;
import protocol.PizzeriaProtocolFactory;
import protocol.ProtocolFactory;
import wrapper.PizzeriaConfigAPI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzeriaServer implements PizzeriaInterface
{
    private final Logger _logger;
    int _portNumber;
    int _acceptTimeout;
    ServerSocket _serverSocket;
    Socket _clientSocket;
    ClientHandler _clientHandler;
    private static PizzeriaConfigAPI _api;

    public static boolean KEEP_RUNNING = true;

    public PizzeriaServer(int portNumber) {
        _logger = Logger.getLogger(this.getClass().getName());
        _portNumber = portNumber;
        _acceptTimeout = -1; //-1 means there is no timeout.
        _serverSocket = null;
        _clientSocket = null;
        _clientHandler = null;
        _api = new PizzeriaConfigAPI();
    }

    public PizzeriaServer(int portNumber, int acceptTimeout) {
        _logger = Logger.getLogger(this.getClass().getName());
        _portNumber = portNumber;
        _acceptTimeout = acceptTimeout; //-1 means there is no timeout.
        _serverSocket = null;
        _clientSocket = null;
        _clientHandler = null;
    }

    @Override
    public Socket acceptConnection(ServerSocket serverSocket) {
        try{
            Socket clientSocket = serverSocket.accept();
            _logger.log(Level.INFO, "Server connected to " + clientSocket);
            return clientSocket;
        }catch (IOException e){
            _logger.log(Level.SEVERE, "Server caught exception when trying to listen on port " + serverSocket.getLocalPort() + "\n" + e);
        }
        return null;
    }

    @Override
    public ServerSocket openSocket(int portNumber, int acceptTimeout) {
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(_portNumber);
            if (acceptTimeout > 0){
                serverSocket.setSoTimeout(acceptTimeout);
            }
        }catch (BindException e){
            _logger.log(Level.SEVERE, "Server cannot bind to port " + _logger + "\n" + e);
            System.exit(3);
        }catch (Exception e){
            _logger.log(Level.SEVERE, "Server caught exception when trying to create socket ");
            System.err.println("Server caught exception when trying to create socket ");
        }
        return serverSocket;
    }

    @Override
    public void runServer() throws IOException {
        if ((_serverSocket = openSocket(_portNumber, _acceptTimeout)) == null) {
            System.exit(2);
        }

        _logger.log(Level.INFO, "Timeout: " + _serverSocket.getSoTimeout());

        while (KEEP_RUNNING) {
            _clientSocket = acceptConnection(_serverSocket);
            if (_clientSocket != null) {
                ObjectOutputStream responseStream = new ObjectOutputStream(_clientSocket.getOutputStream());
                ObjectInputStream requestStream = new ObjectInputStream(_clientSocket.getInputStream());
                try {
                    CustomRequest request = (CustomRequest) requestStream.readObject();

                    ProtocolFactory protocolFactory = new PizzeriaProtocolFactory(_api, request);
                    PizzeriaProtocol protocol = protocolFactory.createProtocol(request);
                    _clientHandler = new ClientHandler(protocol, responseStream); //creates a new thread
                    _clientHandler.start();
                    System.out.println("Server now handling " + _clientHandler.getName());
                }
                catch (ClassNotFoundException e) {
                    _logger.log(Level.SEVERE, "Error: " + e.getMessage());
                }
            }
        }

        _logger.log(Level.INFO, "The server is ending.");
    }
}
