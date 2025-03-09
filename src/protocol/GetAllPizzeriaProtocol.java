package protocol;

import networking.requests.CustomRequest;
import networking.response.CustomResponse;
import networking.response.GetAllPizzaResponse;
import wrapper.CreatePizzeria;
import wrapper.PizzeriaConfigAPI;

import java.util.ArrayList;

public class GetAllPizzeriaProtocol extends PizzeriaProtocol{

    GetAllPizzeriaProtocol(CreatePizzeria api, CustomRequest request) {
        super((PizzeriaConfigAPI) api, request);
    }

    @Override
    public CustomResponse handleRequest() {
        ArrayList<String> pizzeriaNames = _api.getAllPizzeriaNames();
        String message = "Successfully Retrieved all pizzeria names.";
        return new GetAllPizzaResponse(true, message, pizzeriaNames);
    }
}
