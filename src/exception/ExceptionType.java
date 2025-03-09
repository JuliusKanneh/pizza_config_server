// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package exception;

/**
 * The {@code ExceptionType} enum defines various types of exceptions
 * that can occur in the Pizza Configuration API.
 *
 * <p>This enumeration is used to categorize different exception scenarios,
 * making error handling more structured and maintainable.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 * throw PizzeriaExceptionFactory.createException(ExceptionType.PIZZERIA_NOT_FOUND, this, pizzeriaName);
 * </pre>
 */
public enum ExceptionType
{

    /**
     * Indicates that a base price is missing for a pizza configuration.
     */
    BASE_PRICE_MISSING,

    /**
     * Indicates that an option set with the same name already exists.
     */
    OPTIONS_SET_ALREADY_EXIST,

    /**
     * Indicates that an option with the same name already exists within an option set.
     */
    OPTION_ALREADY_EXISTS,

    /**
     * Indicates that a specified option set was not found.
     */
    OPTION_SET_NOT_FOUND,

    /**
     * Indicates that a specified option was not found within an option set.
     */
    OPTION_NOT_FOUND,

    /**
     * Indicates that a requested pizzeria configuration was not found.
     */
    PIZZERIA_NOT_FOUND,


    /**
     * Indicates that a pizzeria configuration with the same name already exists.
     */
    PIZZERIA_ALREADY_EXISTS,

    /**
     * Indicates that the required configuration file was not found.
     */
    CONFIGURATION_FILE_NOT_FOUND
}
