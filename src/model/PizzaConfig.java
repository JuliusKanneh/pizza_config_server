// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

/**
 * PizzaConfig Class Documentation
 * <p>
 * This class represents a Pizza Configuration model for the Pizza Configuration API.
 * It allows users to define a pizza configuration by setting its name, base price,
 * and various option sets. Users can add, update, and delete option sets and options.
 *
 * <p><strong>Author:</strong> Julius Aries Kanneh, Jr (jkanneh)
 * <br><strong>Date:</strong> January 25, 2025
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Set and update pizza configuration name and base price</li>
 *   <li>Add, find, update, and delete option sets</li>
 *   <li>Add, find, update, and delete options within an option set</li>
 *   <li>Prevent duplication of option sets and options</li>
 *   <li>Handle exceptions for invalid operations</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 *   PizzaConfig pizzaConfig = new PizzaConfig("Deluxe Pizza", 10.99);
 *   pizzaConfig.addOptionSet("Toppings");
 *   pizzaConfig.addOption("Toppings", "Pepperoni", 2.00);
 *   pizzaConfig.updateOptionPrice("Toppings", "Pepperoni", 2.50);
 *   System.out.println(pizzaConfig.toString());
 * </pre>
 *
 * <h2>Revision History:</h2>
 * <ul>
 *   <li>None since submission</li>
 * </ul>
 */
package model;

import exception.*;
import utils.ConstantValues.UpdateOptionPriceRes;

import java.io.Serializable;
import java.util.ArrayList;

import static utils.ConstantValues.DEFAULT_BASE_PRICE;

/**
 * The {@code PizzaConfig} class represents a model for configuring pizzas.
 * It provides methods for managing option sets, options, and updating configurations.
 *
 * <p>Example Usage:</p>
 * {@code
 * PizzaConfig config = new PizzaConfig("Deluxe", 15.99);
 * config.addOptionSet("Toppings");
 * config.addOption("Toppings", "Pepperoni", 2.00);
 * }
 */
public class PizzaConfig implements Serializable
{
    private String _configName;
    private double _basePrice;
    private ArrayList<OptionSet> _optionSets;

    /**
     * Constructs a new PizzaConfig with a specified name and base price.
     *
     * @param configName the name of the pizza configuration
     * @param basePrice the base price of the pizza
     */
    public PizzaConfig(String configName, double basePrice) {
        _configName = configName;
        _basePrice = basePrice;
        _optionSets = new ArrayList<>();
    }

    /**
     * Constructs a new PizzaConfig with a specified name and a default base price.
     *
     * @param configName the name of the pizza configuration
     */
    public PizzaConfig(String configName) {
        _configName = configName;
        _basePrice = DEFAULT_BASE_PRICE;
        _optionSets = new ArrayList<>();
    }

    /**
     * Constructs a default PizzaConfig with no initial settings.
     * I am using this constructor to allow more flexibility when constructing a pizzConfi option.
     */
    public PizzaConfig() {
        _basePrice = DEFAULT_BASE_PRICE;
        _optionSets = new ArrayList<>();
    }

    /**
     * Gets the name of the pizza configuration.
     *
     * @return the configuration name
     */
    public String getConfigName() {
        return _configName;
    }

    /**
     * Sets the name of the pizza configuration.
     *
     * @param configName the new configuration name
     */
    public void setConfigName(String configName) {
        _configName = configName;
    }

    /**
     * Gets the base price of the pizza configuration.
     *
     * @return the base price
     */
    public double getBasePrice() {
        return _basePrice;
    }

    /**
     * Updates the base price of the pizza configuration.
     *
     * @param basePrice the new base price
     */
    public void updateBasePrice(double basePrice) {
        _basePrice = basePrice;
    }

    /**
     * Gets the list of option sets for the pizza.
     *
     * @return an ArrayList of {@link OptionSet}
     */
//    private ArrayList<OptionSet> getOptionSets() {
//        return _optionSets;
//    }

    public ArrayList<String> getOptionSetNames() {
        ArrayList<String> optionSetNames = new ArrayList<>();
        for (OptionSet optionSet : _optionSets) {
            optionSetNames.add(optionSet.getName());
        }
        return optionSetNames;
    }

    /**
     * Finds an option set by name.
     *
     * @param optionSetName the name of the option set
     * @return the matching OptionSet or null if not found
     */
    private OptionSet findOptionSet(String optionSetName) {
        for (OptionSet optionSet : _optionSets) {
            if (optionSet.getName().equals(optionSetName)) {
                return optionSet;
            }
        }
        return null;
    }

