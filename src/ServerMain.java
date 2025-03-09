// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

import server.PizzeriaServer;
import utils.ConstantValues;
import utils.UtilMethods;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.ConstantValues.OperationType.DEFAULT_PORT;

public class ServerMain
{
    public static void main(String[] args) {
        int portNumber = ConstantValues.DEFAULT_PORT;
        int acceptTimeout = -1;

        Logger LOGGER = Logger.getLogger(ServerMain.class.getName());

        if (args.length == 2) {
            portNumber = Integer.parseInt(args[0]);
            acceptTimeout = Integer.parseInt(args[1]);
        } else if (args.length != 1) {
            UtilMethods.showUsage(DEFAULT_PORT);
        }


        PizzeriaServer server = (acceptTimeout != -1) ? new PizzeriaServer(portNumber, acceptTimeout) : new PizzeriaServer(portNumber);
        try {
            server.runServer();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }
    }
}
