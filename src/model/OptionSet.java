/**
    File: OptionSet.java
    Author: Julius Aries Kanneh, Jr (jkanneh)
    Date: January 25, 2025
    Description:
        This file models the behavior of OptionSet and its nested Option class.
        An OptionSet represents a collection of options (such as toppings, sizes, etc.)
        available for a pizza configuration. It provides methods to add and retrieve options,
        as well as to print the details of the option set.

    Revision History:
        - None since submission

    Academic Integrity Policy Statement:
        "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."
*/


package model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * OptionSet represents a collection of options for a pizza configuration.
 * <p>
 * This class encapsulates an option set identified by its name and a list of {@link Option}
 * objects. It provides methods to add new options (with or without prices), find existing options,
 * and print the details of the option set.
 * </p>
 * <p>
 * Usage Example:
 * <pre>
 * OptionSet optionSet = new OptionSet("Toppings");
 * optionSet.addOption("Mushrooms", 1.50);
 * OptionSet.Option option = optionSet.findOption("Mushrooms");
 * </pre>
 * </p>
 *
 * @see Option
 */
class OptionSet implements Serializable
{
    private String _name;
    private final ArrayList<Option> _options;

    /**
     * Constructs a new OptionSet with the specified name.
     *
     * @param name the name of the option set.
     */
    protected OptionSet(String name) {
        _name = name;
        _options = new ArrayList<>();
    }

    /**
     * Returns the name of this OptionSet.
     *
     * @return the option set name.
     */
    protected String getName() {
        return _name;
    }

    /**
     * Sets the name of this OptionSet.
     *
     * @param name the new name for the option set.
     */
    protected void setName(String name) {
        _name = name;
    }

    /**
     * Returns the list of options contained in this OptionSet.
     *
     * @return an ArrayList of {@link Option} objects.
     */
    //TODO: consider creating a method to print all of the options in relating to an optionSet in the PizzaConfig class.
    protected ArrayList<Option> getOptions() {
        return _options;
    }

    /**
     * Adds a new option with the specified name to this OptionSet.
     * <p>
     * If an option with the given name already exists, the method does not add a duplicate and returns false.
     * Otherwise, it creates a new option and adds it to the list.
     * </p>
     *
     * @param optionName the name of the option to add.
     * @return false if the option already exists; otherwise, false is returned (Note: this method currently always returns false).
     */
    protected boolean addOption(String optionName) {
        boolean flag = false;
        Option tempOption = findOption(optionName);
        if (tempOption != null) {
            // return false because the option with name already exist.
            return flag;
        }

        tempOption = new Option(optionName);
        _options.add(tempOption);
        return flag;
    }

    /**
     * Adds a new option with the specified name and price to this OptionSet.
     * <p>
     * If an option with the given name already exists, the method does nothing.
     * (TODO: Consider throwing an exception or returning a status to indicate the option already exists.)
     * </p>
     *
     * @param optionName the name of the option to add.
     * @param price      the price associated with the option.
     */
    protected boolean addOption(String optionName, double price) {
        Option tempOption = findOption(optionName);
        if (tempOption != null) {
            // return false because the option with name already exist.
            return false;
        }

        tempOption = new Option(optionName, price);
        _options.add(tempOption);
        return true;
    }
    /**
     * Searches for an option with the specified name in this OptionSet.
     *
     * @param optionName the name of the option to search for.
     * @return the {@link Option} if found; otherwise, null.
     */
    protected Option findOption(String optionName) {
        Option tempOption = null;

        if (!_options.isEmpty()){
            for (Option option : _options ) {
                if (option.getName().equals(optionName)) {
                    tempOption = option;
                }
            }
        }

        return tempOption;
    }

    /**
     * Returns a string representation of this OptionSet.
     *
     * @return a string containing the class name, option set name, and its options.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getClass().getName());
        stringBuilder.append(" [name = " + _name + ",");
        stringBuilder.append("_options = " + _options + ",");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * Prints the details of this OptionSet and its options.
     * <p>
     * The output includes the name of the option set followed by the details of each option.
     * </p>
     */
    protected void print(){
        StringBuilder stringBuilder = new StringBuilder("OptionSet Details\n");
        stringBuilder.append("-------------------------\n");
        stringBuilder.append("Name: " + _name + "\n");
        for (Option option : _options) {
            option.print();
        }
    }

    /**
     * Option represents an individual option within an OptionSet.
     * <p>
     * Each Option has a name and an associated price. The price is optional and defaults to 0 if not specified.
     * </p>
     */
    class Option implements Serializable {
        private String _name;
        private double _price; // This price is optional. Set the default to 0

        /**
         * Constructs a new Option with the specified name and a default price of 0.
         *
         * @param name the name of the option.
         */
        protected Option(String name){
            _name = name;
            _price = 0;
        }

        /**
         * Constructs a new Option with the specified name and price.
         *
         * @param name  the name of the option.
         * @param price the price of the option.
         */
        protected Option(String name, double price) {
            _name = name;
            _price = price;
        }

        /**
         * Returns the name of this option.
         *
         * @return the option name.
         */
        protected String getName() {
            return _name;
        }

        /**
         * Sets the name of this option.
         *
         * @param name the new name for the option.
         */
        protected void setName(String name) {
            _name = _name;
        }

        /**
         * Returns the price of this option.
         *
         * @return the option price.
         */
        protected double getPrice() {
            return _price;
        }

        /**
         * Sets the price of this option.
         *
         * @param price the new price for the option.
         */
        protected void setPrice(double price) {
            _price = price;
        }

        /**
         * Returns a string representation of this option.
         *
         * @return a string containing the option name and price.
         */
        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Option [name=");
            stringBuilder.append(_name);
            stringBuilder.append(", price=");
            stringBuilder.append(_price);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        /**
         * Prints the details of this option.
         * <p>
         * The output includes the option name and its price.
         * </p>
         */
        protected void print(){
            StringBuilder stringBuilder = new StringBuilder("Option Details\n");
            stringBuilder.append("-------------------------\n");
            stringBuilder.append("Option Name: ");
            stringBuilder.append(_name);
            stringBuilder.append("\n");
            stringBuilder.append("Option Price: ");
            stringBuilder.append(_price);
            System.out.println(stringBuilder);
        }
    }

}
