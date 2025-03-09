// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package wrapper;

public interface DeletePizzeria
{

    public boolean deletePizzeria(String pizzeriaName);
    /**
     * Deletes an optionSet giving the pizzeriaName.
     * @param pizzeriaName the name of the Pizzeria.
     * @param optionSetName the name of the optionSet.
     * @param optionName the name of the option to be deleted.
     */
    public boolean deleteOption(String pizzeriaName, String optionSetName, String optionName);

    /**
     * Deletes an optionSet giving the pizzeriaName.
     * @param pizzeriaName the name of the Pizzeria.
     * @param optionSetName the name of the optionSet to be deleted.
     */
    public boolean deleteOptionSet(String pizzeriaName, String optionSetName);

}
