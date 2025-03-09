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

import exception.*;
import io.PizzaConfigParser;
import model.PizzaConfig;
import utils.ConstantValues.UpdateOptionPriceRes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ProxyPizzerias
{
    private Logger _logger;

    private static LinkedHashMap<String, PizzaConfig> configs = new LinkedHashMap<>();

    ExceptionFactory factory = new PizzeriaExceptionFactory();
    CustomException exception;

    ProxyPizzerias(){
        _logger = Logger.getLogger(this.getClass().getName());
    }

    public boolean createPizzeria(Properties configProperties) {
        PizzaConfig pizzaConfig = PizzaConfigParser.buildPizzaConfig(configProperties);

        if (pizzaConfig == null){
            return false;
        }

        if(configs.containsKey(pizzaConfig.getConfigName())){
            configs.replace(pizzaConfig.getConfigName(), pizzaConfig);
        }else{
            configs.put(pizzaConfig.getConfigName(), pizzaConfig);
        }
        return true;
    }

    public void addPizzeria(PizzaConfig pizzaConfig) {
        PizzaConfig foundPizzaConfig = configs.get(pizzaConfig.getConfigName());
        if (foundPizzaConfig != null) {
            Map<String, Object> params = Map.of("foundPizzaConfig", foundPizzaConfig,
                    "newPizzaConfig", pizzaConfig);
            exception = factory.createException(ExceptionType.BASE_PRICE_MISSING, params);
            exception.fix();
        }

        configs.put(pizzaConfig.getConfigName(), pizzaConfig);
    }

    public synchronized void addOptionSet(String pizzeriaName, String optionSetName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            pizzaConfig = new PizzaConfig();
            Map<String, Object> params = Map.of("pizzaConfig", pizzaConfig,
                    "pizzeriaName", pizzeriaName);
            exception = factory.createException(ExceptionType.BASE_PRICE_MISSING, params);
            exception.fix();
        }
        pizzaConfig.addOptionSet(optionSetName);
    }

    public void configurePizzeria(String filename) {
        boolean success = PizzaConfigParser.buildPizzaConfig(filename, (PizzeriaConfigAPI) this);
        if(!success){
            _logger.log(Level.SEVERE, "Building Pizzeria failed! Self-healing method couldn't fix the problem.");
        }
    }

    public synchronized void printPizzeria(String pizzeriaName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);

        if (pizzaConfig == null) {
            _logger.log(Level.SEVERE, "Pizzeria '" + pizzeriaName + "' not found!");
        } else {
            pizzaConfig.print();
        }
    }

    public synchronized void printAllPizzeria() {
        if(configs.isEmpty())
            _logger.log(Level.INFO, "No Pizzeria exits.");

        for (PizzaConfig pizzaConfig : configs.values()) {
            pizzaConfig.print();
        }
    }

    public synchronized ArrayList<String> getAllPizzeriaNames(){
        ArrayList<String> pizzeriaNames = new ArrayList<>();
        for (Map.Entry<String, PizzaConfig> entry : configs.entrySet()) {
            pizzeriaNames.add(entry.getKey());
        }
        return pizzeriaNames;
    }

    public synchronized ArrayList<String> getOptionSetNames(String pizzeriaName){
        ArrayList<String> optionSetNames = new ArrayList<>();
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            return optionSetNames;
        }

        return pizzaConfig.getOptionSetNames();
    }

    public synchronized PizzaConfig getPizzaConfig(String pizzeriaName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "Pizzeria '" + pizzeriaName + "' does not exist!");
            return null;
        }
        return configs.get(pizzeriaName);
    }

    public synchronized boolean updateOptionSetName(String pizzeriaName, String optionSetName, String newName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "Pizzeria '" + pizzeriaName + "' does not exist!");
            return false;
            //TODO: a better approach could be to prompt the user to decide whether you should create a new pizza config and create a new option set or not.
        }

        boolean success = pizzaConfig.updateOptionSetName(optionSetName, newName);
        try {
            if (!success) {
                throw new OptionSetNotFoundException(pizzaConfig, optionSetName);
            }
        }catch (CustomException e){
            e.fix();
        }
        return true;
    }

    public synchronized boolean updateBasePrice(String pizzeriaName, double newPrice) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);

        if (pizzaConfig == null) {
            pizzaConfig = new PizzaConfig();
            Map<String, Object> params = Map.of("pizzaConfig", pizzaConfig, "pizzeriaName", pizzeriaName);
            exception = factory.createException(ExceptionType.BASE_PRICE_MISSING, params);
            exception.fix();
        }


        pizzaConfig.updateBasePrice(newPrice);
        return true;
    }


    public synchronized boolean updateOptionPrice(String pizzeriaName, String optionSetName, String optionName, double newPrice) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "Pizzeria '" + pizzeriaName + "' does not exist!");
            return false;
            //TODO: a better approach could be to prompt the user to decide whether you should create a new pizza config and create a new option set or not.
        }

        UpdateOptionPriceRes res = pizzaConfig.updateOptionPrice(optionSetName, optionName, newPrice);
        while (res != UpdateOptionPriceRes.SUCCESS) {
            if (res == UpdateOptionPriceRes.OPTION_SET_NOT_FOUND) {
                Map<String, Object> params = Map.of("pizzaConfig", pizzaConfig, "optionSetName", optionSetName);
                exception = factory.createException(ExceptionType.OPTION_SET_NOT_FOUND, params);
                exception.fix();
            } else if (res == UpdateOptionPriceRes.OPTION_NOT_FOUND) {
                Map<String, Object> params = Map.of("pizzaConfig", pizzaConfig, "optionName", optionName);
                exception = factory.createException(ExceptionType.OPTION_SET_NOT_FOUND, params);
                exception.fix();
            }

            res = pizzaConfig.updateOptionPrice(optionSetName, optionName, newPrice);
        }
        return true;
    }

    public synchronized boolean deletePizzeria(String pizzeriaName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.SEVERE, "You are trying to delete a pizzeria that does not exist.");
            return false;
        }
        configs.remove(pizzeriaName);
        return true;
    }

    public synchronized boolean deleteOption(String pizzeriaName, String optionSetName, String optionName){
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "You are trying to delete a an option within an option set that does not exist.");
            return false;
        }

        boolean success = pizzaConfig.deleteOption(optionSetName, optionName);
        if (!success) {
            _logger.log(Level.WARNING, " Deleting option '" + optionName + "' failed!");
        }
        return true;
    }

    public synchronized boolean deleteOptionSet(String pizzeriaName, String optionSetName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "You are trying to delete a an option set of a pizza configuration that doesn't not exist.");
            return false;
        }

        boolean success = pizzaConfig.deleteOptionSet(optionSetName);
        if (!success) {
            _logger.log(Level.WARNING, " Deleting option set '" + optionSetName + "' failed! Option set does not exist.");
        }

        return true;
    }

    public boolean addOption(String pizzeriaName, String optionSetName, String optionName){
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "You are trying to update a pizzeria that does not exist.");
            return false;
        }

        boolean success = pizzaConfig.addOption(optionSetName, optionName);
        if (!success) {
            _logger.log(Level.WARNING, " Adding option '" + optionName + "' failed!");
        }
        return true;
    }

    public boolean addOption(String pizzeriaName, String optionSetName, String optionName, double price){
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "You are trying to update a pizzeria that does not exist.");
            return false;
        }

        boolean success = pizzaConfig.addOption(optionSetName, optionName, price);
        if (!success) {
            _logger.log(Level.WARNING, " Adding option '" + optionName + "' failed!");
        }

        return true;
    }


}
