// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package dao;

import model.PizzaConfig;

import java.util.ArrayList;
import java.util.List;

public interface PizzaConfigDAO
{
    boolean createPizzeria(PizzaConfig config);

    void configurePizzeriasFromTxtFile(String filename);

    void addOptionSet(String pizzeriaName, String optionSetName);

    void printPizzeria(String pizzeriaName);

    PizzaConfig getPizzaConfig(String pizzeriaName);

    ArrayList<String> getAllPizzeriaNames();

    void printAllPizzerias();

    boolean updatePizzeria(PizzaConfig config);

    boolean deletePizzeria(String pizzeriaName);

    ArrayList<String> getOptionSetNames(String pizzeriaName);

    boolean updateOptionSetName(String pizzeriaName, String optionSetName, String newName);

    boolean updateBasePrice(String pizzeriaName, double newPrice);

    boolean updateOptionPrice(String pizzeriaName, String optionSetName, String optionName, double newPrice);

    boolean deleteOption(String pizzeriaName, String optionSetName, String optionName);

    boolean deleteOptionSet(String pizzeriaName, String optionSetName);

    boolean addOption(String pizzeriaName, String optionSetName, String optionName);

    boolean addOption(String pizzeriaName, String optionSetName, String optionName, double price);

    ArrayList<String> getOptions(String pizzeriaName, String optionSetName);
}
