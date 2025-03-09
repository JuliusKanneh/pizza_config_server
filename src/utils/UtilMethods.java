package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Utility class that provides common file-related helper methods.
 * <p>
 * This class contains methods to perform basic file operations, such as verifying whether a file exists,
 * is indeed a file (and not a directory), and whether it is readable.
 * </p>
 */
public class UtilMethods
{
    /**
     * Checks whether a file exists at the specified path and verifies that it is a file and is readable.
     * <p>
     * This method creates a {@link File} object using the provided file path and checks for three conditions:
     * <ol>
     *   <li>The file exists.</li>
     *   <li>The path represents a file (and not a directory).</li>
     *   <li>The file can be read.</li>
     * </ol>
     * If all conditions are met, the method returns {@code true}; otherwise, it returns {@code false}.
     * </p>
     *
     * @param filePath the path to the file to check
     * @return {@code true} if the file exists, is a file, and is readable; {@code false} otherwise
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }

    public static void showUsage(ConstantValues.OperationType type) {
        //            case CLIENT -> System.out.println("Usage: \n\tJava Client <host name> <port number>");
        //            case SERVER -> System.out.println("Usage: \n\tJava Server <port number>");
        if (Objects.requireNonNull(type) == ConstantValues.OperationType.DEFAULT_PORT) {
            System.out.println("Usage: \n\tUsing default port 9090");
        }
    }

    public static <T> T readFromKeyboard(String prompt, Class<T> type) throws IOException {
        T result = null;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        String input = keyboard.readLine();

        if (type == String.class) {
            result = type.cast(input);
        } else if (type == Integer.class) {
            result = type.cast(Integer.parseInt(input));
        } else if (type == Double.class) {
            result = type.cast(Double.parseDouble(input));
        }else if(type == Boolean.class){
            result = type.cast(Boolean.parseBoolean(input));
        }

        return result;
    }

}
