// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package wrapper;

public interface UpdatePizzeria
{
    /**
     * Searches the model for the entry specified by the model name for a given OptionSet and sets the name of OptionSet to [newName]
     * @param newName the new name of the optionSet
     * @param optionSetName the name of the optionSet to be updated
     * @param pizzeriaName the name of the pizzeria of which its option set is to be updated
     */
    public boolean updateOptionSetName(String pizzeriaName, String optionSetName, String newName);

    /**
     * Searches the model for the entry specified by the [pizzeriaName] and update the [basePrice] to the value specified by [newValue]
     * @param pizzeriaName the name of the pizzeria
     * @param newPrice the new base price of the pizzeria
     */
    public boolean updateBasePrice(String pizzeriaName, double newPrice);

    /**
     * Searches the model for the entry specified by the [pizzeriaName] and updates the option in the given optionSet
     * to have price specified by [newPrice]
     * @param pizzeriaName the name of the pizzeria
     * @param optionSetName the name of the optionSet
     * @param optionName the name of the option within the optionSet
     * @param newPrice the new base price of the pizzeria
     */
    public boolean updateOptionPrice(String pizzeriaName, String optionSetName, String optionName, double newPrice);

    public boolean addOption(String pizzeriaName, String optionSetName, String optionName);

    public boolean addOption(String pizzeriaName, String optionSetName, String optionName, double newPrice);
}
