// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package io;

import exception.*;
import model.PizzaConfig;
import utils.UtilMethods;
import wrapper.CreatePizzeria;
import wrapper.UpdatePizzeria;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code PizzaConfigParser} class is responsible for parsing a pizza configuration file
 * and converting it into a structured {@code LinkedHashMap} of {@link PizzaConfig} objects.
 *
 * <p>This class reads a configuration file line by line, extracts relevant information
 * such as pizzeria names, base prices, option sets, and options, and populates
 * the {@code PizzeriaConfigAPI} instance.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 * PizzeriaConfigAPI api = new PizzeriaConfigAPI();
 * LinkedHashMap<String, PizzaConfig> configs = PizzaConfigParser.buildPizzaConfig("config.txt", api);
 * </pre>
 *
 * @author Julius Aries Kanneh, Jr
 */
public class PizzaConfigParser
{
    private static Logger logger = Logger.getLogger("PizzaConfigParser");

    /**
     * Builds a {@code LinkedHashMap} of pizza configurations from a given file.
     *
     * <p>This method reads a configuration file, extracts the necessary details,
     * and populates the {@code PizzeriaConfigAPI} with parsed data.
     * The structure of the file should follow a predefined format.</p>
     *
     * @param fileName the name of the configuration file
     * @param api      the {@code PizzeriaConfigAPI} instance to update with parsed data
     * @return {@code true} if the configuration was successfully parsed, {@code false} otherwise
     * @throws RuntimeException if an I/O error occurs while reading the file
     */
    public static boolean buildPizzaConfig(String fileName, CreatePizzeria api) {
        File file = new File(fileName);

        try {
            if (!file.isFile() || !file.canRead() || !file.exists()) {
                throw new CustomFileNotFoundException(fileName);
            }
        }
        catch (CustomException e) {
            e.fix();
        }

        if (!file.isFile() || !file.canRead() || !file.exists())
            return false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            PizzaConfig currentPizzeria = null;
            String currentOptionSetName = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("==============================")) {
                    continue;
                }

