// "On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work."

package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection extends DBConnection
{
    private String _url;
    private String _user;
    private String _password;
    private String _dbName;

    public MySqlConnection(String url, String dbName, String user, String password) {
        super(url, dbName, user, password);
        _url = url;
        _user = user;
        _password = password;
        _dbName = dbName;
    }

    @Override
    public void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public Connection getConnection() throws SQLException {
        _url += _dbName;
        return DriverManager.getConnection(_url, _user, _password);
    }

    @Override
    public void closeConnection() {

    }
}
