package networking.requests;

public class DeletePizzeriaRequest implements CustomRequest
{
    private String _pizzeriaName;

    public DeletePizzeriaRequest(String pizzeriaName) {
        super();
        _pizzeriaName = pizzeriaName;
    }

    public String getPizzeriaName() {
        return _pizzeriaName;
    }
}
