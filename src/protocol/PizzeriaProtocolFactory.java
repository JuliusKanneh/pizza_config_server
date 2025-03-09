// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package protocol;

import networking.requests.*;
import wrapper.PizzeriaConfigAPI;

import static protocol.ProtocolType.*;

public class PizzeriaProtocolFactory implements ProtocolFactory
{
    private final PizzeriaConfigAPI _api;
    private final CustomRequest _request;

    public PizzeriaProtocolFactory(PizzeriaConfigAPI api, CustomRequest request) {
        _api = api;
        _request = request;
    }

    @Override
    public PizzeriaProtocol createProtocol(CustomRequest request) {
        ProtocolType type = mapRequestToProtocolType(request);
        assert type != null;
        return switch (type) {
            case CONFIG_PIZZERIA_PROTOCOL -> new ConfigPizzeriaProtocol(_api, _request);
            case GET_ALL_PIZZERIA_PROTOCOL -> new GetAllPizzeriaProtocol(_api, _request);
            case GET_ALL_OPTION_SET_NAMES_PROTOCOL -> new GetOptionSetNamesProtocol(_api, _request);
            case GET_PIZZERIA_PROTOCOL -> new GetPizzeriaProtocol(_api, _request);
            case DELETE_PIZZERIA_PROTOCOL -> new DeletePizzeriaProtocol(_api, _request);
            case UPDATE_BASE_PRICE_PROTOCOL -> new UpdateBasePriceProtocol(_api, _request);
            case ADD_OPTION_TO_OPTION_SET_PROTOCOL -> new AddOptionToOptionSetProtocol(_api, _request);
            default -> throw new IllegalArgumentException("Unknown Protocol Type");
        };
    }

    private ProtocolType mapRequestToProtocolType(CustomRequest request) {
        return switch (request) {
            case DeletePizzeriaRequest _ -> DELETE_PIZZERIA_PROTOCOL;
            case GetPizzeriaRequest _ -> GET_PIZZERIA_PROTOCOL;
            case GetAllPizzaRequest _ -> GET_ALL_PIZZERIA_PROTOCOL;
            case ConfigPizzeriaRequest _ -> CONFIG_PIZZERIA_PROTOCOL;
            case UpdateBasePriceRequest _ -> UPDATE_BASE_PRICE_PROTOCOL;
            case AddOptionToOptionSetRequest _ -> ADD_OPTION_TO_OPTION_SET_PROTOCOL;
            case GetOptionSetNamesRequest _ -> GET_ALL_OPTION_SET_NAMES_PROTOCOL;
            case null, default -> null;
        };
    }
}
