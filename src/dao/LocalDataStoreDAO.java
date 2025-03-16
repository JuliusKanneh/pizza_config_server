package dao;

import exception.*;
import model.PizzaConfig;
import utils.ConstantValues;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalDataStoreDAO implements PizzaConfigDAO
{
    private final Logger _logger = Logger.getLogger(LocalDataStoreDAO.class.getName());
    private static final LinkedHashMap<String, PizzaConfig> configs = new LinkedHashMap<>();
    ExceptionFactory factory = new PizzeriaExceptionFactory();
    CustomException exception;

    @Override
    public boolean createPizzeria(PizzaConfig config) {
        if (config == null) {
            return false;
        } else if (configs.containsKey(config.getConfigName())) {
            configs.replace(config.getConfigName(), config);
        } else {
            configs.put(config.getConfigName(), config);
        }
        return true;
    }

    @Override
    public void configurePizzeriasFromTxtFile(String filename) {
        // TODO: fix-> find a way to inject the api for this method to use or find another way around it.
//        boolean success = PizzaConfigParser.buildPizzaConfig(filename, (PizzeriaConfigAPI) this);
//        if(!success){
//            _logger.log(Level.SEVERE, "Building Pizzeria failed! Self-healing method couldn't fix the problem.");
//        }
    }

    @Override
    public void addOptionSet(String pizzeriaName, String optionSetName) {
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

    @Override
    public void printPizzeria(String pizzeriaName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);

        if (pizzaConfig == null) {
            _logger.log(Level.SEVERE, "Pizzeria '" + pizzeriaName + "' not found!");
        } else {
            pizzaConfig.print();
        }
    }

//    @Override
//    public PizzaConfig getPizzeria(String pizzeriaName) {
//        return null;
//    }

    @Override
    public PizzaConfig getPizzaConfig(String pizzeriaName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "Pizzeria '" + pizzeriaName + "' does not exist!");
            return null;
        }
        return configs.get(pizzeriaName);
    }

    @Override
    public ArrayList<String> getAllPizzeriaNames() {
        ArrayList<String> pizzeriaNames = new ArrayList<>();
        for (Map.Entry<String, PizzaConfig> entry : configs.entrySet()) {
            pizzeriaNames.add(entry.getKey());
        }
        return pizzeriaNames;
    }

    @Override
    public void printAllPizzerias() {
        if (configs.isEmpty())
            _logger.log(Level.INFO, "No Pizzeria exits.");

        for (PizzaConfig pizzaConfig : configs.values()) {
            pizzaConfig.print();
        }
    }

    @Override
    public boolean updatePizzeria(PizzaConfig config) {
        return false;
    }

    @Override
    public boolean deletePizzeria(String pizzeriaName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.SEVERE, "You are trying to delete a pizzeria that does not exist.");
            return false;
        }
        configs.remove(pizzeriaName);
        return true;
    }

    @Override
    public ArrayList<String> getOptionSetNames(String pizzeriaName) {
        ArrayList<String> optionSetNames = new ArrayList<>();
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            return optionSetNames;
        }

        return pizzaConfig.getOptionSetNames();
    }

    @Override
    public boolean updateOptionSetName(String pizzeriaName, String optionSetName, String newName) {
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
        }
        catch (CustomException e) {
            e.fix();
        }
        return true;
    }

    @Override
    public boolean updateBasePrice(String pizzeriaName, double newPrice) {
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

    @Override
    public boolean updateOptionPrice(String pizzeriaName, String optionSetName, String optionName, double newPrice) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "Pizzeria '" + pizzeriaName + "' does not exist!");
            return false;
            //TODO: a better approach could be to prompt the user to decide whether you should create a new pizza config and create a new option set or not.
        }

        ConstantValues.UpdateOptionPriceRes res = pizzaConfig.updateOptionPrice(optionSetName, optionName, newPrice);
        while (res != ConstantValues.UpdateOptionPriceRes.SUCCESS) {
            if (res == ConstantValues.UpdateOptionPriceRes.OPTION_SET_NOT_FOUND) {
                Map<String, Object> params = Map.of("pizzaConfig", pizzaConfig, "optionSetName", optionSetName);
                exception = factory.createException(ExceptionType.OPTION_SET_NOT_FOUND, params);
                exception.fix();
            } else if (res == ConstantValues.UpdateOptionPriceRes.OPTION_NOT_FOUND) {
                Map<String, Object> params = Map.of("pizzaConfig", pizzaConfig, "optionName", optionName);
                exception = factory.createException(ExceptionType.OPTION_SET_NOT_FOUND, params);
                exception.fix();
            }

            res = pizzaConfig.updateOptionPrice(optionSetName, optionName, newPrice);
        }
        return true;
    }

    @Override
    public boolean deleteOption(String pizzeriaName, String optionSetName, String optionName) {
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

    @Override
    public boolean deleteOptionSet(String pizzeriaName, String optionSetName) {
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

    @Override
    public boolean addOption(String pizzeriaName, String optionSetName, String optionName) {
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

    @Override
    public boolean addOption(String pizzeriaName, String optionSetName, String optionName, double price) {
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

    @Override
    public ArrayList<String> getOptions(String pizzeriaName, String optionSetName) {
        PizzaConfig pizzaConfig = configs.get(pizzeriaName);
        if (pizzaConfig == null) {
            _logger.log(Level.WARNING, "Pizzeria '" + pizzeriaName + "' does not exist!");
            return null;
        }
        ArrayList<String> optionNames = pizzaConfig.getOptionNames(optionSetName);
        System.out.println("Option Names For Test: " + optionNames);
        return optionNames;
    }
}
