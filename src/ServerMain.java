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

//TODO: after starting the server, establish a connection to the datastore as per the type of of the dao provided.
//if it is a database connection, establish the connection before proceeding to any interaction. Don't allow the user to continue
//if the connection was not successful.
public class ServerMain
{
    private final static Logger _LOGGER = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) {
        int portNumber = ConstantValues.DEFAULT_PORT;
        int acceptTimeout = -1;

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
            _LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }

        PizzeriaServer server = (acceptTimeout != -1) ? new PizzeriaServer(portNumber, acceptTimeout, dao) : new PizzeriaServer(portNumber, dao);
        try {
            server.runServer();
        }
        catch (IOException e) {
            _LOGGER.log(Level.SEVERE, "ERROR: " + e.getMessage());
        }
    }

    private static PizzaConfigDAO selectDao() throws IOException {
        System.out.println("Please select the datastore you would like your server to run on: ");
        System.out.println("1. Local Datastore using Linked Hash Map");
        System.out.println("2. My SQL database connection");
        Integer choice = UtilMethods.readFromKeyboard("Please enter your option: ", Integer.class);
        return switch (choice) {
            case 1 -> new LocalDataStoreDAO();
            case 2 -> createMySqlDataStore();
            default -> null;
        };
    }

    private static PizzaConfigDAO createMySqlDataStore() {
        String dbName = null;
        String dbUrl = null;
        String dbUser = null;
        String dbPassword = null;

        while (dbName == null) {
            try {
                dbName = UtilMethods.readFromKeyboard("Enter database name: ", String.class);
            }
            catch (IOException e) {
                _LOGGER.log(Level.SEVERE, "Error reading database name: " + e.getMessage() + "\nPlease try again.");
            }
        }

        while (dbUrl == null) {
            try {
                dbUrl = UtilMethods.readFromKeyboard("Enter database url: ", String.class);
            }
            catch (IOException e) {
                _LOGGER.log(Level.SEVERE, "Error reading database url: " + e.getMessage() + "\nPlease try again.");
            }
        }

        while (dbUser == null) {
            try {
                dbUser = UtilMethods.readFromKeyboard("Enter database username: ", String.class);
            }
            catch (IOException e) {
                _LOGGER.log(Level.SEVERE, "Error reading database username: " + e.getMessage() + "\nPlease try again.");
            }
        }

        while (dbPassword == null) {
            try {
                dbPassword = UtilMethods.readFromKeyboard("Enter database password (leave blank if your database does not have a password): ", String.class);
            }
            catch (IOException e) {
                _LOGGER.log(Level.SEVERE, "Error reading database password: " + e.getMessage() + "\nPlease try again.");
            }
        }

        return new MySQLPizzaConfigDAO(dbName, dbUrl, dbUser, dbPassword);
    }
}
