// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package protocol;

import networking.requests.AddOptionToOptionSetRequest;
import networking.requests.CustomRequest;
import networking.response.AddOptionToOptionSetResponse;
import networking.response.CustomResponse;
import wrapper.PizzeriaConfigAPI;
import wrapper.UpdatePizzeria;

import java.util.logging.Level;

public class AddOptionToOptionSetProtocol extends PizzeriaProtocol
{
    private final UpdatePizzeria _updatePizzeriaApi;

    AddOptionToOptionSetProtocol(UpdatePizzeria api, CustomRequest request) {
        super((PizzeriaConfigAPI) api, request);
        _updatePizzeriaApi = api;
    }

    @Override
    public CustomResponse handleRequest() {
        String pizzeriaName = ((AddOptionToOptionSetRequest) _request).getPizzeriaName();
        String optionSetName = ((AddOptionToOptionSetRequest) _request).getOptionSetName();
        String optionName = ((AddOptionToOptionSetRequest) _request).getOptionName();
        double price = ((AddOptionToOptionSetRequest) _request).getPrice();

        boolean success = _updatePizzeriaApi.addOption(pizzeriaName, optionSetName, optionName, price);
        String message = "Successfully created pizzeria";
        if (!success) {
            message = "Error creating pizzeria";
            _logger.log(Level.SEVERE, message);
        }

        return new AddOptionToOptionSetResponse(success, message);
    }
}
