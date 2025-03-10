// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

import dao.LocalDataStoreDAO;
import dao.MySQLPizzaConfigDAO;
import dao.PizzaConfigDAO;
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

        PizzaConfigDAO dao = null;
        try {
            while (dao == null) {
                dao = selectDao();
            }
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }

        PizzeriaServer server = (acceptTimeout != -1) ? new PizzeriaServer(portNumber, acceptTimeout, dao) : new PizzeriaServer(portNumber, dao);
        try {
            server.runServer();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }
    }

    private static PizzaConfigDAO selectDao() throws IOException {
        System.out.println("Please select the datastore you would like your server to run on: ");
        System.out.println("1. Local Datastore using Linked Hash Map");
        System.out.println("2. My SQL database connection");
        Integer choice = UtilMethods.readFromKeyboard("Please enter your option: ", Integer.class);
        return switch (choice) {
            case 1 -> new LocalDataStoreDAO();
            case 2 -> new MySQLPizzaConfigDAO();
            default -> null;
        };
    }
}
