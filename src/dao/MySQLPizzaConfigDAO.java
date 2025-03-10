// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package dao;

import db.DBConnection;
import db.MySqlConnection;
import model.PizzaConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLPizzaConfigDAO implements PizzaConfigDAO
{
    Logger _logger = Logger.getLogger(MySQLPizzaConfigDAO.class.getName());
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
        String sql = "INSERT INTO pizzerias (name, base_price) VALUES (?, ?)";
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
        //TODO: implement this method as it is implmeneted in the LocalDataStoreDAO.java class
    }

    @Override
    public void printPizzeria(String pizzeriaName) {

    }

    @Override
    public PizzaConfig getPizzaConfig(String pizzeriaName) {
        String sql = "SELECT * FROM pizzerias WHERE name = ?";
        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pizzeriaName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PizzaConfig config = new PizzaConfig();
                config.setConfigName(rs.getString("name"));
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
        String sql = "SELECT name FROM pizzerias";
        try (Connection conn = CONNECTION.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("name"));
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
        String sql = "UPDATE pizzerias SET base_price = ? WHERE name = ?";
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
        String sql = "DELETE FROM pizzerias WHERE name = ?";
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
}
