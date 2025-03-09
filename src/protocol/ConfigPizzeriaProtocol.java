// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package protocol;

import networking.requests.ConfigPizzeriaRequest;
import networking.requests.CustomRequest;
import networking.response.ConfigurePizzeriaResponse;
import networking.response.CustomResponse;
import wrapper.CreatePizzeria;
import wrapper.PizzeriaConfigAPI;

import java.util.Properties;
import java.util.logging.Level;

public class ConfigPizzeriaProtocol extends PizzeriaProtocol
{
    private final CreatePizzeria createPizzeriaApi;

    ConfigPizzeriaProtocol(CreatePizzeria api, CustomRequest request) {
        super((PizzeriaConfigAPI) api, request);
        createPizzeriaApi = api;
    }

    @Override
    public CustomResponse handleRequest() {
        Properties properties = ((ConfigPizzeriaRequest) _request).getProperties();
        System.out.println("\n---------- printing properties on the server side before creating a pizzeria-----------\n");
        System.out.println(properties);
        boolean success = createPizzeriaApi.createPizzeria(properties);
        String message = "Successfully created pizzeria";
        if (!success) {
            message = "Error creating pizzeria";
            _logger.log(Level.SEVERE, message);
        }

        return new ConfigurePizzeriaResponse(success, message);
    }
}
