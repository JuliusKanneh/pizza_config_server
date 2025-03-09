// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package server;

import networking.response.CustomResponse;
import protocol.PizzeriaProtocol;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread
{
    private static int CLIENT_NUMBER = 0;
    private final PizzeriaProtocol _protocol;
    private final ObjectOutputStream _out;
    private final Logger _logger;

    ClientHandler(PizzeriaProtocol protocol, ObjectOutputStream out) throws IOException {
        _protocol = protocol;
        _out = out;
        CLIENT_NUMBER++;
        _logger = Logger.getLogger(ClientHandler.class.getName());
        setName("Client " + CLIENT_NUMBER);
    }

    @Override
    public void run() {
        try{
            CustomResponse response = _protocol.handleRequest();
            _out.writeObject(response);
            _out.flush();
            _logger.log(Level.INFO, "Server finished handling " + Thread.currentThread().getName());
        }catch (IOException e){
            _logger.log(Level.SEVERE, "Exception caught when trying to read requests for client " + Thread.currentThread().getName(), e);
        }
    }
}
