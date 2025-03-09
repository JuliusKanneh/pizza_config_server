package networking.response;
import model.PizzaConfig;

public class GetPizzeriaResponse extends CustomResponse
{
    private PizzaConfig _pizzaConfig;

    public GetPizzeriaResponse(boolean status, String message, PizzaConfig pizzaConfig) {
        super(status, message);
        _pizzaConfig = pizzaConfig;
    }

    public PizzaConfig getPizzaConfig() {
        return _pizzaConfig;
    }
}
