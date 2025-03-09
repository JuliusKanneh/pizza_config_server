package protocol;

import networking.requests.CustomRequest;
import networking.requests.GetOptionSetNamesRequest;
import networking.response.CustomResponse;
import networking.response.GetOptionSetNamesResponse;
import wrapper.PizzeriaConfigAPI;

import java.util.ArrayList;

public class GetOptionSetNamesProtocol extends PizzeriaProtocol
{
    GetOptionSetNamesProtocol(PizzeriaConfigAPI api, CustomRequest request) {
        super((PizzeriaConfigAPI) api, request);
    }

    @Override
    public CustomResponse handleRequest() {
        String pizzeriaName = ((GetOptionSetNamesRequest) _request).getPizzeriaName();
        ArrayList<String> optionSetNames = _api.getOptionSetNames(pizzeriaName);
        String message = "Successfully retrieved option set names of " + pizzeriaName + ".";
        if(optionSetNames == null) {
            message = "Pizzeria " + pizzeriaName + " does not have any option set.";
        }
        return new GetOptionSetNamesResponse(true, message, optionSetNames);
    }

}
