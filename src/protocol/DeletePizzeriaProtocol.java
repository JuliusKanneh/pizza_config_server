package protocol;

import networking.requests.CustomRequest;
import networking.requests.DeletePizzeriaRequest;
import networking.response.CustomResponse;
import networking.response.DeletePizzeriaResponse;
import wrapper.DeletePizzeria;
import wrapper.PizzeriaConfigAPI;

import java.util.logging.Level;

public class DeletePizzeriaProtocol extends PizzeriaProtocol{

    DeletePizzeriaProtocol(DeletePizzeria api, CustomRequest request) {
        super((PizzeriaConfigAPI) api, request);
    }

    @Override
    public CustomResponse handleRequest() {
        String pizzeriaName = ((DeletePizzeriaRequest) _request).getPizzeriaName();
        boolean success = _api.deletePizzeria(pizzeriaName);
        String message = "Successfully created pizzeria";
        if (!success) {
            message = "Error creating pizzeria";
            _logger.log(Level.SEVERE, message);
        }
        return new DeletePizzeriaResponse(success, message);
    }
}
