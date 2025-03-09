// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package networking.requests;

public class GetOptionSetNamesRequest implements CustomRequest
{
    private String _pizzeriaName;

    public GetOptionSetNamesRequest(String pizzeriaName){
        super();
        _pizzeriaName = pizzeriaName;
    }

    public String getPizzeriaName(){
        return _pizzeriaName;
    }
}
