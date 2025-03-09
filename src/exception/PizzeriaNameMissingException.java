// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package exception;

import model.PizzaConfig;

import java.util.logging.Level;

public class PizzeriaNameMissingException extends CustomException
{
    private final String _pizzeriaName;

    private PizzaConfig _pizzaConfig;

    public PizzeriaNameMissingException(PizzaConfig pizzaConfig, String pizzeriaName){
        super("Pizzeria " + pizzeriaName + " not found");
        _pizzeriaName = pizzeriaName;
        _pizzaConfig = pizzaConfig;
    }

    @Override
    public void fix() {
        if (super.getLoggerStatus()){
            logMessage(">> Fix: adding the pizzeria name to the newly created pizzeria " + _pizzaConfig, Level.WARNING);
        }
        _pizzaConfig.setConfigName(_pizzeriaName);
        if (super.getLoggerStatus()){
            logMessage("Successfully Fixed!", Level.INFO);
        }
    }
}
