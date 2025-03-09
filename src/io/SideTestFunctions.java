/*
On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized
assistance on this work.
 */

package io;

import exception.CustomException;
import exception.OptionSetNotFoundException;
import model.PizzaConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class SideTestFunctions
{

    static Logger logger = Logger.getLogger(SideTestFunctions.class.getName());
    static Properties properties = new Properties();

    /**
     * Parses a configuration file and creates a list of {@link PizzaConfig} objects.
     *
     * <p>The method reads a file line by line, extracts pizzeria names, base prices,
     * option sets, and options, and constructs {@code PizzaConfig} instances accordingly.</p>
     *
     * @param fileName the name of the configuration file (expected to be in the {@code src/} directory)
     * @return a list of parsed {@link PizzaConfig} objects
     * @throws CustomException if any parsing error occurs
     */
    public static List<PizzaConfig> parseFile(String fileName) throws CustomException {
        List<PizzaConfig> pizzerias = new ArrayList<>();
        String filePath = "src/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            PizzaConfig currentPizzeria = null;
            String currentOptionSetName = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Skip empty lines and separators
                if (line.isEmpty() || line.startsWith("==============================")) {
                    continue;
                }

                // Extract pizzeria name
                if (line.startsWith("PizzeriaName")) {
                    if (currentPizzeria != null) {
                        pizzerias.add(currentPizzeria); // Save previous pizzeria
                    }
                    String pizzeriaName = extractString(line);
                    currentPizzeria = new PizzaConfig(pizzeriaName);
                }
                // Extract base price
                else if (line.startsWith("BasePrice")) {
                    if (currentPizzeria != null) {
                        double basePrice = extractPrice(br);
                        currentPizzeria.updateBasePrice(basePrice);
                    }
                }
                // Extract option set name
                else if (line.startsWith("OptionSetName")) {
                    if (currentPizzeria != null) {
                        currentOptionSetName = extractString(line);
                        currentPizzeria.addOptionSet(currentOptionSetName);
                    }
                }
                // Ignore OptionCount as metadata
                else if (line.startsWith("OptionCount")) {
                    continue;
                }
                // Extract option name and price
                else if (line.startsWith("OptionName")) {
                    if (currentPizzeria != null) {
                        String optionName = extractString(line);
                        double optionPrice = extractPrice(br);
                        currentPizzeria.addOption(currentOptionSetName, optionName, optionPrice);
                    }
                }
            }

            // Save last parsed pizzeria
            if (currentPizzeria != null) {
                pizzerias.add(currentPizzeria);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return pizzerias;
    }

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

            System.out.println("Printing the config to compare the property.");
            config.print();
            System.out.println("Printing the config to compare the property.");


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
     * Extracts a string value from a configuration line.
     *
     * <p>This method assumes the format: {@code Key: "Value"}</p>
     *
     * @param line the input line containing the key-value pair
     * @return the extracted string value
     */
    private static String extractString(String line) {
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
        return Double.parseDouble(br.readLine().split(" ")[1]);
    }

    /*
    /**
     * Reads and prints the content of a file to the console.
     *
     * <p>This method was intended for debugging purposes but is currently commented out.</p>
     *
     * @param fileName the name of the file to read
     */
    /*
    static void readFileToConsole(String fileName) {
        String filePath = "src/" + fileName;
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader buff = new BufferedReader(file);
            String line;

            while ((line = buff.readLine()) != null) {
                System.out.println(line);
            }
            buff.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }
    */
}