                if (line.startsWith("PizzeriaName")) {
                    String pizzeriaName = extractString(line);
                    currentPizzeria = new PizzaConfig(pizzeriaName);
                } else if (line.startsWith("BasePrice")) {
                    if (currentPizzeria != null) {
                        double basePrice = extractPrice(br);
                        UpdatePizzeria updatePizzeriaApi = (UpdatePizzeria) api;
                        updatePizzeriaApi.updateBasePrice(currentPizzeria.getConfigName(), basePrice);
                    } else {
                        //TODO: remove after testing
                        System.out.println("For debugging only, this mostly should not happen");
                        //This most-likely won't happen
                    }
                } else if (line.startsWith("OptionSetName")) {
                    if (currentPizzeria != null) {
                        currentOptionSetName = extractString(line);
                        api.addOptionSet(currentPizzeria.getConfigName(), currentOptionSetName);
                    }
                } else if (line.startsWith("OptionName")) {
                    if (currentPizzeria != null) {
                        String optionName = extractString(line);
                        double optionPrice = extractPrice(br);
                        currentPizzeria.addOption(currentOptionSetName, optionName, optionPrice);
                    }
                }
            }

            if (currentPizzeria != null) {
                api.addPizzeria(currentPizzeria);
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * Builds a pizza configuration from properties.
     *
     * <p>This method extracts details from a {@code Properties} object and
     * constructs a {@code PizzaConfig} object based on the provided values.</p>
     *
     * @param configProperties the properties object containing pizza configuration details
     * @return a {@code PizzaConfig} instance, or {@code null} if no valid configuration is found
     */
    public static PizzaConfig buildPizzaConfig(Properties configProperties) {
        PizzaConfig config = null;
        String pizzeriaName = configProperties.getProperty("pizzeria.name");

        if (pizzeriaName == null) {
            return config;
        }

        double basePrice = Double.parseDouble(configProperties.getProperty("pizzeria.basePrice"));

        // Create PizzaConfig object
        config = new PizzaConfig(pizzeriaName, basePrice);

        // Identify unique option sets dynamically
        Map<String, String> optionSets = new LinkedHashMap<>();
        for (String key : configProperties.stringPropertyNames()) {
            if (key.matches("pizzeria\\.optionSet\\.\\d+\\.name")) {
                optionSets.put(key, configProperties.getProperty(key));
            }
        }

        // Process each detected option set
        for (Map.Entry<String, String> entry : optionSets.entrySet()) {
            String optionSetKey = entry.getKey();
            String optionSetName = entry.getValue();
            String[] keyParts = optionSetKey.split("\\.");
            if (keyParts.length < 4)
                continue;
            String optionSetNumber = keyParts[2];

            // Add the option set to pizzaConfig
            try {
                boolean success = config.addOptionSet(optionSetName);
                if (!success) {
                    throw new OptionSetNotFoundException(config, optionSetName);
                }
            }
            catch (CustomException e) {
                e.fix();
            }

            // Store option names and their corresponding prices
            Map<String, Double> options = new LinkedHashMap<>();
            Map<String, String> optionNames = new LinkedHashMap<>();

            // Iterate through all properties to find matching option names and prices
            for (String key : configProperties.stringPropertyNames()) {
                String[] parts = key.split("\\.");
                if (parts.length < 6)
                    continue;

                // Ensure the key belongs to the current option set
                if (!parts[2].equals(optionSetNumber))
                    continue;

                String optionNumber = parts[4]; // Extract option number
                String lastSegment = parts[5]; // This will be "name" or "price"

                if ("name".equals(lastSegment)) {
                    optionNames.put(optionNumber, configProperties.getProperty(key));
                } else if ("price".equals(lastSegment)) {
                    try {
                        options.put(optionNumber, Double.parseDouble(configProperties.getProperty(key)));
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Error: Invalid price format for " + key);
                        options.put(optionNumber, 0.0); // Default price
                    }
                }
            }


            // Add options to the option set
            for (String optionNumber : optionNames.keySet()) {
                String optionName = optionNames.get(optionNumber);
                double optionPrice = options.getOrDefault(optionNumber, 0.0);
                config.addOption(optionSetName, optionName, optionPrice);
            }
        }

        return config;
    }

    /**
     * Retrieves configuration properties from a file.
     *
     * <p>This method reads a properties file and loads its content into a {@code Properties} object.</p>
     *
     * @param fileName the name of the configuration file
     * @return a {@code Properties} object containing the configuration data
     */
    public static Properties getConfigProperties(String fileName) {
        Properties properties = new Properties();

        try {
            if (!UtilMethods.fileExists(fileName)) {
                logger.log(Level.WARNING, ">>Fix: Attempting to fix configuration file not found error:" + fileName);
                PizzeriaExceptionFactory exceptionFactory = new PizzeriaExceptionFactory();
                Map<String, Object> data = Map.of("fileName", fileName);
                throw exceptionFactory.createException(ExceptionType.CONFIGURATION_FILE_NOT_FOUND, data);
            }
        }
        catch (CustomException e) {
            e.fix();
            fileName = ((ConfigurationFileNotFoundException) e).getFileName();
        }

        try {
            FileInputStream fileInput = new FileInputStream(fileName);
            properties.load(fileInput);
            fileInput.close();
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to find file after attempting to fix the file name." + e.getMessage());
        }

        return properties;
    }

    /**
     * Extracts a string value from a configuration line.
     *
     * <p>This method assumes the format: {@code Key: "Value"}</p>
     *
     * @param line the input line containing the key-value pair
     * @return the extracted string value
     */
    private static String extractString(String line) {
        //TODO: get the name of the pizzeria using tokenization.
        return line.split("\"")[1];
    }

    /**
     * Extracts a price value from the next line of the buffered reader.
     *
     * <p>This method assumes the format: {@code Price: X.XX}</p>
     *
     * @param br the buffered reader to read the next line
     * @return the extracted price as a {@code Double}
     * @throws IOException if an I/O error occurs while reading the file
     */
    private static Double extractPrice(BufferedReader br) throws IOException {
        //TODO: get the price of the pizzeria using tokenization.
        return Double.parseDouble(br.readLine().split(" ")[1]);
    }
}
