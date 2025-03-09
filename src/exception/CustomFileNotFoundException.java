package exception;

import utils.UtilMethods;

public class CustomFileNotFoundException extends CustomException
{
    /**
     * The name of the missing file.
     */
    private String _fileName;

    /**
     * Constructs a new {@code FileNotFoundException} with the specified file name
     * and API reference.
     *
     * @param fileName the name of the missing file
     */
    public CustomFileNotFoundException(String fileName) {
        super("File not found!");
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

        if (!(originalFilePath.endsWith(".txt")) || !(originalFilePath.contains("."))) {
            String filePathWithProperties = originalFilePath + ".txt";
            if (UtilMethods.fileExists(filePathWithProperties)) {
                return filePathWithProperties;
            }
        }

        return originalFilePath;
    }
}
