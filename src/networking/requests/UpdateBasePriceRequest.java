package networking.requests;

public class UpdateBasePriceRequest implements CustomRequest
{
    private String _pizzeriaName;
    private double _basePrice;

    public UpdateBasePriceRequest(String pizzeriaName, double basePrice){
        super();
        _pizzeriaName = pizzeriaName;
        _basePrice = basePrice;
    }

    public String get_pizzeriaName() {
        return _pizzeriaName;
    }

    public double get_basePrice() {
        return _basePrice;
    }
}
