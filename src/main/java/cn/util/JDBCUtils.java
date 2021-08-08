package cn.util;



import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try {
            Properties pro=new Properties();
            pro.load(new FileReader(JDBCUtils.class.getClassLoader().getResource("config.properties").toURI().getPath()));
            url=pro.getProperty("url");
            username=pro.getProperty("username");
            password=pro.getProperty("password");
            driver=pro.getProperty("driver");
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }


}
