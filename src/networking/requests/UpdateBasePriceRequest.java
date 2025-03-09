// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

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
