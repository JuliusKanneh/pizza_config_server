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

    public MySqlConnection(String url, String user, String password) {
        super(url, user, password);
        _url = url;
        _user = user;
        _password = password;
    }

    @Override
    public void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(_url, _user, _password);
    }

    @Override
    public void closeConnection() {

    }
}
