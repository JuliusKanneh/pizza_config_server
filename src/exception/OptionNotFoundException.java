package exception;

import model.PizzaConfig;
import utils.ConstantValues;

import java.util.logging.Level;

public class OptionNotFoundException extends CustomException
{
    /** The pizza configuration where the option was not found. */
    private final PizzaConfig _pizzaConfig;

    /** The name of the option set where the option was missing. */
    private final String _optionSetName;

    /** The name of the missing option. */
    private final String _optionName;

    /** The price assigned to the newly added option if the fix is applied. */
    private final double _price;

    public OptionNotFoundException(PizzaConfig pizzaConfig, String optionSetName, String optionName){
        super("Option not found!");
        _pizzaConfig = pizzaConfig;
        _optionSetName = optionSetName;
        _optionName = optionName;
        _price = ConstantValues.DEFAULT_OPTION_PRICE;
    }

    public OptionNotFoundException(PizzaConfig pizzaConfig, String optionSetName, String optionName, double price){
        super("Option not found: " + optionName);
        _pizzaConfig = pizzaConfig;
        _optionSetName = optionSetName;
        _optionName = optionName;
        _price = price;
    }

    @Override
    public void fix() {
        if (super.getLoggerStatus()) {
            super.logMessage(">> Fixing: Setting base price of \"" + _pizzaConfig.getConfigName() + "\" to default $" + _price, Level.WARNING);
        }
        _pizzaConfig.addOption(_optionSetName, _optionName, _price);
        if (super.getLoggerStatus()) {
            super.logMessage("Successfully fixed!", Level.WARNING);
        }
    }

}
