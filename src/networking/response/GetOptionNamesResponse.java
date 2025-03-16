package networking.response;

import java.util.ArrayList;

public class GetOptionNamesResponse extends CustomResponse{
    private ArrayList<String> _optionSetNames;

    public GetOptionNamesResponse(boolean status, String message, ArrayList<String> optionSetNames) {
        super(status, message);
        _optionSetNames = optionSetNames;
    }

    public ArrayList<String> getOptionSetNames() {
        return _optionSetNames;
    }
}
