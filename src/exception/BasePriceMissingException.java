// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package exception;

import model.PizzaConfig;
import utils.ConstantValues;

import java.util.logging.Level;


public class BasePriceMissingException extends CustomException
{
    private final PizzaConfig _pizzaConfig;


    public BasePriceMissingException(PizzaConfig pizzaConfig) {
        super("Base price is missing.");
        _pizzaConfig = pizzaConfig;
    }


    @Override
    public void fix() {
        double price = ConstantValues.DEFAULT_BASE_PRICE;
        if (super.getLoggerStatus()) {
            super.logMessage(">> Fixing: Setting base price of \"" + _pizzaConfig.getConfigName() + "\" to default $" + price, Level.WARNING);
        }
        _pizzaConfig.updateBasePrice(price);
        super.logMessage("Successfully fixed base price by setting its the default base price ($0.0)", Level.INFO);
    }
}
