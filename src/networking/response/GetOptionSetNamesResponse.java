// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package networking.response;

import java.util.ArrayList;

public class GetOptionSetNamesResponse extends CustomResponse
{
    private ArrayList<String> _optionSetNames;

    public GetOptionSetNamesResponse(boolean status, String message, ArrayList<String> optionSetNames) {
        super(status, message);
        _optionSetNames = optionSetNames;
    }

    public ArrayList<String> getOptionSetNames() {
        return _optionSetNames;
    }
}
