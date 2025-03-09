package protocol;

import model.PizzaConfig;
import networking.requests.CustomRequest;
import networking.requests.GetPizzeriaRequest;
import networking.response.CustomResponse;
import networking.response.GetPizzeriaResponse;
import wrapper.CreatePizzeria;
import wrapper.PizzeriaConfigAPI;

public class GetPizzeriaProtocol extends PizzeriaProtocol{

    GetPizzeriaProtocol(CreatePizzeria api, CustomRequest request) {
        super((PizzeriaConfigAPI) api, request);
    }

    @Override
    public CustomResponse handleRequest() {
        String pizzeriaName = ((GetPizzeriaRequest) _request).getPizzeriaName();
        PizzaConfig pizzaConfig = _api.getPizzaConfig(pizzeriaName);
        String message = "Successfully retrieved pizzeria details " + pizzeriaName;
        if(pizzaConfig == null) {
            message = "Pizzeria does not exist";
        }
        return new GetPizzeriaResponse(true, message, pizzaConfig);
    }
}
