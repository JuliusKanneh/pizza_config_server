// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package exception;

import utils.UtilMethods;

/**
 * The {@code ConfigurationFileNotFoundException} class represents a custom exception
 * that is thrown when the pizza configuration file is not found.
 *
 * <p>This exception is used to handle cases where the specified configuration file
 * is missing, allowing for proper error handling and potential recovery mechanisms.</p>
 *
 * <p>Academic Integrity Policy Statement:
 * "On my honor, as a Carnegie-Mellon Africa student, I have neither given
 * nor received unauthorized assistance on this work."</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 * try {
 *     File configFile = new File("config.txt");
 *     if (!configFile.exists()) {
 *         throw new ConfigurationFileNotFoundException("Configuration file not found.");
 *     }
 * } catch (ConfigurationFileNotFoundException e) {
 *     e.fix(); // Attempt to recover
 * }
 * </pre>
 */
public class ConfigurationFileNotFoundException extends CustomException
{

    /**
     * Constructs a new {@code ConfigurationFileNotFoundException} with a specified error message.
     *
     * @param errorMessage the message describing the error
     */
    private String _fileName;

    public ConfigurationFileNotFoundException(String fileName) {
        super("Configuration File Not Found");
        _fileName = fileName;
    }

    public String getFileName() {
        return _fileName;
    }

    @Override
    public void fix() {
        _fileName = attemptFix(_fileName);
    }

    public static String attemptFix(String originalFilePath) {
        if (UtilMethods.fileExists(originalFilePath)) {
            return originalFilePath;
        }

        String lowerCasePath = originalFilePath.toLowerCase();
        if (UtilMethods.fileExists(lowerCasePath)) {
            return lowerCasePath;
        }

        String upperCasePath = originalFilePath.toUpperCase();
        if (UtilMethods.fileExists(upperCasePath)) {
            return upperCasePath;
        }

        if (!(originalFilePath.endsWith(".properties")) || !(originalFilePath.contains("."))) {
            String filePathWithProperties = originalFilePath + ".properties";
            if (UtilMethods.fileExists(filePathWithProperties)) {
                return filePathWithProperties;
            }
        }

        return originalFilePath;
    }
}
