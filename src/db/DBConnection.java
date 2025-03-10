package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class DBConnection
{
    private String _url;
    private String _dbName;
    private String _user;
    private String _password;
    private Logger _logger;
    private static int BD_CONNECTION_COUNT;

    public DBConnection(String url, String dbName, String user, String password) {
        _url = url;
        _dbName = dbName;
        _user = user;
        _password = password;
        _logger = Logger.getLogger(this.getClass().getName());
        BD_CONNECTION_COUNT++;
    }

    public int getBdConnectionCount(){
        return BD_CONNECTION_COUNT;
    }

    public abstract void loadDriver() throws ClassNotFoundException;

    public abstract Connection getConnection() throws SQLException;

    public abstract void closeConnection();

}
