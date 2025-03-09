// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package wrapper;

import model.PizzaConfig;

import java.util.ArrayList;
import java.util.Properties;

public interface CreatePizzeria
{
    /**
     * Given a text file name, this method will build an instance of your pizzeria configuration. This method does not return the object.
     * For now, make this a stub (a printing to the console will suffice); you will implement file I/O in the next version of your API.
     *
     * @param filename the name of the flat .txt file with the configuration details of the pizzeria.
     */
    public void configurePizzeria(String filename);

    /**
     * Adds a pizzaConfig to the list of LHM
     *
     * @param pizzaConfig the reference to the pizzaConfig to be added to the LHM
     */
    public void addPizzeria(PizzaConfig pizzaConfig);

    /**
     * Given a config .properties file, this method parses the file, creates a PizzaConfig object from it, and add it to
     * the datastore (Linked Hash Map)
     *
     * @param configProperties the object of the properties file that is parsed to create a pizza configuration.
     */
    public boolean createPizzeria(Properties configProperties);

    /**
     * This method will get add an optionSet to an already existing pizzeria configuration.
     */
    public void addOptionSet(String pizzeriaName, String optionSetName);

    /**
     * This function searches the Model and prints the properties of a given pizzeria
     *
     * @param pizzeriaName the name of the pizzeria to be printed
     */
    public void printPizzeria(String pizzeriaName);

    public ArrayList<String> getAllPizzeriaNames();

    public ArrayList<String> getOptionSetNames(String pizzeriaName);

    public PizzaConfig getPizzaConfig(String pizzeriaName);

    public void printAllPizzeria();

}
