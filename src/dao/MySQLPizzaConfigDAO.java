// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package dao;

import db.DBConnection;
import db.MySqlConnection;
import exception.CustomException;
import exception.ExceptionFactory;
import exception.ExceptionType;
import exception.PizzeriaExceptionFactory;
import model.PizzaConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLPizzaConfigDAO implements PizzaConfigDAO
{
    Logger _logger = Logger.getLogger(MySQLPizzaConfigDAO.class.getName());
    ExceptionFactory factory = new PizzeriaExceptionFactory();
    CustomException exception;
    private static DBConnection CONNECTION = null;
    private String _dbName;
    private String _url;
    private String _user;
    private String _password;

    public MySQLPizzaConfigDAO(String dbName, String url, String username, String password) {
        _dbName = dbName;
        _url = url;
        _user = username;
        _password = password;
        CONNECTION = new MySqlConnection(_url, _dbName, _user, _password);
    }

    @Override
    public boolean createPizzeria(PizzaConfig config) {
        String sql = "INSERT INTO pizzaconfig (config_name, base_price) VALUES (?, ?)";
        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, config.getConfigName());
            pstmt.setDouble(2, config.getBasePrice());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException e) {
            _logger.log(Level.SEVERE, "Failed to create pizzeria: " + config.getConfigName(), e);
            return false;
        }
    }

    @Override
    public void configurePizzeriasFromTxtFile(String filename) {

    }

    @Override
    public void addOptionSet(String pizzeriaName, String optionSetName) {
        PreparedStatement optionSetStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;

        String sql = "SELECT id, base_price FROM pizzaconfig WHERE name = ?";

        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pizzeriaName);
            rs = pstmt.executeQuery();

            int pizzeriaId;

            if (rs.next()) {
                // Pizzeria exists, get its ID
                pizzeriaId = rs.getInt("pizza_config_id");

                // Check if base_price is set
                double basePrice = rs.getDouble("base_price");
                if (basePrice <= 0) {
                    // Base price missing, handle exception
                    Map<String, Object> params = Map.of("pizzeriaName", pizzeriaName);
                    exception = factory.createException(ExceptionType.BASE_PRICE_MISSING, params);
                    exception.fix();
                }
            } else {
                // Pizzeria doesn't exist, create it
                String insertPizzeriaSql = "INSERT INTO pizzaconfig (config_name) VALUES (?)";
                insertStmt = conn.prepareStatement(insertPizzeriaSql, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setString(1, pizzeriaName);
                insertStmt.executeUpdate();

                // Get the newly created pizzeria ID
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    pizzeriaId = generatedKeys.getInt(1);

                    // Handle missing base price
                    Map<String, Object> params = Map.of("pizzeriaName", pizzeriaName);
                    exception = factory.createException(ExceptionType.BASE_PRICE_MISSING, params);
                    exception.fix();
                } else {
                    throw new SQLException("Failed to create pizzeria, no ID obtained.");
                }

                generatedKeys.close();
                insertStmt.close();
            }

            // Add option set
            String insertOptionSet = "INSERT INTO option_sets (pizzeria_id, name) VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE name = name";  // No-op update if exists
            optionSetStmt = conn.prepareStatement(insertOptionSet);
            optionSetStmt.setInt(1, pizzeriaId);
            optionSetStmt.setString(2, optionSetName);
            optionSetStmt.executeUpdate();
        }
        catch (SQLException e) {
            // Handle database exceptions
            throw new RuntimeException("Database error while adding option set: " + e.getMessage(), e);
        }
        finally {
            // Close resources except connection
            try {
                if (rs != null)
                    rs.close();
                if (insertStmt != null)
                    insertStmt.close();
                if (optionSetStmt != null)
                    optionSetStmt.close();
            }
            catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    @Override
    public void printPizzeria(String pizzeriaName) {

    }

    @Override
    public PizzaConfig getPizzaConfig(String pizzeriaName) {
        String sql = "SELECT * FROM pizzaconfig WHERE config_name = ?";
        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pizzeriaName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PizzaConfig config = new PizzaConfig();
                config.setConfigName(rs.getString("config_name"));
                config.updateBasePrice(rs.getDouble("base_price"));
                // TODO: Load option sets and options from related tables
                return config;
            }
            return null;
        }
        catch (SQLException e) {
            _logger.log(Level.SEVERE, "Failed to retrieve pizzeria: " + pizzeriaName, e);
            return null;
        }
    }

    @Override
    public ArrayList<String> getAllPizzeriaNames() {
        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT config_name FROM pizzaconfig";
        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("config_name"));
            }
        }
        catch (SQLException e) {
            _logger.log(Level.SEVERE, "Failed to retrieve pizzeria names", e);
        }
        return names;
    }

    @Override
    public void printAllPizzerias() {

    }

    @Override
    public boolean updatePizzeria(PizzaConfig config) {
        String sql = "UPDATE pizzaconfig SET base_price = ? WHERE config_name = ?";
        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, config.getBasePrice());
            pstmt.setString(2, config.getConfigName());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException e) {
            _logger.log(Level.SEVERE, "Failed to update pizzeria: " + config.getConfigName(), e);
            return false;
        }
    }

    @Override
    public boolean deletePizzeria(String pizzeriaName) {
        String sql = "DELETE FROM pizzaconfig WHERE config_name = ?";
        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pizzeriaName);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException e) {
            _logger.log(Level.SEVERE, "Failed to delete pizzeria: " + pizzeriaName, e);
            return false;
        }
    }

    @Override
    public ArrayList<String> getOptionSetNames(String pizzeriaName) {
        return null;
    }

    @Override
    public boolean updateOptionSetName(String pizzeriaName, String optionSetName, String newName) {
        return false;
    }

    @Override
    public boolean updateBasePrice(String pizzeriaName, double newPrice) {
        return false;
    }

    @Override
    public boolean updateOptionPrice(String pizzeriaName, String optionSetName, String optionName, double newPrice) {
        return false;
    }

    @Override
    public boolean deleteOption(String pizzeriaName, String optionSetName, String optionName) {
        return false;
    }

    @Override
    public boolean deleteOptionSet(String pizzeriaName, String optionSetName) {
        return false;
    }

    @Override
    public boolean addOption(String pizzeriaName, String optionSetName, String optionName) {
        return false;
    }

    @Override
    public boolean addOption(String pizzeriaName, String optionSetName, String optionName, double price) {
        return false;
    }

    @Override
    public ArrayList<String> getOptions(String pizzeriaName, String optionSetName) {
        return null;
    }
}
