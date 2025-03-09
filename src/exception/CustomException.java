// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

/**
 * File: CustomException.java
 * Author: Julius Aries Kanneh, Jr (jkanneh)
 * Date: January 25, 2025
 * Description:
 * This is a custom exception handler for the Pizza Configuration API.
 * <p>
 * Revision History:
 * - None since submission
 */
package exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code CustomException} class serves as a base class for handling custom exceptions
 * within the Pizza Configuration API.
 *
 * <p>This class provides structured error handling, logging capabilities,
 * and a method for self-healing fixes instead of simply throwing errors to the caller.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 * try {
 *     throw new CustomException("Resource not found", Level.WARNING);
 * } catch (CustomException e) {
 *     e.fix(); // Calls a self-healing method
 * }
 * </pre>
 */
public abstract class CustomException extends Exception
{
    /** The error message associated with the exception. */
    private String _errorMessage;

    /** Status indicating whether logging is enabled for the exception. */
    private boolean _loggerStatus;

    /**
     * Constructs a new {@code CustomException} with a specified error message and log level.
     *
     * @param errorMessage the error message to be logged
     * @param level the logging level (e.g., {@code Level.SEVERE}, {@code Level.WARNING})
     */
    public CustomException(String errorMessage, Level level) {
        _errorMessage = errorMessage;
        logMessage(errorMessage, level);
    }

    /**
     * Constructs a new {@code CustomException} with a specified error message.
     *
     * <p>The logging level defaults to {@code Level.ALL}.</p>
     *
     * @param errorMessage the error message to be logged
     */
    public CustomException(String errorMessage) {
        if (_loggerStatus) {
            logMessage(errorMessage, Level.SEVERE);
        }
    }

    /**
     * Fixes the exception instead of throwing an error to the caller.
     *
     * <p>This method is intended to be implemented by subclasses to provide
     * appropriate recovery mechanisms.</p>
     */
    public abstract void fix();

    /**
     * Retrieves the error message associated with the exception.
     *
     * @return the error message
     */
    public String getErrorMsg() {
        return _errorMessage;
    }

    /**
     * Retrieves the current status of the logger.
     *
     * @return {@code true} if logging is enabled, {@code false} otherwise
     */
    public boolean getLoggerStatus() {
        return _loggerStatus;
    }

    /**
     * Enables logging for the exception.
     */
    public void turnOnLogger() {
        _loggerStatus = true;
    }

    /**
     * Disables logging for the exception.
     */
    public void turnOffLogger() {
        _loggerStatus = false;
    }

    /**
     * Logs the provided message at the specified logging level if logging is enabled.
     *
     * @param message the message to log
     * @param level the logging level (e.g., {@code Level.SEVERE}, {@code Level.WARNING})
     */
    public void logMessage(String message, Level level) {
        turnOnLogger();
        Logger _logger = Logger.getLogger(getClass().getName());
        if (_loggerStatus) {
            _logger.log(level, message);
        }
    }

    /**
     * Returns a string representation of the exception, including the error message.
     *
     * @return a formatted string describing the exception
     */
    @Override
    public String toString() {
        return new StringBuilder(getClass().getName())
                .append(" [_errorMessage=")
                .append(_errorMessage)
                .append("]").toString();
    }

}
