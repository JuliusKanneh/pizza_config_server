// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package protocol;

import networking.requests.CustomRequest;
import networking.requests.UpdateBasePriceRequest;
import networking.response.CustomResponse;
import networking.response.UpdateBasePriceResponse;
import wrapper.PizzeriaConfigAPI;

import java.util.logging.Level;

public class UpdateBasePriceProtocol extends PizzeriaProtocol
{

    UpdateBasePriceProtocol(PizzeriaConfigAPI api, CustomRequest request) {
        super(api, request);
    }

    @Override
    public CustomResponse handleRequest() {
        String pizzeriaName = ((UpdateBasePriceRequest) _request).get_pizzeriaName();
        double basePrice = ((UpdateBasePriceRequest) _request).get_basePrice();
        boolean success = _api.updateBasePrice(pizzeriaName, basePrice);
        String message = "Successfully Updated base price of " + pizzeriaName + " to " + basePrice;
        if (!success) {
            message = "Error Updating BasePrice";
            _logger.log(Level.SEVERE, message);
        }
        return new UpdateBasePriceResponse(success, message);
    }
}
