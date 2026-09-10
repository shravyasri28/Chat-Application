package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public static String driverPath = "com.mysql.cj.jdbc.Driver";

    public static String host = System.getenv().getOrDefault("DB_HOST", "localhost");
    public static String username = System.getenv().getOrDefault("DB_USER", "root");
    public static String password = System.getenv().getOrDefault("DB_PASSWORD", "");
    public static String dbname = System.getenv().getOrDefault("DB_NAME", "chat");
    public static int portNo = Integer.parseInt(
        System.getenv().getOrDefault("DB_PORT", "3306")
    );

    public String url = "jdbc:mysql://" + host + ":" + portNo + "/" + dbname
            + "?useUnicode=true&characterEncoding=UTF-8&useSSL=true";

    static Connection conn = null;

    public DatabaseConfig() throws ClassNotFoundException, SQLException {

        Class.forName(driverPath);

        conn = DriverManager.getConnection(url, username, password);
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public Connection getConnection() {
        return conn;
    }
}
