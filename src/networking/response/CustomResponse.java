// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package networking.response;

import java.io.Serializable;

abstract public class CustomResponse implements Serializable
{
    boolean _status;
    String _message;

    CustomResponse(boolean status, String message) {
        _status = status;
        _message = message;
    }

    public boolean getStatus() {
        return _status;
    }

    public String getMessage() {
        return _message;
    }
}
