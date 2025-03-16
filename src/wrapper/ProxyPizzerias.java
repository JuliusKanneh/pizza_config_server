// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

/*
 File: OptionSet.java
 Author: Julius Aries Kanneh, Jr (jkanneh)
 Date: January 25, 2025,
 Description:

 Revision History:
 - Last update: February 28, 2025
 */

package wrapper;

import dao.MySQLPizzaConfigDAO;
import dao.PizzaConfigDAO;
import exception.*;
import io.PizzaConfigParser;
import model.PizzaConfig;
import utils.ConstantValues.UpdateOptionPriceRes;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ProxyPizzerias
{
    private Logger _logger;
//    private static final LinkedHashMap<String, PizzaConfig> configs = new LinkedHashMap<>();
    PizzaConfigDAO _pizzaConfigDAO;
    ExceptionFactory factory = new PizzeriaExceptionFactory();
    CustomException exception;

    public ProxyPizzerias(PizzaConfigDAO pizzaConfigDAO){
        _pizzaConfigDAO = pizzaConfigDAO;
    }

    public boolean createPizzeria(Properties configProperties) {
        PizzaConfig pizzaConfig = PizzaConfigParser.buildPizzaConfig(configProperties);
        return _pizzaConfigDAO.createPizzeria(pizzaConfig);
    }

    public synchronized void addOptionSet(String pizzeriaName, String optionSetName) {
        _pizzaConfigDAO.addOptionSet(pizzeriaName, optionSetName);
    }

    public void configurePizzeria(String filename) {
        _pizzaConfigDAO.configurePizzeriasFromTxtFile(filename);
    }

    public synchronized void printPizzeria(String pizzeriaName) {
        _pizzaConfigDAO.printPizzeria(pizzeriaName);
    }

    public synchronized void printAllPizzeria() {
        _pizzaConfigDAO.printAllPizzerias();
    }

    public synchronized ArrayList<String> getAllPizzeriaNames(){
        return _pizzaConfigDAO.getAllPizzeriaNames();
    }

    public synchronized ArrayList<String> getOptionSetNames(String pizzeriaName){
        return _pizzaConfigDAO.getOptionSetNames(pizzeriaName);
    }

    public synchronized PizzaConfig getPizzaConfig(String pizzeriaName) {
        return _pizzaConfigDAO.getPizzaConfig(pizzeriaName);
    }

    public synchronized boolean updateOptionSetName(String pizzeriaName, String optionSetName, String newName) {
        return _pizzaConfigDAO.updateOptionSetName(pizzeriaName, optionSetName, newName);
    }

    public synchronized boolean updateBasePrice(String pizzeriaName, double newPrice) {
        return _pizzaConfigDAO.updateBasePrice(pizzeriaName, newPrice);
    }


    public synchronized boolean updateOptionPrice(String pizzeriaName, String optionSetName, String optionName, double newPrice) {
       return _pizzaConfigDAO.updateOptionPrice(pizzeriaName, optionSetName, optionName, newPrice);
    }

    public synchronized boolean deletePizzeria(String pizzeriaName) {
        return _pizzaConfigDAO.deletePizzeria(pizzeriaName);
    }

    public synchronized boolean deleteOption(String pizzeriaName, String optionSetName, String optionName){
        return _pizzaConfigDAO.deleteOption(pizzeriaName, optionSetName, optionName);
    }

    public synchronized boolean deleteOptionSet(String pizzeriaName, String optionSetName) {
        return _pizzaConfigDAO.deleteOptionSet(pizzeriaName, optionSetName);
    }

    public void addPizzeria(PizzaConfig pizzaConfig) {

    }

    public boolean addOption(String pizzeriaName, String optionSetName, String optionName){
        return _pizzaConfigDAO.addOption(pizzeriaName, optionSetName, optionName);
    }

    public boolean addOption(String pizzeriaName, String optionSetName, String optionName, double price){
        return _pizzaConfigDAO.addOption(pizzeriaName, optionSetName, optionName, price);
    }

    public ArrayList<String> getOptionNames(String pizzeriaName, String optionSetName){
        return _pizzaConfigDAO.getOptions(pizzeriaName, optionSetName);
    }
}
