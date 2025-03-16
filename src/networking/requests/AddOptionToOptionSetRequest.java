package networking.requests;


public class AddOptionToOptionSetRequest implements CustomRequest
{
    private String _pizzeriaName;
    private String _optionSetName;
    private String _optionName;
    private double _price;

    public AddOptionToOptionSetRequest(String pizzeriaName, String optionSetName, String optionName, double price) {
        super();
        _pizzeriaName = pizzeriaName;
        _optionSetName = optionSetName;
        _optionName = optionName;
        _price = price;
    }

    public AddOptionToOptionSetRequest(String pizzeriaName, String optionSetName, String optionName) {
        super();
        _pizzeriaName = pizzeriaName;
        _optionSetName = optionSetName;
        _optionName = optionName;
        _price = 0.0;
    }

    public String getOptionName() {
        return _optionName;
    }

    public String getOptionSetName() {
        return _optionSetName;
    }

    public String getPizzeriaName() {
        return _pizzeriaName;
    }

    public double getPrice() {
        return _price;
    }
}
