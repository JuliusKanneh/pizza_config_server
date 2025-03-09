package exception;

import model.PizzaConfig;

import java.util.logging.Level;

/**
 * The {@code PizzeriaAlreadyExitException} class is a custom exception that
 * is thrown when an attempt is made to add a pizzeria that already exists
 * in the system.
 *
 * <p>This exception provides a self-healing mechanism by generating a unique
 * pizzeria name and adding it to the configuration with the specified base price.</p>
 *
 * <p>Academic Integrity Policy Statement: "On my honor, as a Carnegie-Mellon Africa student, I have neither given
 *              nor received unauthorized assistance on this work."</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 * try {
 *     if (pizzeriaConfigs.containsKey("Dominos")) {
 *         throw new PizzeriaAlreadyExitException("Dominos", 12.99, pizzeriaConfigs);
 *     }
 *     pizzeriaConfigs.put("Dominos", new PizzaConfig("Dominos", 12.99));
 * } catch (PizzeriaAlreadyExitException e) {
 *     e.fix(); // Automatically renames and adds the pizzeria
 * }
 * </pre>
 */
public class PizzeriaAlreadyExitException extends CustomException
{
    private PizzaConfig _oldPizzaConfig;
    private final PizzaConfig _newPizzaConfig;

    public PizzeriaAlreadyExitException(PizzaConfig oldPizzaConfig, PizzaConfig newPizzaConfig){
        super("Pizzeria " + oldPizzaConfig.getConfigName() + " already exits!");
        _oldPizzaConfig = oldPizzaConfig;
        _newPizzaConfig = newPizzaConfig;
    }

    @Override
    public void fix() {
        if(super.getLoggerStatus()){
            super.logMessage(">> Fix: Updating Pizzeria " + _oldPizzaConfig + " to " + _newPizzaConfig, Level.WARNING);
        }

        //Update the pizzeria
        _oldPizzaConfig = _newPizzaConfig;

        if(super.getLoggerStatus()){
            super.logMessage("Successfully Fixed.", Level.INFO);
        }
    }
}
