package utils;

/**
 * Utility class providing default constant values used throughout the application.
 *
 * <p>
 * This class holds default values for various configuration settings related to pizzas,
 * such as the base price, option price, and the default pizzeria name. These constants are
 * used as fallback values when no explicit configuration is provided.
 * </p>
 *
 * <ul>
 *   <li>{@link #DEFAULT_BASE_PRICE}: The default base price for a pizza.</li>
 *   <li>{@link #DEFAULT_OPTION_PRICE}: The default price for an option in a pizza configuration.</li>
 * </ul>
 */
public class ConstantValues
{
    /** The default base price for a pizza. */
    public static double DEFAULT_BASE_PRICE = 0.0;

    /** The default price for a pizza option when no specific price is provided. */
    public static double DEFAULT_OPTION_PRICE = 0.0;

    public static int DEFAULT_PORT = 9090;

    public enum UpdateOptionPriceRes {
        OPTION_SET_NOT_FOUND,
        OPTION_NOT_FOUND,
        SUCCESS
    }

    public enum OperationType{
        SERVER,
        CLIENT,
        DEFAULT_PORT
    }

}
