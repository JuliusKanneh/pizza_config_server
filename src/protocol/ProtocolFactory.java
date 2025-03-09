// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package protocol;

import networking.requests.CustomRequest;

public interface ProtocolFactory
{
    PizzeriaProtocol createProtocol(CustomRequest request);
}
