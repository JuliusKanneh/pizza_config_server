package protocol;

import networking.requests.CustomRequest;

public interface ProtocolFactory
{
    PizzeriaProtocol createProtocol(CustomRequest request);
}
