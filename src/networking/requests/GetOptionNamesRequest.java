package networking.requests;

public class GetOptionNamesRequest implements CustomRequest
{
    private String _pizzeriaName;
    private String _optionSetName;

    public GetOptionNamesRequest(String pizzeriaName, String optionSetName){
        super();
        _pizzeriaName = pizzeriaName;
        _optionSetName = optionSetName;
    }

    public String getPizzeriaName(){
        return _pizzeriaName;
    }

    public String getOptionSetName(){
        return _optionSetName;
    }
}
