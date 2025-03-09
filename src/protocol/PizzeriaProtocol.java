package protocol;

import networking.requests.CustomRequest;
import networking.response.CustomResponse;
import wrapper.PizzeriaConfigAPI;

import java.util.logging.Logger;

abstract public class PizzeriaProtocol
{
    final PizzeriaConfigAPI _api;
    final CustomRequest _request;
    final Logger _logger;

    PizzeriaProtocol(PizzeriaConfigAPI api, CustomRequest request) {
        _api = api;
        _request = request;
        _logger = Logger.getLogger(this.getClass().getName());
    }

    public abstract CustomResponse handleRequest();
}