    /**
     * Adds a new option set to the configuration.
     *
     * @param optionSetName the name of the option set
     */
    public boolean addOptionSet(String optionSetName) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet != null) {
            return false;
        }
        _optionSets.add(new OptionSet(optionSetName));
        return true;
    }

    /**
     * Adds a new option to an existing option set.
     *
     * @param optionSetName the name of the option set
     * @param optionName the name of the option
     */
    public boolean addOption(String optionSetName, String optionName) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet == null) {
            //Ensuring that there is an optionSet that exist.
            return false;
        }

        for (OptionSet.Option option : optionSet.getOptions()) {
            if (option.getName().equals(optionName)) {
                return false;
            }
        }
        return optionSet.addOption(optionName);
    }

    /**
     * Adds a new option with a price to an existing option set.
     *
     * @param optionSetName the name of the option set
     * @param optionName    the name of the option
     * @param price         the price of the option
     */
    public boolean addOption(String optionSetName, String optionName, double price) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet == null) {
            return false;
        }

        return optionSet.addOption(optionName, price);
    }

    /**
     * Updates the name of an existing option set.
     *
     * @param optionSetName the current name of the option set
     * @param newOptionSetName the new name of the option set
     * @return {@code true} if updated successfully, {@code false} otherwise
     */
    public boolean updateOptionSetName(String optionSetName, String newOptionSetName) {
        OptionSet tempOptionSet = findOptionSet(optionSetName);
        if (tempOptionSet == null) {
            return false;
        }
        tempOptionSet.setName(newOptionSetName);
        return true;
    }


    /**
     * Updates the price of an existing option.
     *
     * @param optionSetName the name of the option set
     * @param optionName the name of the option
     * @param newOptionPrice the new price for the option
     */
    public UpdateOptionPriceRes updateOptionPrice(String optionSetName, String optionName, double newOptionPrice) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet == null) {
            return UpdateOptionPriceRes.OPTION_SET_NOT_FOUND;
        }

        OptionSet.Option option = optionSet.findOption(optionName);
        if (option == null) {
            return UpdateOptionPriceRes.OPTION_NOT_FOUND;
        }

        option.setPrice(newOptionPrice);
        return UpdateOptionPriceRes.SUCCESS;
    }

    /**
     * Deletes an option set by name.
     *
     * @param optionSetName the name of the option set to delete
     */
    public boolean deleteOptionSet(String optionSetName) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet == null) {
            return false;
        }
        return _optionSets.remove(optionSet);
    }


    /**
     * Deletes an option from the specified option set.
     *
     * <p>This method attempts to remove an option from an existing option set.
     * If the option set does not exist, an {@link OptionNotFoundException} is thrown.
     * If the option exists, it is removed; otherwise, no changes are made.</p>
     *
     * @param optionSetName the name of the option set from which the option should be deleted
     * @param optionName the name of the option to delete
     */
    public boolean deleteOption(String optionSetName, String optionName) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet == null) {
            return false;
        }

        ArrayList<OptionSet.Option> options = optionSet.getOptions();
        if (options.isEmpty()) {
            return false;
        }

        boolean foundFlag = false;
        for (OptionSet.Option option : options) {
            if (option.getName().equals(optionName)) {
                foundFlag = true;
                _optionSets.remove(optionSet);
                break;
            }
        }

        if (!foundFlag) {
            return false;
        }

        return true;
    }

    /**
     * Returns a string representation of the {@code PizzaConfig} object.
     *
     * <p>This method generates a concise summary of the pizza configuration,
     * including the configuration name, base price, and the number of option sets.</p>
     *
     * <p>Example Output:</p>
     * <pre>
     * model.PizzaConfig [_configName=Deluxe, _basePrice=15.99, optionSetCount=3]
     * </pre>
     *
     * @return a formatted string representing the pizza configuration
     */
    @Override
    public String toString() {
        return new StringBuilder(getClass().getName())
                .append(" [_configName=")
                .append(_configName)
                .append(", _basePrice=")
                .append(_basePrice)
                .append(", optionSetCount=")
                .append(_optionSets.size())
                .append("]")
                .toString();
    }

    /**
     * Prints the details of the pizza configuration.
     *
     * <p>This method outputs the configuration name, base price, and all option sets
     * associated with the pizza configuration. Each option set is printed along with
     * its options.</p>
     *
     * <p>Example Output:</p>
     * <pre>
     * PizzaConfig Details:
     * --------------------------
     * _configName: Deluxe
     * _basePrice: 15.99
     * _optionSets:
     * OptionSet Name: Toppings
     *    - Pepperoni: $2.00
     *    - Mushrooms: $1.50
     * </pre>
     */
    public void print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PizzaConfig Details:\n");
        stringBuilder.append("--------------------------\n");
        stringBuilder.append("_configName: ").append(_configName);
        stringBuilder.append(", \n");
        stringBuilder.append("_basePrice: ").append(_basePrice);
        stringBuilder.append(", \n");
        stringBuilder.append("_optionSets: \n");
        System.out.println(stringBuilder);

        for (OptionSet optionSet : _optionSets) {
            System.out.println("OptionSet Name: " + optionSet.getName());
            optionSet.print();
            System.out.println("\n");
        }
    }

    /**
     * Prints the details of a specific option set.
     *
     * <p>This method finds and prints a single option set by name. If the option set
     * does not exist, an {@link OptionSetNotFoundException} is thrown.</p>
     *
     * @param optionSetName the name of the option set to
     */
    public void printOptionSet(String optionSetName) {
        OptionSet optionSet = findOptionSet(optionSetName);
        if (optionSet != null) {
            optionSet.print();
        }
    }

}
