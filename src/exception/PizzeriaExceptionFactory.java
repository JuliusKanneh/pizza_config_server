package exception;

import model.PizzaConfig;

import java.util.Map;

public class PizzeriaExceptionFactory implements ExceptionFactory
{
    @Override
    public CustomException createException(ExceptionType type, Map<String, Object> params) {
        return switch (type) {
            case BASE_PRICE_MISSING -> new BasePriceMissingException((PizzaConfig) params.get("pizzaConfig"));
            case CONFIGURATION_FILE_NOT_FOUND ->
                    new ConfigurationFileNotFoundException((String) params.get("fileName"));
            case OPTION_ALREADY_EXISTS -> new OptionAlreadyExitException(
                    (PizzaConfig) params.get("pizzaConfig"),
                    (String) params.get("optionSetName"),
                    (String) params.get("optionName"),
                    (double) params.get("price")
            );
            case PIZZERIA_ALREADY_EXISTS -> new PizzeriaAlreadyExitException(
                    (PizzaConfig) params.get("oldPizzaConfig"),
                    (PizzaConfig) params.get("newPizzaConfig")
            );
            case OPTION_NOT_FOUND -> new OptionNotFoundException(
                    (PizzaConfig) params.get("pizzaConfig"),
                    (String) params.get("optionSetName"),
                    (String) params.get("optionName"),
                    (double) params.get("price")
            );
            default -> throw new IllegalArgumentException("Unknown Exception Type");
        };
    }
}
