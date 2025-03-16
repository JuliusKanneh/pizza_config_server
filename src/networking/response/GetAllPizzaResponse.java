package networking.response;

import java.util.ArrayList;

public class GetAllPizzaResponse extends CustomResponse
{
    private ArrayList<String> _pizzeriaName;

    public GetAllPizzaResponse(boolean status, String message, ArrayList<String> pizzeriaNames) {
        super(status, message);
        _pizzeriaName = pizzeriaNames;
    }

    public ArrayList<String> getPizzeriaName() {
        return _pizzeriaName;
    }
}
