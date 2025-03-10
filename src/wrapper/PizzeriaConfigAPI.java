// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package wrapper;

import dao.PizzaConfigDAO;

public class PizzeriaConfigAPI extends ProxyPizzerias implements CreatePizzeria, UpdatePizzeria, DeletePizzeria
{
    public PizzeriaConfigAPI(PizzaConfigDAO pizzaConfigDAO) {
        super(pizzaConfigDAO);
    }
}
