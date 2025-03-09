package exception;

import model.PizzaConfig;

import java.util.logging.Level;

public class OptionSetNotFoundException extends CustomException
{
    /** The name of the missing option set. */
    private final String _optionSetName;

    private final PizzaConfig _config;

    public OptionSetNotFoundException(PizzaConfig config, String optionSetName){
        super("OptionSet " + optionSetName + " not found!");
        _optionSetName = optionSetName;
        _config = config;
    }

    @Override
    public void fix() {
        if (super.getLoggerStatus()) {
            super.logMessage(">> Fix: Adding Option Set " + _optionSetName, Level.WARNING);
        }
        _config.addOptionSet(_optionSetName);
        if (super.getLoggerStatus()) {
            super.logMessage("Successfully Fixed!", Level.WARNING);
        }
    }

}
