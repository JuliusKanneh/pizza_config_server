package networking.requests;

public class GetPizzeriaRequest implements CustomRequest
{
    private String _pizzeriaName;

    public GetPizzeriaRequest(String pizzeriaName){
        super();
        _pizzeriaName = pizzeriaName;
    }

    public String getPizzeriaName(){
        return _pizzeriaName;
    }
}
