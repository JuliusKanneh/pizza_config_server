package protocol;

import networking.requests.CustomRequest;
import networking.requests.GetOptionNamesRequest;
import networking.requests.GetOptionSetNamesRequest;
import networking.response.CustomResponse;
import networking.response.GetOptionNamesResponse;
import networking.response.GetOptionSetNamesResponse;
import wrapper.PizzeriaConfigAPI;

import java.util.ArrayList;

public class GetOptionNamesProtocol extends PizzeriaProtocol
{
    GetOptionNamesProtocol(PizzeriaConfigAPI api, CustomRequest request) {
        super((PizzeriaConfigAPI) api, request);
    }

    @Override
    public CustomResponse handleRequest() {
        String pizzeriaName = ((GetOptionNamesRequest) _request).getPizzeriaName();
        String optionSetName = ((GetOptionNamesRequest) _request).getOptionSetName();
        ArrayList<String> optionNames = _api.getOptionNames(pizzeriaName, optionSetName);
        String message = "Successfully retrieved option names of " + pizzeriaName + ".";
        if (optionNames == null) {
            message = "No option names found.";
        }
        return new GetOptionNamesResponse(true, message, optionNames);
    }
}
