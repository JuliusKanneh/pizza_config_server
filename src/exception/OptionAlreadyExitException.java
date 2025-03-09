package exception;

import model.PizzaConfig;

import java.util.logging.Level;

public class OptionAlreadyExitException extends CustomException
{
    /** The automatically generated unique name for the conflicting option. */
    private final String _optionSetName;
    private final String _optionName;
    private final double _newOptionPrice;

    /** The pizza configuration associated with this exception. */
    private final PizzaConfig _pizzaConfig;

    public OptionAlreadyExitException(PizzaConfig pizzaConfig, String optionSetName, String optionName, double newOptionPrice){
        super("Option " + optionName + " already exits!");
        _pizzaConfig = pizzaConfig;
        _optionSetName = optionSetName;
        _optionName = optionName;
        _newOptionPrice = newOptionPrice;
    }

    @Override
    public void fix() {
        if (super.getLoggerStatus()) {
            super.logMessage(">> Fixing: updating optionPrice of \"" + _optionName + "\" to $" + _newOptionPrice, Level.WARNING);
        }
        _pizzaConfig.updateOptionPrice(_optionSetName, _optionName, _newOptionPrice);
        if (super.getLoggerStatus()) {
            super.logMessage("Successfully Fixed!", Level.INFO);
        }
    }

}
